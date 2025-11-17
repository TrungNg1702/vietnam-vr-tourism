package com.vr.tourism.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long expiresInMinutes;
    private Long userID;

}
