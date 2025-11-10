package com.ecsite.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for Customer Service
 *
 * <p>Configures: - Stateless session management (JWT-based) - Endpoint authorization rules - CSRF
 * protection (disabled for stateless API)
 *
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Configure security filter chain
   *
   * @param http HttpSecurity
   * @return SecurityFilterChain
   * @throws Exception if configuration fails
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/actuator/**")
                    .permitAll()
                    .requestMatchers("/api/v1/profile/**")
                    .authenticated()
                    .anyRequest()
                    .authenticated());

    return http.build();
  }
}
