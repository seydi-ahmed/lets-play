package com.letsplay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.User;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactive CSRF pour les API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/products").permitAll() // Accès public
                        .requestMatchers("/api/users").permitAll() // Accès public
                        .requestMatchers(HttpMethod.PUT, "/api/products/**").authenticated() // PUT restreint
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").authenticated() // DELETE restreint
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").authenticated() // PUT restreint
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").authenticated() // DELETE restreint
                        .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                )
                .httpBasic(Customizer.withDefaults()); // Active Basic Auth

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("USER") // Ou "ADMIN" selon tes besoins
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
