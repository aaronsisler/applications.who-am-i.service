package com.ebsolutions.applications.whoami.acceptance.persistence;

import com.ebsolutions.applications.whoami.acceptance.AcceptanceStepsContext;
import com.zaxxer.hikari.HikariDataSource;
import io.cucumber.java.en.And;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

public class PersistenceStepsContext extends AcceptanceStepsContext {
  @Autowired
  private DataSource dataSource;

  @And("the database is unavailable")
  public void theDatabaseIsUnavailable() {
    persistenceContainer.stop();

    HikariDataSource hikari = (HikariDataSource) dataSource;
    hikari.getHikariPoolMXBean().softEvictConnections();
  }

  @And("the database is available")
  public void theDatabaseIsAvailable() {
    persistenceContainer.start();
  }
}
