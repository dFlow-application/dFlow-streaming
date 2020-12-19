package com.app.dflow.service;

import com.app.dflow.constants.SubscriptionType;
import com.app.dflow.convertors.HashConvertor;
import com.app.dflow.object.SessionMessage;
import com.app.dflow.utils.WebSocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketService {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketService.class);

    /**
     * Cached subscribers list.
     */
    private Map<String, WebSocketSession> connections = new HashMap<>();

    public void handlerMessage(WebSocketSession session, TextMessage message) throws Exception {
        final SessionMessage sessionMessage = WebSocketUtils.getObject(message.getPayload());
        final String receiverUser = sessionMessage.getReceiver();
        final WebSocketSession targetSession = connections.get(HashConvertor.convertToHash(receiverUser));

        if (targetSession != null && targetSession.isOpen()) {
            sessionMessage.setSender(session.getId());

            final String targetMessage = WebSocketUtils.getString(sessionMessage);
            LOG.info("Sending message {} to {}", targetMessage , receiverUser);

            try {
                targetSession.sendMessage(new TextMessage(targetMessage));
            } catch (Exception e) {
                LOG.warn("Error while sending the message.", e);
            }
        } else {
            LOG.warn("Session {} is closed. Received user {} is offline." +
                            "\n Fail to send message on session {} from sender user: {}.",
                    targetSession.getId(), receiverUser, session.getId(), HashConvertor.convertToHash(session.getId()));
        }

    }

    public void notifySubscribers(WebSocketSession subscriber) {
        final SessionMessage sessionMessage = new SessionMessage();
        sessionMessage.setType(SubscriptionType.INIT);
        sessionMessage.setSender(subscriber.getId());

        connections.values().forEach(currentSession -> {
            try {
                currentSession.sendMessage(new TextMessage(WebSocketUtils.getString(sessionMessage)));
            } catch (Exception e) {
                LOG.warn("Error while sending the message.", e);
            }
        });

        connections.put(HashConvertor.convertToHash(subscriber.getId()), subscriber);

    }

    public void removeSubscriber(WebSocketSession subscriber) {
        connections.remove(HashConvertor.convertToHash(subscriber.getId()));
        disconnectSubscriber(subscriber);
    }

    /**
     * Remove subscriber from connections and notify all subscribers about that.
     * @param subscriber
     */
    private void disconnectSubscriber(WebSocketSession subscriber) {
        final SessionMessage sessionClosedMessage = new SessionMessage();
        sessionClosedMessage.setType(SubscriptionType.LOGOUT);
        sessionClosedMessage.setSender(subscriber.getId());

        connections.values().forEach(currentSession -> {
            try {
                currentSession.sendMessage(new TextMessage(WebSocketUtils.getString(sessionClosedMessage)));
            } catch (Exception e) {
                LOG.warn("Error while sending the message.", e);
            }
        });
    }
}
