package com.ebsolutions.applications.whoami.testfixture.persistence;

import com.ebsolutions.applications.whoami.config.persistence.PersistenceContainer;
import com.ebsolutions.applications.whoami.testfixture.context.CommonContext;
import io.cucumber.java.en.And;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersistenceStepsContext extends CommonContext {
  private final PersistenceContainer persistenceContainer;

  @And("the database is unavailable")
  public void theDatabaseIsUnavailable() {
    persistenceContainer.stop();
  }

  @And("the database is available")
  public void theDatabaseIsAvailable() {
    persistenceContainer.start();
  }
}
