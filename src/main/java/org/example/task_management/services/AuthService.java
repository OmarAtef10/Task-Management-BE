package org.example.task_management.services;

import org.example.task_management.DTO.AuthRequest;
import org.example.task_management.models.User;
import org.example.task_management.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Map<String, String> register(AuthRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        try {
            userRepository.findUsersByUsername(request.getUsername())
                    .ifPresent(u -> {
                        throw new RuntimeException("User already exists");
                    });
        } catch (RuntimeException e) {
            return Collections.singletonMap("error", e.getMessage());
        }
        userRepository.save(user);
        return Collections.singletonMap("message", "User registered successfully");
    }

    public Map<String, String> login(AuthRequest request) {
        try {
            User user = userRepository.findUsersByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            String token = jwtService.generateToken(user);
            return Collections.singletonMap("token", token);
        } catch (RuntimeException e) {
            return Collections.singletonMap("error", e.getMessage());
        }
    }
}