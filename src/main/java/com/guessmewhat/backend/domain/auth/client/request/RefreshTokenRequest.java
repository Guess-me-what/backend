package com.guessmewhat.backend.domain.auth.client.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank
        String refreshToken
){}
