package tdpay.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdpay.mvc.entity.RoleAuthority;
 
/**
 * RoleAuthorityRepository
 */
@Repository
public interface RoleAuthorityRepository extends BaseRepository<RoleAuthority> {

    @Query("select tbl from RoleAuthority tbl where tbl.enableFlag = jp.isols.common.constants.Flags.ENABLE")
    List<RoleAuthority> findByEnabled();

}
