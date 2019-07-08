package tdpay.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdpay.mvc.entity.User;
 
/**
 * UserRepository
 */
@Repository
public interface UserRepository extends BaseRepository<User> {

    @Query("select tbl from User tbl where tbl.loginId = ?1")
    User findByLoginId(String loginId);

    @Query("select tbl from User tbl where tbl.roleId = ?1")
    List<User> findByRoleId(Long roleId);

    @Query("select tbl from User tbl where tbl.enableFlag = jp.isols.common.constants.Flags.ENABLE")
    List<User> findByEnabled();

}
