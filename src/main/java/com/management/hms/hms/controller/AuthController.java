package com.management.hms.hms.controller;

import com.management.hms.hms.entity.User;
import com.management.hms.hms.exception.BadRequestException;
import com.management.hms.hms.repository.UserRepository;
import com.management.hms.hms.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginData){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginData.get("email"), loginData.get("password")
                    )
            );
        } catch (BadCredentialsException e){
            throw new BadRequestException("Invalid email or password");
        }
        final String token = jwtUtil.generateToken(loginData.get("email"));
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

}
