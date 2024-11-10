package com.assignment.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-ui/index.html").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(customizer -> customizer
                        .realmName("Access to management system")
                )
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Stateless session management

        return http.build();
    }
}