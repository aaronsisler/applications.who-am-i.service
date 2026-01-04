package com.ebsolutions.applications.whoami.appuser.core;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(AppUserBaseController.REQUEST_MAPPING_BASE_PATH)
public abstract class AppUserBaseController {

  public static final String REQUEST_MAPPING_BASE_PATH = "/app-users";

}
