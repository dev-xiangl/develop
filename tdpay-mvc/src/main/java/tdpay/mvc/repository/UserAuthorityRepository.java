package tdpay.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdpay.mvc.entity.UserAuthority;
 
/**
 * UserAuthorityRepository
 */
@Repository
public interface UserAuthorityRepository extends BaseRepository<UserAuthority> {

    @Query("select tbl from UserAuthority tbl where tbl.roleId = ?1")
    List<UserAuthority> findByRoleId(Long roleId);

    @Query("select tbl from UserAuthority tbl where tbl.enableFlag = jp.isols.common.constants.Flags.ENABLE")
    List<UserAuthority> findByEnabled();

    @Query("delete from UserAuthority tbl where tbl.roleId = ?1")
    void deleteByRoleId(Long roleId);

}
