package tdpay.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdpay.mvc.entity.Role;
 
/**
 * RoleRepository
 */
@Repository
public interface RoleRepository extends BaseRepository<Role> {

    @Query("select tbl from Role tbl where tbl.enableFlag = jp.isols.common.constants.Flags.ENABLE")
    List<Role> findByEnabled();

}
