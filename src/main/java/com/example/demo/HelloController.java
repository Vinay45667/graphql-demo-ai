package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // Public endpoint - no login required.
    @GetMapping("/")
    public String home() {
        return "Spring Boot up. Try /graphiql for the GraphQL UI, or /secure (requires login).";
    }

    // Protected endpoint - Spring Security will prompt for credentials.
    @GetMapping("/secure")
    public String secure() {
        return "You are authenticated. Security is working.";
    }
}
