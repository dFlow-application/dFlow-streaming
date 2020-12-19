package com.app.dflow.utils;

import com.app.dflow.object.SessionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocketUtils {

    private WebSocketUtils() {}

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static SessionMessage getObject(final String message) throws Exception {
        return objectMapper.readValue(message, SessionMessage.class);
    }

    public static String getString(final SessionMessage message) throws Exception {
        return objectMapper.writeValueAsString(message);
    }
}
