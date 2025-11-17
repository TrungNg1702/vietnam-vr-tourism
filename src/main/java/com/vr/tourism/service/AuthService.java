package com.vr.tourism.service;


import com.vr.tourism.dto.*;
import com.vr.tourism.entity.Role;
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

        Role role = userRepo.count() == 0 ? Role.ADMIN : Role.USER;

        User u = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .role(role)
                .build();

        userRepo.save(u);

        String token = jwtService.generateToken(u.getUsername(), u.getRole().name());

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresInMinutes(Long.parseLong(System.getProperty("jwt.expiration-minutes", "60")))
                .userID(u.getId())
                .build();
    }


    public AuthResponse login(AuthRequest req) {
        var authToken = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        authManager.authenticate(authToken);

        User u = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtService.generateToken(u.getUsername(), u.getRole().name());

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresInMinutes(Long.parseLong(System.getProperty("jwt.expiration-minutes", "60")))
                .userID(u.getId())
                .build();
    }

}
