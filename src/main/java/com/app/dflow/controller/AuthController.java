package com.app.dflow.controller;


import com.app.dflow.model.AuthenticationRequest;
import com.app.dflow.model.AuthenticationResponse;
import com.app.dflow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authenticateService;

    @GetMapping("/test")
    public String getRoomById() {
        return "test";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(new AuthenticationResponse(authenticateService.createAuthenticationToken
                                (authenticationRequest.getUsername(), authenticationRequest.getPassword())));
    }
}
