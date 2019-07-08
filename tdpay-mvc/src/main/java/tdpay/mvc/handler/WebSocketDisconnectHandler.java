package tdpay.mvc.handler;

import java.security.Principal;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import tdpay.mvc.common.WebSocketConstants;

public class WebSocketDisconnectHandler<S> implements ApplicationListener<SessionDisconnectEvent> {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(WebSocketDisconnectHandler.class);

	private SimpMessagingTemplate messagingTemplate;

	public WebSocketDisconnectHandler(final SimpMessagingTemplate messagingTemplate) {
		super();
		this.messagingTemplate = messagingTemplate;
	}

	public void onApplicationEvent(SessionDisconnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        logger.debug("accessor.getSessionId(): " + accessor.getSessionId());
        logger.debug("accessor.toNativeHeaderMap(): " + accessor.toNativeHeaderMap());

		String id = event.getSessionId();
		if (id == null) {
			return;
		}

		MessageHeaders headers = event.getMessage().getHeaders();
		Principal user = SimpMessageHeaderAccessor.getUser(headers);
		logger.debug("user: {}", user);

		accessor.setMessageTypeIfNotSet(SimpMessageType.MESSAGE);
		accessor.setLeaveMutable(true);

		final String destination = WebSocketConstants.SIMPLE_BLOKER + WebSocketConstants.Message.SET_STATUS;
		logger.debug("destination: {}", destination);
		this.messagingTemplate.convertAndSend(destination, user == null ? "" : Arrays.asList(user.getName()), accessor.getMessageHeaders());
	}
}