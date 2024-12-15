package com.github.bat333.stockroom.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringConfig {
    @Autowired
    private SecurityFilter apiKeyFilter;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity security) throws Exception {
        return security.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(https-> https.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authoriza->
                        authoriza.anyRequest().authenticated())
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
