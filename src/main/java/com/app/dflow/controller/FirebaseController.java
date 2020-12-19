package com.app.dflow.controller;

import com.app.dflow.convertors.MessageConvertor;
import com.app.dflow.dto.MessageDTO;
import com.app.dflow.object.Message;
import com.app.dflow.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirebaseController {

    @Autowired
    FirebaseService FirebaseService;

    @GetMapping("/getMessage")
    MessageDTO getMessage(String author) {
        System.out.println(author);
        Message message = FirebaseService.getMessage(author);
        return message != null ? MessageConvertor.convertToDTO(FirebaseService.getMessage(author)) : null;
    }

    @PostMapping("/addMessage")
    boolean addMessage(@RequestBody MessageDTO message) {
        System.out.println(message.getClient() + " " + message.getValue());
        return FirebaseService.addMessage(MessageConvertor.convertToObject(message));
    }


}
