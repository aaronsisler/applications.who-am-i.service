package com.ebsolutions.applications.whoami.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class ContractResourcesConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // URL /contracts/** -> files in src/main/resources/contracts
    registry.addResourceHandler("/contracts/**").addResourceLocations("classpath:/contracts/");
  }
}
