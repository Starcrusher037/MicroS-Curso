package com.duoc.learningplatform.curso_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
            .headers(headers -> headers.frameOptions().disable()) // para H2

            .authorizeHttpRequests(auth -> auth
                //  endpoints públicos (comunicación interna y herramientas) bypass
                .requestMatchers("/api/cursos/*/exists").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                //  todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )

            //  filtro JWT
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil),
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}