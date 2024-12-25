package com.github.bat333.stockroom.infra;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String API_KEY_VALUE = "minha-chave-secreta";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String apiKey = request.getHeader(API_KEY_HEADER);
        if (API_KEY_VALUE.equals(apiKey)) {
            var userAuth = new UsernamePasswordAuthenticationToken(null,null,null);
            SecurityContextHolder.getContext().setAuthentication(userAuth);
            filterChain.doFilter(request, response);
        } else {
          //  response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          //  response.sendRedirect("http://localhost:4200/404");
            var userAuth = new UsernamePasswordAuthenticationToken(null,null,null);
            SecurityContextHolder.getContext().setAuthentication(userAuth);
            filterChain.doFilter(request, response);
        }
    }
}