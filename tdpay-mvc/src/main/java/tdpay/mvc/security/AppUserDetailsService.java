package tdpay.mvc.security;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.isols.common.constants.Flags;
import tdpay.mvc.entity.Role;
import tdpay.mvc.entity.RoleAuthority;
import tdpay.mvc.entity.User;
import tdpay.mvc.entity.UserAuthority;
import tdpay.mvc.repository.RoleAuthorityRepository;
import tdpay.mvc.repository.RoleRepository;
import tdpay.mvc.repository.UserAuthorityRepository;
import tdpay.mvc.repository.UserRepository;

@Component
public class AppUserDetailsService implements UserDetailsService {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(AppUserDetailsService.class);

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected RoleAuthorityRepository roleAuthorityRepository;

    @Autowired
    protected UserAuthorityRepository userAuthorityRepository;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("username = " + username);

		logger.debug("User");
		User user = userRepository.findByLoginId(username);
		if (user == null) {
			throw new UsernameNotFoundException("User:" + username + " not found.");
		}
		if (!user.getEnableFlag().equals(Flags.ENABLE)) {
			throw new UsernameNotFoundException("User:" + username + " is disable.");
		}

		logger.debug("Role");
		Role role = roleRepository.findById(user.getRoleId()).orElse(null);
		if (role == null) {
			throw new UsernameNotFoundException("Role: RoleId:" + user.getRoleId() + " not found.");
		}
		if (!role.getEnableFlag().equals(Flags.ENABLE)) {
			throw new UsernameNotFoundException("Role:" + role.getId() + " is disable.");
		}

		AppUserDetails userDetails = new AppUserDetails();
        userDetails.setUsername(user.getLoginId());
        userDetails.setPassword(user.getPassword());
		userDetails.setEnabled(user.getEnableFlag().equals(Flags.ENABLE));
		userDetails.setUserId(user.getId());
		userDetails.setLoginId(user.getLoginId());
		userDetails.setName(user.getName());
		userDetails.setRoleId(role.getId());
		userDetails.setRoleName(role.getName());
		userDetails.setMid(user.getMid());
		setAuthority(userDetails);

        return userDetails;
	}

    protected void setAuthority(AppUserDetails userDetails) {
        final List<RoleAuthority> roleAuthorityList = roleAuthorityRepository.findByEnabled();
        if (roleAuthorityList == null || roleAuthorityList.isEmpty()) {
            logger.debug("RoleAuthority is empty.");
            return;
        }

		/* role_authority.CODE */
		final List<String> roleAuthorityCodeList =
				roleAuthorityList.stream().map(x -> x.getCode()).distinct().collect(Collectors.toList());

        List<UserAuthority> userAuthorityList = userAuthorityRepository.findByRoleId(userDetails.getRoleId());
        if (userAuthorityList == null) {
            logger.debug("UserAuthority is empty.");
            return;
        }

		/* role_authority.CODEに存在しないエンティティを削除 */
		userAuthorityList.removeIf(x -> !roleAuthorityCodeList.contains(x.getCode()));

        if (userAuthorityList.isEmpty()) {
            logger.debug("UserAuthority is empty.");
            return;
        }

        userAuthorityList.stream().filter(x -> x.getEnableFlag().equals(Flags.ENABLE)).forEach(x -> {
		        AppGrantedAuthority authority = new AppGrantedAuthority(x.getCode());
				userDetails.addAuthority(authority);
	        });
        logger.debug("User RoleAuthority: {}", userDetails.getAuthorities());
    }
}
