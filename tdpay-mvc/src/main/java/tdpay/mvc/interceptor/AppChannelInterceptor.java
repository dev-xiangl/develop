package tdpay.mvc.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

import tdpay.mvc.service.LoginService;

public class AppChannelInterceptor implements ChannelInterceptor {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(AppChannelInterceptor.class);

    @SuppressWarnings("unused")
    private final LoginService loginService;

    /**
     * コンストラクタ
     * @param loginService
     */
    public AppChannelInterceptor(final LoginService loginService) {
    	logger.debug("AppChannelInterceptor Constructor.");
        this.loginService = loginService;
    }

    @Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
    	logger.debug("message: " + message.toString());
        return message;
    }
}
