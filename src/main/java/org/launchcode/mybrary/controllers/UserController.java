package org.launchcode.mybrary.controllers;

import org.launchcode.mybrary.controllers.services.UserValidator;
import org.launchcode.mybrary.controllers.services.SecurityService;
import org.launchcode.mybrary.controllers.services.UserService;
import org.launchcode.mybrary.models.*;
import org.launchcode.mybrary.models.data.BookOrderDao;
import org.launchcode.mybrary.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import java.util.Set;

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
    @Autowired
    private BookOrderDao bookOrderDao;



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
        if (userForm.getUsername().length() < 5 || userForm.getUsername().length() > 40) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(userForm.getUsername()) == null) {
            errors.rejectValue("username", "InvalidUser.userForm.username");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (userForm.getPassword().length() < 5 || userForm.getPassword().length() > 40) {
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
    @GetMapping(value = "home")
    public String HomeAccess(Model model) {

        User user = userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user",user);
        model.addAttribute("username",user.getUsername());

        return "user/home";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());

        return "user/addUser";
    }
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User userForm, BindingResult bindingResult, Errors errors) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/addUser";
        }
        if (errors.hasErrors()) {
            return "user/addUser";
        }

        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/home";
    }
    @GetMapping(value = "user/{id}/edit")
    public String displayuserEdit(Model model, @PathVariable int id) {

        User user = userDao.findById(id);
        model.addAttribute("user", user);

        return "user/editUser";
    }
    @PostMapping(value = "user/{id}/edit")
    public String processuserEdit(Model model, @PathVariable int id,
                                  @ModelAttribute @Valid User newUser, Errors errors) {

        if (errors.hasErrors()) {
            return "user/editUser";
        }
        User edit = userDao.findById(id);
        edit.setUsername(newUser.getUsername());
        edit.setPassword(newUser.getPassword());
        userService.save(edit);

        return "redirect:/";
    }
    @GetMapping(value = "user/{id}/remove")
    public String confirmRemoveuser(Model model, @PathVariable int id) {

        User user = userDao.findById(id);
        model.addAttribute("user",user);

        return "user/remove";
    }
    @PostMapping(value = "user/{id}/remove")
    public String processRemoveuser(Model model, @PathVariable int id) {

        User bye = userDao.findById(id);
        userDao.delete(bye);

        return "redirect:/";
    }


    @GetMapping(value = "user/{id}/orders")
    public String displayOrders(Model model, @PathVariable int id) {

        model.addAttribute("username",userDao.findById(id).getUsername());
        model.addAttribute("user",userDao.findById(id));
        model.addAttribute("orders",userDao.findById(id).getOrders());


        return "user/orders";
    }
    @GetMapping(value = "user/{id}/order/add")
    public String addOrderForm(Model model, @PathVariable int id) {

        model.addAttribute("user",userDao.findById(id));
        model.addAttribute("order", new BookOrder());

        return "user/order/addOrder";
    }
    @PostMapping(value = "user/{id}/order/add")
    public String processAddOrder(Model model, @PathVariable int id,
                                     @ModelAttribute @Valid BookOrder newOrder,
                                     Errors errors) {
        if (errors.hasErrors()) {
            return "user/order/addOrder";
        }
        BookOrder order = bookOrderDao.save(newOrder);
        User user = userDao.findById(id);
        user.getOrders().add(order);
        userDao.save(user);

        model.addAttribute("user",user);
        model.addAttribute("orders",user.getOrders());

        return "user/orders";
    }
    @GetMapping(value = "user/order/remove/{id}")
    public String confirmRemovefavorite(Model model, @PathVariable int id) {

        BookOrder order = bookOrderDao.findById(id);
        model.addAttribute("bookOrder",order);

        return "user/order/removeOrder";
    }
    @PostMapping(value = "user/order/remove/{id}")
    public String processRemovefavorite(Model model, @PathVariable int id) {

        BookOrder bye = bookOrderDao.findById(id);
        bookOrderDao.delete(bye);

        return "redirect:/home";
    }
    @GetMapping(value = "user/order/edit/{id}")
    public String displayfavoriteEdit(Model model, @PathVariable int id) {


        BookOrder order = bookOrderDao.findById(id);
        model.addAttribute("bookOrder",order);

        return "user/order/editOrder";
    }
    @PostMapping(value = "user/order/edit/{id}")
    public String processfavoriteEdit(Model model, @PathVariable int id,
                                      @ModelAttribute @Valid BookOrder newOrder, Errors errors) {

        if (errors.hasErrors()) {
            return "user/order/editOrder";
        }
        BookOrder edit = bookOrderDao.findById(id);
        edit.setTitle(newOrder.getTitle());
        edit.setAuthor(newOrder.getAuthor());

        bookOrderDao.save(edit);

        return "redirect:/home";
    }
    @GetMapping(value = "user/orders")
    public String showAllBookOrders(Model model) {

        model.addAttribute("bookOrders",bookOrderDao.findAll());

        return "user/order/allOrders";
    }
    @GetMapping(value = "user/admin/manageUsers")
    public String manageUsers(Model model) {

        model.addAttribute("users", userDao.findAll());

        return "user/users";
    }
    @GetMapping(value = "user/admin/manageOrders")
    public String manageBookOrders(Model model) {

        model.addAttribute("bookOrders",bookOrderDao.findAll());
        model.addAttribute("user",userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        return "user/order/manage";
    }
    @GetMapping(value = "user/admin/manageOrders/clear")
    public String confirmClearOrders(Model model) {

        return "user/order/clear";
    }
    @PostMapping(value = "user/admin/manageOrders/clear")
    public String processClearOrders(Model model) {

        for (BookOrder order : bookOrderDao.findAll()) {
            if (order.isFilled() == true) {
                bookOrderDao.delete(order);
            }
        }

        return "redirect:/user/admin/manageOrders";

    }
    @GetMapping(value = "/user/admin/updateOrder/{id}")
    public String updateOrderForm(Model model, @PathVariable int id) {

        BookOrder order = bookOrderDao.findById(id);
        model.addAttribute("bookOrder",order);

        return "user/order/update";
    }
    @PostMapping(value = "/user/admin/updateOrder/{id}")
    public String processUpdateForm(Model model, @PathVariable int id,
                                    @ModelAttribute @Valid BookOrder newOrder, Errors errors) {

        if (errors.hasErrors()) {
            return "user/order/update";
        }
        BookOrder edit = bookOrderDao.findById(id);
        edit.setTitle(newOrder.getTitle());
        edit.setAuthor(newOrder.getAuthor());
        edit.setPlaced(newOrder.isPlaced());
        edit.setFilled(newOrder.isFilled());

        bookOrderDao.save(edit);

        return "redirect:/user/admin/manageOrders";
    }

}
