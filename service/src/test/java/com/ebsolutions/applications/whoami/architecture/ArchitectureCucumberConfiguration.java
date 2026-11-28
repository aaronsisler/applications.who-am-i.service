package com.ebsolutions.applications.whoami.architecture;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * The Spring context for the architecture suite. The steps need no Spring at all, but
 * cucumber-spring requires exactly one @CucumberContextConfiguration on every glue path, so this
 * starts an empty context, which takes milliseconds (ADR 0004).
 */
@CucumberContextConfiguration
@ContextConfiguration(classes = ArchitectureCucumberConfiguration.Empty.class)
public class ArchitectureCucumberConfiguration {

  @Configuration(proxyBeanMethods = false)
  static class Empty {}
}
