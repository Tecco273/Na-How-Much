package com.soft.backapp.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.soft.backapp.service.MyUserService;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final MyUserService userService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   MyUserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            var userDetails = userService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token)) {

                var claims = jwtService.extractAllClaims(token);

                Integer tokenVersionFromToken = claims.get("tokenVersion", Integer.class);

                var user = userService.getUserByUsername(username);

                if (!tokenVersionFromToken.equals(user.getTokenVersion())) {
                    filterChain.doFilter(request, response);
                    return;
                }

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
