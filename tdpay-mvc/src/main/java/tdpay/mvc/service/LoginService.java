package tdpay.mvc.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tdpay.mvc.entity.User;
import tdpay.mvc.repository.UserRepository;

/**
 *
 */
@Service
public class LoginService {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(LoginService.class);

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    /**
     * ログインを行う。
     *
     * @param username UserDetailsクラスのusername
     * @param password UserDetailsクラスのpassword
     * @note WebSocket用ユーザー向けに、GrantedAuthorityリストを生成して、UsernamePasswordAuthenticationTokenクラスに設定する。
     */
    @Transactional
    public boolean login(final String username, final String password) {
        logger.debug("username=" + username + ", password=" + password);

        User user = userRepository.findByLoginId(username);
        if (user == null) {
            logger.debug("User is null.");
            return false;
        }

        try {
            logger.debug("login(String userId, String password):");
            logger.debug("  username=" + username);

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Bad credentials for user " + username);
            }

            final List<GrantedAuthority> authorities = null;
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
            logger.info("authentication:");
            logger.info("  Credentials:" + (String)authentication.getCredentials());

            SecurityContext ctx = SecurityContextHolder.getContext();
            ctx.setAuthentication(authentication);
            SecurityContextHolder.setContext(ctx);
        } catch (AuthenticationException e) {
            logger.error("Authentication failed: " + e.getMessage());
            return false;
        }

        //logger.debug("Role Code: {}", UserDetailsUtils.getUserDetails().getAuthorities().toString());

        return true;
    }

    @Transactional
    public void logout() {
    }
}
