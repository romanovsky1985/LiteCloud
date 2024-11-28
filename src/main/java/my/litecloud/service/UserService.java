package my.litecloud.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import my.litecloud.dto.PageDTO;
import my.litecloud.entity.PageEntity;
import my.litecloud.entity.UserEntity;
import my.litecloud.mapper.PageMapper;
import my.litecloud.repository.PageRepository;
import my.litecloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PageMapper pageMapper;
    @Autowired
    private EntityManager entityManager;

    public void createUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return;
        }
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void appendPage(String username, PageDTO pageDTO) {
        UserEntity user = userRepository.getByUsername(username);
        PageEntity page = pageMapper.map(pageDTO);
        page.setOwner(user);
        pageRepository.save(page);
    }

    public void deletePage(String username, Long id) {
        PageEntity page = pageRepository.getById(id);
        if (!Objects.equals(page.getOwner().getUsername(), username)) {
            throw new RuntimeException("permission denied");
        }
        pageRepository.deleteById(id);
    }

    public List<PageDTO> getPages(String username) {
        UserEntity user = userRepository.getByUsername(username);
        return user.getPages().stream()
                .map(pageMapper::map)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        {
            return userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException(String.format("User: %s not found", username)));
        }
    }

}
