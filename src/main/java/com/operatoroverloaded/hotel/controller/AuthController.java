// package com.operatoroverloaded.hotel.controller;

// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.operatoroverloaded.hotel.models.Logon;
// // import com.operatoroverloaded.hotel.security.JwtService;
// import com.operatoroverloaded.hotel.stores.logonstore.LogonStore;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     // private final PasswordEncoder passwordEncoder;
//     // private final JwtService jwtService;

//     // public AuthController(PasswordEncoder passwordEncoder, JwtService jwtService) {
//     //     this.passwordEncoder = passwordEncoder;
//     //     this.jwtService = jwtService;
//     // }

//     @PostMapping("/login")
//     public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
//         LogonStore logonStore = LogonStore.getInstance();
//         Logon user = logonStore.tryLogon(email, password);

//         if (user == null) {
//             return ResponseEntity.status(401).body("Invalid credentials");
//         }

//         String jwt = jwtService.generateToken(user.getEmail());
//         return ResponseEntity.ok().body(jwt);
//     }

//     @PostMapping("/register")
//     public ResponseEntity<?> register(@RequestParam String email, @RequestParam String password, @RequestParam String access) {
//         LogonStore logonStore = LogonStore.getInstance();
//         Logon user = logonStore.addNewUser(access, email, passwordEncoder.encode(password));
//         return ResponseEntity.ok(user);
//     }
// }
