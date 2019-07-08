package tdpay.mvc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.filter.GenericFilterBean;

import tdpay.mvc.utils.UserDetailsUtils;

public class AppSessionManagementFilter extends GenericFilterBean {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(AppSessionManagementFilter.class);

    private SessionRegistry sessionRegistry;

    public AppSessionManagementFilter(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
        UserDetailsUtils.setSessionRegistry(sessionRegistry);
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest servletRequest = (HttpServletRequest)request;

        final HttpSession session = servletRequest.getSession(false);
        if (session != null) {
            //logger.debug("session: ID={}", session.getId());
            final SessionInformation sessionInformation = sessionRegistry.getSessionInformation(session.getId());
            final AppUserDetails userDetails = UserDetailsUtils.getUserDetails();
            if (userDetails != null) {
                //logger.debug("userDetails: {}", userDetails);
            	if (!userDetails.isEnabled()) {
	                session.invalidate();
	            } else if (sessionInformation == null) {
	                final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	                final Authentication newAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), userDetails.getAuthorities());
	                SecurityContextHolder.getContext().setAuthentication(newAuthentication);
	                sessionRegistry.registerNewSession(session.getId(), userDetails);
	            }
            }
        }

        chain.doFilter(request, response);
    }
}