// package com.operatoroverloaded.hotel.security;

// import java.util.Date;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;

// import com.operatoroverloaded.hotel.models.Logon;
// import com.operatoroverloaded.hotel.stores.logonstore.LogonStore;

// @Service
// public class JwtService {

//     @Autowired
//     private LogonStore logonStore;

//     public String generateToken(String email) {
//         Logon user = logonStore.getUserByEmail(email);
//         Date now = new Date();
//         return JwtUtil.generateToken(user.getEmail(), user.getPassword(), now, now.getTime() + 1000 * 60 * 60);
//     }

//     public String getUsernameFromToken(String token) {
//         return JwtUtil.getUsernameFromToken(token);
//     }

//     public boolean validateToken(String token, String email) {
//         Logon user = logonStore.getUserByEmail(email);
//         return JwtUtil.validateToken(token, user.getPassword());
//     }

//     public UserDetails loadUserByToken(String token) {
//         String username = getUsernameFromToken(token);
//         return logonStore.getUserByEmail(username);
//     }
// }