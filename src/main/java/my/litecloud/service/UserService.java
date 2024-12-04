package my.litecloud.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import jakarta.transaction.Transactional;
import my.litecloud.dto.FileCreateDTO;
import my.litecloud.dto.FileReadDTO;
import my.litecloud.dto.PageDTO;
import my.litecloud.entity.FileEntity;
import my.litecloud.entity.PageEntity;
import my.litecloud.entity.UserEntity;
import my.litecloud.mapper.FileMapper;
import my.litecloud.mapper.PageMapper;
import my.litecloud.repository.FileRepository;
import my.litecloud.repository.PageRepository;
import my.litecloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private PageMapper pageMapper;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileMapper fileMapper;



    public void createUser(String username, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        {
            return userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException(String.format("User: %s not found", username)));
        }
    }

    /*========== Pages ============*/

    public void appendPage(String username, PageDTO pageDTO) {
        UserEntity user = userRepository.getByUsername(username);
        PageEntity page = pageMapper.map(pageDTO);
        page.setOwner(user);
        pageRepository.save(page);
    }

    public List<PageDTO> getPages(String username) {
        UserEntity user = userRepository.getByUsername(username);
        return user.getPages().stream()
                .map(pageMapper::map)
                .toList();
    }

    public void deletePage(String username, Long id) {
        PageEntity page = pageRepository.getById(id);
        if (!Objects.equals(page.getOwner().getUsername(), username)) {
            throw new RuntimeException("permission denied");
        }
        pageRepository.deleteById(id);
    }

    /*========== Files ============*/

    public void appendFile(String username, FileCreateDTO fileDTO) {
        UserEntity user = userRepository.getByUsername(username);
        FileEntity file = fileMapper.map(fileDTO);
        file.setOwner(user);
        fileRepository.save(file);
    }

    public List<FileReadDTO> getFiles(String username) {
        UserEntity user = userRepository.getByUsername(username);
        return user.getFiles().stream()
                .map(fileMapper::map)
                .toList();
    }

    public void deleteFile(String username, Long id) {
        FileEntity file = fileRepository.getById(id);
        if (!Objects.equals(file.getOwner().getUsername(), username)) {
            throw new RuntimeException("permission denied");
        }
        fileRepository.deleteById(id);
    }

    public void shareFile(String username, Long id, boolean shared) {
        FileEntity file = fileRepository.getById(id);
        if (!Objects.equals(file.getOwner().getUsername(), username)) {
            throw new RuntimeException("permission denied");
        }
        file.setShared(shared);
        fileRepository.save(file);
    }

    public String getFileName(String username, Long id) {
        FileEntity file = fileRepository.getById(id);
        if (!Objects.equals(file.getOwner().getUsername(), username)) {
            throw new RuntimeException("permission denied");
        }
        return file.getName();
    }

    public String getFileName(Long id) {
        FileEntity file = fileRepository.getById(id);
        if (!file.getShared()) {
            throw new RuntimeException("permission denied");
        }
        return file.getName();
    }

    public byte[] getFileData(String username, Long id) {
        FileEntity file = fileRepository.getById(id);
        if (!Objects.equals(file.getOwner().getUsername(), username)) {
            throw new RuntimeException("permission denied");
        }
        return file.getData();
    }

    public byte[] getFileData(Long id) {
        FileEntity file = fileRepository.getById(id);
        if (!file.getShared()) {
            throw new RuntimeException("permission denied");
        }
        return file.getData();
    }

}
