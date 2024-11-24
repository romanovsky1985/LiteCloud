package my.litecloud.repository;

import my.litecloud.entity.PageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends CrudRepository<PageEntity, Long> {
    default PageEntity getById(Long id) {
        return findById(id).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Page: %d not found", id)));
    }
}
