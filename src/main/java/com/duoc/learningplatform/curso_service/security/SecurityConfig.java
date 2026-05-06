package com.duoc.learningplatform.curso_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions().disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/cursos/*/exists").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                // cursos
                .requestMatchers(HttpMethod.POST, "/api/cursos/**").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.PUT, "/api/cursos/**").hasRole("PROFESOR")
                .requestMatchers(HttpMethod.DELETE, "/api/cursos/**").hasRole("PROFESOR")

                // inscripciones
                .requestMatchers(HttpMethod.POST, "/api/inscripciones/**").hasRole("ALUMNO")

                .anyRequest().authenticated()
            )

            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil),
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}