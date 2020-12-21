package com.app.dflow.object;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String client;
    private String value;
}
