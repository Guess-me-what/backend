package com.guessmewhat.backend.domain.auth.client.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank
        @Email
        String email,
        String nickname,
        String password
){}
