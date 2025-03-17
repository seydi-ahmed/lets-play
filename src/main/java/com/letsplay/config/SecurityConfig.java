package com.letsplay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Nouvelle manière de désactiver CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/products").permitAll() // Autorise l'accès à /api/products sans authentification
                .requestMatchers("/api/users").permitAll() // Autorise l'accès à /api/users sans authentification
                .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}