package com.blubber.homework.hw4.webapp;

import com.blubber.homework.hw4.webapp.service.DatabaseService;
import com.blubber.homework.hw4.webapp.service.SecurityService;

public interface Routable {

    String getMapping();

    void setSecurityService(SecurityService securityService);
    void setDatabaseService(DatabaseService databaseService);
}
