package tdpay.mvc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import jp.isols.common.constants.Flags;
import jp.isols.common.utils.StringUtils;
import tdpay.mvc.dto.UserSearchDto;
import tdpay.mvc.entity.Role;
import tdpay.mvc.entity.User;
import tdpay.mvc.entity.UserAuthority;
import tdpay.mvc.form.UserMaintenanceRegistForm;
import tdpay.mvc.repository.RoleRepository;
import tdpay.mvc.repository.UserAuthorityRepository;
import tdpay.mvc.repository.UserRepository;
import tdpay.mvc.security.AppUserDetails;
import tdpay.mvc.utils.UserDetailsUtils;

/**
 *
 */
@Service
public class UserManageService {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(UserManageService.class);

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    @Autowired
    protected UserAuthorityRepository userAuthorityRepository;

    @Transactional
    public User getUser(final Long userId) {
        Long count = userRepository.count();
        logger.info("userRepository.count(): " + count);

    	return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public User getUserByLoginId(final String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    @Transactional
    public List<User> getUserList() {
        return userRepository.findByEnabled();
    }

    @Transactional
    public List<User> getUserList(final UserSearchDto userSearchDto) {
        User entity = new User();
        if (!StringUtils.isBlank(userSearchDto.getMid())) {
            entity.setMid(userSearchDto.getMid());
        }
        if (!StringUtils.isBlank(userSearchDto.getUserName())) {
            entity.setName(userSearchDto.getUserName());
        }
        if (!StringUtils.isBlank(userSearchDto.getLoginId())) {
            entity.setLoginId(userSearchDto.getLoginId());
        }
        entity.setEnableFlag(Flags.ENABLE);

        ExampleMatcher matcher =
                ExampleMatcher.matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreNullValues();
    	Example<User> example = Example.of(entity, matcher);

        List<User> list = userRepository.findAll(example);
        return list;
    }

    @Transactional
    public Role getRole(final Long roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    @Transactional
    public List<Role> getRoleList() {
        return roleRepository.findByEnabled();
    }

    @Transactional
    public boolean equalsPassword(final String password) {
        final Long userId = UserDetailsUtils.getUserId();
        final User user = this.getUser(userId);

        return passwordEncoder.matches(password, user.getPassword());
    }

    public boolean equalsPassword(final String plainPassword, final String encodedPassword) {
        return passwordEncoder.matches(plainPassword, encodedPassword);
    }

    /**
     * セッションがアクティブのユーザーに対し、ロール権限を更新する。
     *
     * @param userIdList 更新するユーザーIDリスト
     * @param roleId ユーザーIDリストに設定するロールID
     */
    public void refreshUserAuthority(final List<Long> userIdList, final Long roleId) {
        final List<AppUserDetails> userDetailsList = UserDetailsUtils.getLoggedUserDetailsList(userIdList);
        if (CollectionUtils.isEmpty(userDetailsList)) {
            logger.debug("userDetailsList is empty.");
            return;
        }
        logger.debug("userDetailsList: {}", userDetailsList);

        final List<UserAuthority> userAuthorityList = userAuthorityRepository.findByRoleId(roleId);
        if (CollectionUtils.isEmpty(userAuthorityList)) {
            logger.debug("userAuthorityList is empty.");
            return;
        }

        final List<String> codeList = userAuthorityList.stream().map(x -> x.getCode()).collect(Collectors.toList());

        userDetailsList.stream().forEach(x -> {
                UserDetailsUtils.expireUserSessions(x.getUserId());
                x.setAuthoritiesByCode(codeList);
            });

        logger.debug("userDetailsList: {}", userDetailsList);
    }

    @Transactional
    public void saveUserMaintenance(final User user, final String plainPassword) {
        if (!StringUtils.isEmpty(plainPassword)) {
            user.setPassword(passwordEncoder.encode(plainPassword));
        }

        if (user.getCreatedBy() == null) {
            user.setCreatedBy(UserDetailsUtils.getUserId().toString());
            user.setCreatedDatetime(LocalDateTime.now());
            user.setUpdatedBy(null);
            user.setUpdatedDatetime(null);
        } else {
            user.setCreatedBy(user.getCreatedBy());
            user.setCreatedDatetime(user.getCreatedDatetime());
            user.setUpdatedBy(UserDetailsUtils.getUserId().toString());
            user.setUpdatedDatetime(LocalDateTime.now());
        }

        userRepository.save(user);
    }

}
