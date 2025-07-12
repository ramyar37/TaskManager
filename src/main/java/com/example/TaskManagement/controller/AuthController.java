package com.example.TaskManagement.controller;

import com.example.TaskManagement.entity.LoginRequest;
import com.example.TaskManagement.entity.User;
import com.example.TaskManagement.repository.UserRepository;
import com.example.TaskManagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String accessToken = jwtUtil.generateToken(request.getEmail());
            String refreshToken = UUID.randomUUID().toString();

            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setRefreshToken(refreshToken);
                userRepository.save(user);
            }

            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            ));
        } else {
            return ResponseEntity.badRequest().body("Invalid Credentials!");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request){

        String refreshToken = request.get("refreshToken");

        User user = userRepository.findByRefreshToken(refreshToken).orElseThrow(()-> new RuntimeException("Invalid refresh token"));

        String newAccessToken = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken,
                "refreshToken",refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request){

        String email = request.get("email");

        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found.."));

        user.setRefreshToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Logged out successfully..");
    }

}

