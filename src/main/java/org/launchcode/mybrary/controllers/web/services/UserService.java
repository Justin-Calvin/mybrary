package org.launchcode.mybrary.controllers.web.services;

import org.launchcode.mybrary.models.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
