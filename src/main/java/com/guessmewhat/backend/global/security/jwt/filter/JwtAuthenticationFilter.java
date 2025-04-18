package com.guessmewhat.backend.global.security.jwt.filter;

import com.guessmewhat.backend.global.security.jwt.JwtExtract;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtExtract jwtExtract;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, ExpiredJwtException {

        String token = jwtExtract.extractTokenFromRequest(request);

        if (token != null) {
            SecurityContextHolder.getContext().setAuthentication(jwtExtract.getAuthentication(token));
        }

        filterChain.doFilter(request, response);
    }

}
