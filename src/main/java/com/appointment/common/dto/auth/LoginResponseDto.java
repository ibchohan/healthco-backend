package com.appointment.common.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponseDto {

    private String token;

    private long expiresIn;

    @NotBlank(message = "refresh token cannot be null or empty")
    private String refreshToken;
}
