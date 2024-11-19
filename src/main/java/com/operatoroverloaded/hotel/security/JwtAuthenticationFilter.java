package com.operatoroverloaded.hotel.security;

import com.operatoroverloaded.hotel.utils.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import io.micrometer.common.lang.NonNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull jakarta.servlet.http.HttpServletRequest request,
    jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
    throws jakarta.servlet.ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract the token

            try {
                Claims claims = JwtUtil.validateToken(token);
                String userId = claims.getSubject(); // Retrieve user ID from the token

                if (userId != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (SignatureException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Error processing token");
                return;
            }
        }

        filterChain.doFilter(request, response); // Proceed with the request
    }
}
