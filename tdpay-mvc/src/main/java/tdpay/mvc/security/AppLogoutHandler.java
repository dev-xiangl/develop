package tdpay.mvc.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import tdpay.mvc.common.UrlConstants;

/**
 * ログアウト時のハンドラ
 */
public class AppLogoutHandler extends SimpleUrlLogoutSuccessHandler {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(AppLogoutHandler.class);

    /**
     * ログアウト成功時
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.debug("");
        this.setDefaultTargetUrl(UrlConstants.LOGIN);

        super.onLogoutSuccess(request, response, authentication);
    }
}
