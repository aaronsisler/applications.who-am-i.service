package com.ebsolutions.applications.whoami.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final CorsConfig corsConfig;

  @Bean
  @SuppressWarnings("java:S4502") // Ignore CSRF disabling
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .cors(c -> c.configurationSource(corsConfig))
        .authorizeHttpRequests(req -> req.anyRequest().permitAll());

    return httpSecurity.build();
  }
}
