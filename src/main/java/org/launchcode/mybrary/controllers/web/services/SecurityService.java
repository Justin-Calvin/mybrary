package org.launchcode.mybrary.controllers.web.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}