package com.banhmygac.ordering_system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Value("${app.websocket.endpoint}")
    private String wsEndpoint;

    @Value("${app.websocket.broker}")
    private String brokerPrefix;

    @Value("${app.websocket.app}")
    private String appPrefix;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(brokerPrefix);
        config.setApplicationDestinationPrefixes(appPrefix);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(wsEndpoint)
                .setAllowedOriginPatterns(allowedOrigins)
                .withSockJS();
    }
}