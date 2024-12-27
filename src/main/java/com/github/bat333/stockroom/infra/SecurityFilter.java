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

        if (API_KEY_VALUE.equals(this.key(request))) {
            var userAuth = new UsernamePasswordAuthenticationToken(null,null,null);
            SecurityContextHolder.getContext().setAuthentication(userAuth);
            filterChain.doFilter(request, response);
        }else{
            var userAuth = new UsernamePasswordAuthenticationToken(null,null,null);
            SecurityContextHolder.getContext().setAuthentication(userAuth);
            filterChain.doFilter(request, response);
        }
    }

    private String key(HttpServletRequest request) {
        String apiKey = request.getHeader(API_KEY_HEADER);

        System.out.println(apiKey);
        if (apiKey == null || apiKey.isEmpty()) {
            return "API Key is missing or invalid";
        }

        return apiKey;
    }
}