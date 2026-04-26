package com.ebsolutions.applications.whoami.acceptance.common;

import com.ebsolutions.applications.whoami.acceptance.AcceptanceStepsContext;
import io.cucumber.java.en.And;

public class PersistenceStepsContext extends AcceptanceStepsContext {
  @And("the database is unavailable")
  public void theDatabaseIsUnavailable() {
    persistenceContainer.stop();
  }
}
