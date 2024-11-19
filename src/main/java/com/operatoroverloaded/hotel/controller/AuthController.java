package com.operatoroverloaded.hotel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.operatoroverloaded.hotel.stores.logonstore.LogonStore;
import com.operatoroverloaded.hotel.utils.JwtUtil;

import java.util.Map;

@RestController
public class AuthController {

    // Public route to simulate login and generate a token
    @PostMapping("/public/login")
    public String login(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String password = request.get("password");

        // Simulate authentication (replace with actual user verification logic)
        if (LogonStore.getInstance().tryLogon(userId, password) != null) {
            return JwtUtil.generateToken(userId);
        }
        return "Invalid credentials";
    }

    // Protected route to demonstrate authentication
    @GetMapping("/secure/profile")
    public String profile() {
        return "Secure user profile data";
    }
}
