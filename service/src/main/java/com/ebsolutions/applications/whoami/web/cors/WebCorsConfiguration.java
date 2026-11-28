package com.ebsolutions.applications.whoami.web.cors;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Lets browser-based frontends call the API. CORS only governs browsers; it does not restrict which
 * networks or clients can reach the service. Not named CorsConfiguration, to avoid confusion with
 * Spring's org.springframework.web.cors.CorsConfiguration.
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CorsProperties.class)
class WebCorsConfiguration implements WebMvcConfigurer {

  private final CorsProperties corsProperties;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins(corsProperties.allowedOrigins().toArray(String[]::new))
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("Authorization", "Content-Type")
        .exposedHeaders("Location")
        .maxAge(3600);
  }
}
