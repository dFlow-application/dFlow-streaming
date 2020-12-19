package com.app.dflow.configuration;

import com.app.dflow.handler.ServerSocketHandler;
import com.app.dflow.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    WebSocketService webSocketService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ServerSocketHandler(webSocketService), "/socket").setAllowedOrigins("*");
    }

    @Bean
    public AbstractWebSocketHandler WebSocketHandler() {
        return new ServerSocketHandler(webSocketService);
    }
}
