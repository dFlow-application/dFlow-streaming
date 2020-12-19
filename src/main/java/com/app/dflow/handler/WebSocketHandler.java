package com.app.dflow.handler;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocked class used to handler messages form a session.
 */
public class WebSocketHandler extends AbstractWebSocketHandler {

    /**
     * List of available sessions.
     */
    List<WebSocketSession> webSessions = new CopyOnWriteArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InterruptedException {
        for (WebSocketSession currentSession : webSessions) {
            if (currentSession.isOpen() && !currentSession.getId().equals(session.getId())) {
                currentSession.sendMessage(message);
            }
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received");
        session.sendMessage(message);
    }

    /**
     * Session added to available list after connection was configured.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSessions.add(session);
    }
}
