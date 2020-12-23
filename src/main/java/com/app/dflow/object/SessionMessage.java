package com.app.dflow.object;

import com.app.dflow.constants.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionMessage {
    private SubscriptionType type;
    private String sender;
    private String receiver;
    private Object data;
    private int roomId;

//    public void setReceiver(String receiver) {
//        this.receiver = HashConvertor.convertToHash(receiver);
//    }
//
//    public void setSender(String sender) {
//        this.sender = HashConvertor.convertToHash(sender);
//    }
}
