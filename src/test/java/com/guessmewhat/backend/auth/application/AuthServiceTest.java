package com.guessmewhat.backend.auth.application;

import com.guessmewhat.backend.domain.auth.client.request.SignInRequest;
import com.guessmewhat.backend.domain.auth.client.request.SignUpRequest;
import com.guessmewhat.backend.domain.auth.service.AuthService;
import com.guessmewhat.backend.domain.auth.service.response.RefreshTokenResponse;
import com.guessmewhat.backend.domain.user.domain.entity.UserEntity;
import com.guessmewhat.backend.domain.user.domain.enums.UserRole;
import com.guessmewhat.backend.domain.user.domain.repository.jpa.UserRepository;
import com.guessmewhat.backend.domain.user.exception.PasswordWrongException;
import com.guessmewhat.backend.domain.user.exception.UserExistException;
import com.guessmewhat.backend.domain.user.exception.UserNotFoundException;
import com.guessmewhat.backend.global.security.jwt.JwtProvider;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 중복")
    void signUp_duplicateEmail_throwsException() {
        SignUpRequest request = new SignUpRequest("test@example.com", "닉네임", "password");

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(mock(UserEntity.class)));

        assertThrows(UserExistException.class, () -> authService.signUp(request));
    }

    @Test
    @DisplayName("로그인 실패 - 유저 없음")
    void signIn_userNotFound_throwsException() {
        SignInRequest request = new SignInRequest("notfound@example.com", "password");

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.signIn(request));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void signIn_wrongPassword_throwsException() {
        String email = "test@example.com";
        String inputPassword = "wrong-password";
        String encodedPassword = "encoded-password";

        UserEntity mockUser = UserEntity.builder()
                .email(email)
                .password(encodedPassword)
                .nickname("닉네임")
                .userRole(UserRole.USER)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(userRepository.getByEmail(email)).thenReturn(mockUser);
        when(passwordEncoder.matches(inputPassword, encodedPassword)).thenReturn(false);

        SignInRequest request = new SignInRequest(email, inputPassword);
        assertThrows(PasswordWrongException.class, () -> authService.signIn(request));
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void refresh_success() {
        String refreshToken = "some-refresh-token";

        Claims mockClaims = mock(Claims.class);
        when(mockClaims.getSubject()).thenReturn("user@example.com");

        JwsHeader<?> mockHeader = mock(JwsHeader.class);
        when(mockHeader.get("authority")).thenReturn(UserRole.USER);

        Jws<Claims> jwsClaims = mock(Jws.class);
        when(jwsClaims.getBody()).thenReturn(mockClaims);
        when(jwsClaims.getHeader()).thenReturn(mockHeader);

        when(jwtProvider.getClaims(refreshToken)).thenReturn(jwsClaims);
        when(jwtProvider.generateAccessToken("user@example.com", UserRole.USER)).thenReturn("new-access-token");

        RefreshTokenResponse response = authService.refresh(refreshToken);
        assertEquals("new-access-token", response.accessToken());
    }

}
