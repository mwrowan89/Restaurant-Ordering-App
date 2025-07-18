package com.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()       // Disable CSRF if you want POST requests to work without CSRF token
                .authorizeHttpRequests(auth -> auth
                
                // Implement redirect user to login on protected endpoints
                // .requestMatchers("/api/orders").hasRole("USER") 
                // .requestMatchers("/api/menuItems/edit/**").hasRole("ADMIN")

             
                .anyRequest().permitAll()); // Allow all requests without auth
                // ^^^^^ remove this when auth is implemented ^^^^^^

        return http.build();
    }
}
