package org.example.task_management.controllers;

import org.example.task_management.DTO.AuthRequest;
import org.example.task_management.services.AuthService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthRequest request) {
        Map<String, String> result = authService.register(request);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request) {
        Map<String, String> result = authService.login(request);
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/redoc")
    public ResponseEntity<String> redoc() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/redoc.html");
        String htmlContent = StreamUtils.copyToString(
                resource.getInputStream(),
                StandardCharsets.UTF_8
        );
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlContent);
    }


}