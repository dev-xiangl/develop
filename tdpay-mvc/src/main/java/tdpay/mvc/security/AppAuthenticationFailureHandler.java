package tdpay.mvc.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import tdpay.mvc.common.UrlConstants;

/**
 * ログイン認証失敗時のハンドラ
 */
public class AppAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(AppAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.error("onAuthenticationFailure");

		RequestDispatcher dispatch = request.getRequestDispatcher(UrlConstants.LOGIN);
		request.setAttribute("error", true);
		dispatch.forward(request, response);
	}

}