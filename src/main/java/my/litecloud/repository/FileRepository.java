package my.litecloud.repository;

import my.litecloud.entity.FileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface FileRepository extends CrudRepository<FileEntity, Long> {
    default FileEntity getById(Long id) {
        return findById(id).orElseThrow(
                () -> new UsernameNotFoundException(String.format("File: %d not found", id)));
    }
}
