package com.example.employeepayroll.service;

import com.example.employeepayroll.dto.LoginRequest;
import com.example.employeepayroll.dto.RegisterRequest;
import com.example.employeepayroll.entity.Role;
import com.example.employeepayroll.entity.User;
import com.example.employeepayroll.repository.UserRepository;
import com.example.employeepayroll.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.EMPLOYEE);

        return userRepository.save(user);
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        return jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );
    }
}