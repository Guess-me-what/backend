package com.guessmewhat.backend.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guessmewhat.backend.domain.auth.client.api.AuthController;
import com.guessmewhat.backend.domain.auth.client.request.RefreshTokenRequest;
import com.guessmewhat.backend.domain.auth.client.request.SignInRequest;
import com.guessmewhat.backend.domain.auth.client.request.SignUpRequest;
import com.guessmewhat.backend.domain.auth.service.AuthService;
import com.guessmewhat.backend.domain.auth.service.response.JsonWebTokenResponse;
import com.guessmewhat.backend.domain.auth.service.response.RefreshTokenResponse;
import com.guessmewhat.backend.global.common.response.BaseResponse;
import com.guessmewhat.backend.global.common.response.BaseResponseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class AuthControllerTest {

    private AuthService authService;
    private AuthController authController;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        authService = Mockito.mock(AuthService.class);
        authController = new AuthController(authService);
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp_success() {
        // given
        SignUpRequest request = new SignUpRequest("test@example.com", "닉네임", "password123");

        Mockito.doNothing().when(authService).signUp(any(SignUpRequest.class));

        // when
        BaseResponse result = authController.signUp(request);

        // then
        assertNotNull(result);
        assertEquals(201, result.getStatus());
        assertEquals("sign up successful", result.getMessage());
    }


    @Test
    @DisplayName("로그인 성공")
    void signIn_success() {
        // given
        SignInRequest request = new SignInRequest("test@example.com", "password123");
        JsonWebTokenResponse response = JsonWebTokenResponse.builder()
                .nickname("닉네임")
                .accessToken("access-token")
                .refreshToken("refresh-token")
                .build();
        Mockito.when(authService.signIn(any(SignInRequest.class))).thenReturn(response);

        // when
        BaseResponseData<JsonWebTokenResponse> result = authController.signIn(request);

        // then
        assertNotNull(result);
        assertEquals(200, result.getStatus());
        assertEquals("sign in successful", result.getMessage());
        assertNotNull(result.getData());
        assertEquals("access-token", result.getData().accessToken());
        assertEquals("닉네임", result.getData().nickname());
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void refresh_success() {
        // given
        RefreshTokenRequest request = new RefreshTokenRequest("valid-refresh-token");
        RefreshTokenResponse response = RefreshTokenResponse.builder()
                .accessToken("new-access-token")
                .build();
        Mockito.when(authService.refresh(any())).thenReturn(response);

        // when
        BaseResponseData<RefreshTokenResponse> result = authController.refresh(request);

        // then
        assertNotNull(result);
        assertEquals(200, result.getStatus());
        assertEquals("token reissue successful", result.getMessage());
        assertNotNull(result.getData());
        assertEquals("new-access-token", result.getData().accessToken());
    }
}

