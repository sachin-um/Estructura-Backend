package com.Estructura.API.config;

import com.Estructura.API.model.Token;
import com.Estructura.API.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    private static Boolean checkTokenValidity(Token t) {
        return !t.isExpired() && !t.isRevoked();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        System.out.println(request.getRequestURI());
        if (request.getRequestURI().equals("/api/v1/auth/refresh-token")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt       = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null &&
            SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails =
                this.userDetailsService.loadUserByUsername(
                userEmail);
            Optional<Token> token = tokenRepository.findByToken(jwt);
            Boolean isTokenValid = token.map(
                JwtAuthenticationFilter::checkTokenValidity).orElse(false);
            if (isTokenValid && jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
