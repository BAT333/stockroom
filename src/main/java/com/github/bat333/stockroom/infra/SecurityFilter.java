package com.github.bat333.stockroom.infra;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (autenticate(request) || SecurityContextHolder.getContext().getAuthentication().getName()!=null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    "localUser",
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);

        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Acesso negado");
            response.sendRedirect("/ui/form");
        }

    }

    private boolean autenticate(HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        return request.getRequestURI().startsWith("/ui") ||  request.getRequestURI().startsWith("/js");
    }
}
