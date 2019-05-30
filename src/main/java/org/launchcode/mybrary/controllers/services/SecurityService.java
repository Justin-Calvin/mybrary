package org.launchcode.mybrary.controllers.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}