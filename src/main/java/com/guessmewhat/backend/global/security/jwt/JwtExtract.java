package com.guessmewhat.backend.global.security.jwt;

import com.guessmewhat.backend.domain.user.domain.repository.jpa.UserJpaRepository;
import com.guessmewhat.backend.domain.user.dto.User;
import com.guessmewhat.backend.domain.user.exception.UserNotFoundException;
import com.guessmewhat.backend.global.security.auth.CustomUserDetails;
import com.guessmewhat.backend.global.security.jwt.enums.JwtType;
import com.guessmewhat.backend.global.security.jwt.exception.TokenTypeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtExtract {

    private final UserJpaRepository userRepository;
    private final JwtProvider jwtProvider;
    private final User userDTO;

    public Authentication getAuthentication(final String token) {
        final Jws<Claims> claims = jwtProvider.getClaims(token);

        if (isWrongType(claims, JwtType.ACCESS)) {
            throw TokenTypeException.EXCEPTION;
        }

        User user = userRepository
                .findByEmail(claims.getBody().getSubject())
                .map(userDTO::toUser)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        final CustomUserDetails details = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        return extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    public String extractToken(final String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    public boolean isWrongType(final Jws<Claims> claims, final JwtType jwtType) {
        return !(claims.getHeader().get(Header.JWT_TYPE).equals(jwtType.toString()));
    }

}
