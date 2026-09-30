package com.example.employeepayroll.controller;

import com.example.employeepayroll.dto.LoginRequest;
import com.example.employeepayroll.dto.RegisterRequest;
import com.example.employeepayroll.entity.User;
import com.example.employeepayroll.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return Map.of("token", token);
    }
}