package com.ebsolutions.applications.whoami.acceptance.persistence.hook;

import io.cucumber.java.Before;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class PersistenceCleanupHook {
  private final JdbcTemplate jdbcTemplate;
  private final List<String> tablesToTruncate = List.of("app_user");

  @Before
  public void truncateDatabase() {
    jdbcTemplate.execute(generateTruncateQuery());
  }

  private String generateTruncateQuery() {
    StringBuilder queryBuilder = new StringBuilder();

    for (String table : tablesToTruncate) {
      queryBuilder
          .append("TRUNCATE TABLE ")
          .append(table)
          .append(" RESTART IDENTITY CASCADE;")
          .append(" ");
    }

    return queryBuilder.toString();
  }
}
