package com.appointment.controller;

import com.appointment.common.dto.auth.LoginRequestDto;
import com.appointment.common.dto.auth.LoginResponseDto;
import com.appointment.common.exceptions.EntityNotFoundException;
import com.appointment.common.security.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.appointment.common.constants.Route.AUTH_URL;

@RestController
@RequestMapping(value = {AUTH_URL})
@Slf4j
@AllArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Validated @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authenticationService.login(loginRequestDto));
    }

    @PostMapping("/token/refresh")
    public LoginResponseDto refreshToken(@RequestBody @Validated LoginResponseDto loginResponseDto)
            throws EntityNotFoundException {
        return authenticationService.refreshToken(loginResponseDto.getRefreshToken());
    }

}
