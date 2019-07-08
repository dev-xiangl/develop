package tdpay.mvc.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import tdpay.mvc.common.UrlConstants;
import tdpay.mvc.utils.UserDetailsUtils;

/**
 * ログイン認証成功時のハンドラ
 */
public class AppAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(AppAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.debug("onAuthenticationSuccess");
        logger.debug("UserDetailsUtils.isLoggedIn(): {}", UserDetailsUtils.isLoggedIn());

        redirectStrategy.sendRedirect(request, response, UrlConstants.INDEX);
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}