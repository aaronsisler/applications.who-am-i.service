package com.ebsolutions.applications.whoami.testfixture.config.persistence;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.persistence")
public record PersistenceConfigProperties(String database,
                                          String schema,
                                          String username,
                                          String password) {
}
