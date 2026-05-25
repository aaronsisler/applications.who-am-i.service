package com.ebsolutions.applications.whoami.acceptance.config.persistence;

public interface PersistenceContainer {

  void start();

  void stop();

  String getDriverClassName();

  String getJdbcUrl();

  String getPassword();

  String getUsername();

}
