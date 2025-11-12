
package com.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails; // Import UserDetails
import org.springframework.security.core.userdetails.UserDetailsService; // Import UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService; // Inject UserDetailsService

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // Only proceed if SecurityContext is currently empty (not already authenticated)
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = jwtUtils.extractUsername(token);

            if (username != null && jwtUtils.validateToken(token)) {

                // 1. Load UserDetails from database
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // 2. Create Authentication object with loaded user details (including authorities/roles)
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // Credentials are set to null as this is a stateless filter
                                userDetails.getAuthorities()); // Use loaded authorities

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 3. Set the authenticated user in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
}
}


