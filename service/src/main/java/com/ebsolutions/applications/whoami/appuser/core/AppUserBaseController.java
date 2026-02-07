package com.ebsolutions.applications.whoami.appuser.core;

import com.ebsolutions.applications.whoami.config.ControllerPathMappings;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ControllerPathMappings.APP_USERS)
public abstract class AppUserBaseController {
  /**
   * This abstract class should not be instantiated
   */
  protected AppUserBaseController() {
  }
}
