package org.example.task_management.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class DocumentationController {
    @GetMapping(value = "/redoc", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> redoc() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/redoc.html");
        String htmlContent = StreamUtils.copyToString(
                resource.getInputStream(),
                StandardCharsets.UTF_8
        );
        return ResponseEntity.ok(htmlContent);
    }
}