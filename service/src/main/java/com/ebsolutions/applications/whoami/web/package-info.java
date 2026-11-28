/**
 * Infrastructure that helps the application talk HTTP: error responses, CORS, serving the API
 * contracts, binding contract enums, and conversions shared by web mappers.
 *
 * <p>This module must not know about any business module, which is why it declares no allowed
 * dependencies. Only the {@code mapping} named interface may be used by other modules.
 */
@ApplicationModule(
    displayName = "Web infrastructure",
    allowedDependencies = {})
@NullMarked
package com.ebsolutions.applications.whoami.web;

import org.jspecify.annotations.NullMarked;
import org.springframework.modulith.ApplicationModule;
