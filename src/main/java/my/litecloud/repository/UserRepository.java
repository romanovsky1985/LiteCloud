package my.litecloud.repository;

import my.litecloud.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    default UserEntity getByUsername(String username) {
        return findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User: %s not found", username)));
    }
}
