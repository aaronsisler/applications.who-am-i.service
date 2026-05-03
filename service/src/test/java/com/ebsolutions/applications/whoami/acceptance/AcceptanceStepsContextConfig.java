package com.ebsolutions.applications.whoami.acceptance;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

@TestConfiguration
public class AcceptanceStepsContextConfig {

  @Bean
  public RestClient createRestClient(@Value("${server.url}") String serverUrl,
                                     @Value("${server.port}") String serverPort) {

    return RestClient.builder()
        .baseUrl(String.format("%s:%s", serverUrl, serverPort))
        .build();
  }

  @Bean
  public JdbcTemplate createJdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}

