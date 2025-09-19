package com.vr.tourism.service;


import com.vr.tourism.dto.*;
import com.vr.tourism.entity.User;
import com.vr.tourism.repository.UserRepository;
import com.vr.tourism.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (req.getEmail() != null && userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User u = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .role("USER")
                .build();
        userRepo.save(u);
        String token = jwtService.generateToken(u.getUsername(), u.getRole());
        return new AuthResponse(token, "Bearer", Long.parseLong(System.getProperty("jwt.expiration-minutes", "60")));
    }

    public AuthResponse login(AuthRequest req) {
        // authenticate via AuthenticationManager to let Spring check credentials
        var authToken = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        authManager.authenticate(authToken); // will throw if bad creds
        User u = userRepo.findByUsername(req.getUsername()).orElseThrow();
        String token = jwtService.generateToken(u.getUsername(), u.getRole());
        return new AuthResponse(token, "Bearer", Long.parseLong(System.getProperty("jwt.expiration-minutes", "60")));
    }
}
