package tdpay.mvc.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdpay.mvc.entity.SystemProperty;
 
/**
 * SystemPropertyRepository
 */
@Repository
public interface SystemPropertyRepository extends BaseRepository<SystemProperty> {

    @Query("select tbl from SystemProperty tbl where tbl.code = ?1")
    SystemProperty findByCode(String code);

}
