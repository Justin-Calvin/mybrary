package org.launchcode.mybrary.controllers;

import org.launchcode.mybrary.models.Item;
import org.launchcode.mybrary.models.data.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;


@Controller
public class HomeController {

    @Autowired
    private ItemDao itemDao;

    @RequestMapping(value = "")
    public String home() {

        return "home";
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

        return "redirect:/search/inventory";
    }

}
