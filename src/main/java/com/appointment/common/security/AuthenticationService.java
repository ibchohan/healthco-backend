package com.appointment.common.security;

import com.appointment.common.dto.auth.LoginRequestDto;
import com.appointment.common.dto.auth.LoginResponseDto;
import com.appointment.common.event.BusinessEventPublisher;
import com.appointment.common.exceptions.EntityNotFoundException;
import com.appointment.entities.User;
import com.appointment.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final BusinessEventPublisher businessEventPublisher;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        User user = userService.findUserByUsername(loginRequestDto.getUsername()).orElseThrow();
        if (!user.isActive()) {
            throw new DisabledException("User account is deactivated. Please contact your administrator.");
        }
        Pair<String, String> jwtTokenPair = jwtService.generateTokens(user);

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .token(jwtTokenPair.getFirst())
                .expiresIn(jwtService.getAccessTokenExpirationTime())
                .refreshToken(jwtTokenPair.getSecond())
                .build();

        businessEventPublisher.publishUserLoginEvent(user);

        return loginResponseDto;
    }

    public LoginResponseDto refreshToken(String refreshToken) throws EntityNotFoundException {
        Long userId = Long.valueOf(jwtService.extractUserId(refreshToken));
        User user = userService.findById(userId);
        Pair<String, String> tokens = jwtService.generateTokens(user);
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .token(tokens.getFirst())
                .expiresIn(jwtService.getAccessTokenExpirationTime())
                .refreshToken(tokens.getSecond())
                .build();

        businessEventPublisher.publishTokenRefreshEvent(user);

        return loginResponseDto;

    }
}
