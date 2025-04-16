package com.guessmewhat.backend.domain.auth.client.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 1, max = 6)
        String nickname,

        @NotBlank
        @Size(min = 8)
        String password
){}
