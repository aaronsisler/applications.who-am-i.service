package com.ebsolutions.applications.whoami.core.system;

import com.ebsolutions.applications.whoami.config.ApiPaths;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ApiPaths.SYSTEM_TEST_PATH)
public abstract class SystemTestBaseController {
  /**
   * This abstract class should not be instantiated
   */
  protected SystemTestBaseController() {
  }
}