package com.operatoroverloaded.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.operatoroverloaded.hotel.models.Logon;
import com.operatoroverloaded.hotel.security.JwtService;
import com.operatoroverloaded.hotel.stores.logonstore.LogonStore;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JsonNode json) {
        String email = json.get("email").asText();
        String password = json.get("password").asText();
        LogonStore logonStore = LogonStore.getInstance();
        Logon user = logonStore.tryLogon(email, password);

        if (user == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String jwt = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok().body(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody JsonNode json) {
        String email = json.get("email").asText();
        String password = json.get("password").asText();
        LogonStore logonStore = LogonStore.getInstance();
        Logon user = logonStore.addNewUser("ADMIN", email, password);
        
        if (user == null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        String jwt = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok().body(jwt);
    }
}