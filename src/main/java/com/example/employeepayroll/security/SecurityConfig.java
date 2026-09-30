package com.example.employeepayroll.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/employees/**")
                        .hasAnyRole("ADMIN", "EMPLOYEE")

                        .requestMatchers(HttpMethod.POST, "/api/employees/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/employees/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/api/employees/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/payroll/**")
                        .hasAnyRole("ADMIN", "EMPLOYEE")

                        .requestMatchers(HttpMethod.POST, "/api/payroll/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}