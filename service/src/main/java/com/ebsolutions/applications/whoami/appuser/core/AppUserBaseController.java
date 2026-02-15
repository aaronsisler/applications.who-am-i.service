package com.ebsolutions.applications.whoami.appuser.core;

import com.ebsolutions.applications.whoami.config.ApiPaths;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ApiPaths.APP_USERS_PATH)
public abstract class AppUserBaseController {
  /**
   * This abstract class should not be instantiated
   */
  protected AppUserBaseController() {
  }
}
