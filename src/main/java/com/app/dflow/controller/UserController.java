package com.app.dflow.controller;

import com.app.dflow.dto.UserDTO;
import com.app.dflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public ResponseEntity<UserDTO> getUserById(long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/remove")
    public ResponseEntity<Boolean> removeUser(long id) {
        return userService.removeUser(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }
}
