package com.ebsolutions.applications.whoami.testfixture.config.persistence;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@TestConfiguration
@RequiredArgsConstructor
public class PersistenceConfig {

  @Bean
  public DataSource dataSource(PersistenceContainer container,
                               PersistenceConfigProperties persistenceConfigProperties) {

    return DataSourceBuilder.create()
        .driverClassName(container.getDriverClassName())
        .url(container.getJdbcUrl() + "&currentSchema=" + persistenceConfigProperties.schema())
        .username(container.getUsername())
        .password(container.getPassword())
        .build();
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
