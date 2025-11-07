package com.fiap.fleetzone.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final String devStaticToken;
    private final String devStaticUserEmail;

    public JwtAuthenticationFilter(TokenService tokenService, UserDetailsService uds) {
        this(tokenService, uds, null, null);
    }

    public JwtAuthenticationFilter(TokenService tokenService, UserDetailsService uds, String devStaticToken, String devStaticUserEmail) {
        this.tokenService = tokenService;
        this.userDetailsService = uds;
        this.devStaticToken = devStaticToken == null ? "" : devStaticToken;
        this.devStaticUserEmail = devStaticUserEmail == null ? "" : devStaticUserEmail;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }
        if (token != null) {
            try {
                // Dev static-token shortcut: if provided and matches, authenticate as configured user
                if (!devStaticToken.isBlank() && token.equals(devStaticToken) && !devStaticUserEmail.isBlank()) {
                    var userDetails = userDetailsService.loadUserByUsername(devStaticUserEmail.toLowerCase());
                    var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    String email = tokenService.getEmail(token);
                    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        var userDetails = userDetailsService.loadUserByUsername(email);
                        var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            } catch (Exception ex) {
                // invalid token: ignore and continue unauthenticated
            }
        }
        filterChain.doFilter(request, response);
    }
}
