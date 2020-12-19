package com.app.dflow.handler;

import com.app.dflow.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * WebSocked class used to handler messages form a session.
 */
@Component
public class ServerSocketHandler extends AbstractWebSocketHandler {

    private WebSocketService webSocketService;

    private static final Logger LOG = LoggerFactory.getLogger(ServerSocketHandler.class);

    public ServerSocketHandler(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LOG.info("handleTextMessage : {}", message.getPayload());
        webSocketService.handlerMessage(session, message);
    }

    /**
     * Session added to available list after connection was configured.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOG.info("[" + session.getId() + "] Connection established " + session.getId());
        webSocketService.notifySubscribers(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOG.info("[" + session.getId() + "] Connection closed " + session.getId() + " with status: " + status.getReason());
        webSocketService.removeSubscriber(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOG.info("[" + session.getId() + "] Connection error " + session.getId() + " with status: " + exception.getLocalizedMessage());
        webSocketService.removeSubscriber(session);
    }
}
