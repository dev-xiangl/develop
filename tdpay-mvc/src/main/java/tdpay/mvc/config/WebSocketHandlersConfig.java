package tdpay.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.session.ExpiringSession;

import tdpay.mvc.handler.WebSocketConnectHandler;
import tdpay.mvc.handler.WebSocketDisconnectHandler;

@Configuration
public class WebSocketHandlersConfig<S extends ExpiringSession> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

	@Bean
	public WebSocketConnectHandler<S> webSocketConnectHandler() {
		return new WebSocketConnectHandler<S>(messagingTemplate);
	}

	@Bean
	public WebSocketDisconnectHandler<S> webSocketDisconnectHandler() {
		return new WebSocketDisconnectHandler<S>(messagingTemplate);
	}
}
