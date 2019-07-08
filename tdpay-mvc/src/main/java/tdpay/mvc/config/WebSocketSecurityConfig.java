package tdpay.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
        messages
            .simpTypeMatchers(
            		SimpMessageType.CONNECT,
            		SimpMessageType.MESSAGE,
            		SimpMessageType.SUBSCRIBE).authenticated()
            .simpTypeMatchers(
            		SimpMessageType.UNSUBSCRIBE,
            		SimpMessageType.DISCONNECT).permitAll()
            .anyMessage().denyAll();
    }

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}
}
