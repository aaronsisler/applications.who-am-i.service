package com.ebsolutions.applications.whoami.acceptance.persistence;

import com.ebsolutions.applications.whoami.acceptance.config.persistence.PersistenceContainer;
import com.ebsolutions.applications.whoami.common.CommonContext;
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
