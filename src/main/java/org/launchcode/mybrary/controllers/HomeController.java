package org.launchcode.mybrary.controllers;

import org.launchcode.mybrary.models.Item;
import org.launchcode.mybrary.models.User;
import org.launchcode.mybrary.models.data.ItemDao;
import org.launchcode.mybrary.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;



@Controller
public class HomeController {

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity security) throws Exception {
            getHttp()
                    .csrf().disable();
            security.httpBasic().disable();
        }

    }

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String Landing() {

        return "redirect:/login";
    }


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddForm(Model model) {

        model.addAttribute(new Item());

        return "add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Item newItem, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "add";
        }

        itemDao.save(newItem);
        model.addAttribute("success","Item has been registered!");
        return "add";
    }

    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String confirmRemoveItem(Model model, @PathVariable int id) {

        model.addAttribute("item",itemDao.findById(id));

        return "remove";
    }

    @RequestMapping(value = "remove/{id}", method = RequestMethod.POST)
    public String processRemoveItem(@PathVariable int id) {

        Item bye = itemDao.findById(id);
        itemDao.delete(bye);

        return "redirect:/home";
    }

    @RequestMapping(value = "edit/{itemId}", method = RequestMethod.GET)
    public String displayEditCheeseForm(Model model, @PathVariable int itemId) {

        model.addAttribute("item", itemDao.findById(itemId));

        return "edit";
    }

    @RequestMapping(value = "edit/{itemId}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable int itemId,
                                  @ModelAttribute @Valid Item newItem,
                                  Errors errors) {

        if (errors.hasErrors()) {
            return "edit";
        }

        Item editedItem = itemDao.findById(itemId);
        editedItem.setTitle(newItem.getTitle());
        editedItem.setAuthor(newItem.getAuthor());
        editedItem.setStock(newItem.getStock());
        itemDao.save(editedItem);

        return "redirect:/search/inventory";
    }


}
