package org.launchcode.mybrary.controllers;

import org.launchcode.mybrary.controllers.web.UserValidator;
import org.launchcode.mybrary.controllers.web.services.SecurityService;
import org.launchcode.mybrary.controllers.web.services.UserService;
import org.launchcode.mybrary.models.Item;
import org.launchcode.mybrary.models.User;
import org.launchcode.mybrary.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String Landing() {

        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());

        return "login";
    }

    @PostMapping("/login")
    public String processlogin(User userForm, Model model, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (userForm.getUsername().length() < 6 || userForm.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(userForm.getUsername()) == null) {
            errors.rejectValue("username", "InvalidUser.userForm.username");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (userForm.getPassword().length() < 8 || userForm.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        if (!bCryptPasswordEncoder.matches(userForm.getPassword(),
                userService.findByUsername(userForm.getUsername()).getPassword())) {
            errors.rejectValue("password","InvalidPass.userForm.password");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty");
        if (!userForm.getPasswordConfirm().equals(userForm.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        if (errors.hasErrors()) {
            return "login";
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userForm.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, userForm.getPassword(), userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            return "redirect:/home";
        }
        model.addAttribute("message","User authentication failed.");
        return "login";
    }
    @RequestMapping(value = "home")
    public String HomeAccess(Model model) {

        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());

        return "home";
    }
    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());

        return "addUser";
    }
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User userForm, BindingResult bindingResult, Errors errors) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "addUser";
        }
        if (errors.hasErrors()) {
            return "addUser";
        }

        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/home";
    }

}
