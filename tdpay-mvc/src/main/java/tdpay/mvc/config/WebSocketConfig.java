package tdpay.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import tdpay.mvc.common.WebSocketConstants;
import tdpay.mvc.interceptor.AppChannelInterceptor;
import tdpay.mvc.service.LoginService;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private LoginService loginService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(WebSocketConstants.SIMPLE_BLOKER);
        config.setApplicationDestinationPrefixes(WebSocketConstants.DESTINATION_PREFIX);
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
    	registry.addEndpoint(WebSocketConstants.ENDPOINT).withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new AppChannelInterceptor(this.loginService));
    }

    //@Override
    //public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
    //    DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
    //    resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
    //    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    //    converter.setObjectMapper(new ObjectMapper());
    //    converter.setContentTypeResolver(resolver);
    //    messageConverters.add(converter);
    //    return false;
    //}
}
