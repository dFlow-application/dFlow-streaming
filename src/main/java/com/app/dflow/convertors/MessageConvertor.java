package com.app.dflow.convertors;

import com.app.dflow.dto.MessageDTO;
import com.app.dflow.object.Message;

public class MessageConvertor {
    public static MessageDTO convertToDTO(Message message) {
        return new MessageDTO(message.getClient(), message.getValue());
    }

    public static Message convertToObject(MessageDTO message) {
        return new Message(message.getClient(), message.getValue());
    }
}