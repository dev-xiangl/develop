package tdpay.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
 
/**
 * BaseRepository
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

    Long getMaxId();
}
