package org.launchcode.mybrary.controllers.services;

import org.launchcode.mybrary.models.User;
import org.launchcode.mybrary.models.data.RoleDao;
import org.launchcode.mybrary.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleDao.findAll()));
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {

        return userDao.findByUsername(username);
    }
}
