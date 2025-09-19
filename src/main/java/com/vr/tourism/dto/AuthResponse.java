package com.vr.tourism.dto;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long expiresInMinutes;
}
