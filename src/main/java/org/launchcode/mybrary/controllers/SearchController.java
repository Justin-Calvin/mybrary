package org.launchcode.mybrary.controllers;

import org.launchcode.mybrary.models.Item;
import org.launchcode.mybrary.models.data.ItemDao;
import org.launchcode.mybrary.models.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "search")
public class SearchController {


    @Autowired
    private ItemDao itemDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute(new SearchForm());

        return "search/search";
    }

    @RequestMapping(value = "results")
    public String search(Model model,
                         @ModelAttribute @Valid SearchForm searchForm) {

        if (searchForm.getTitleTerm().length() == 0) {
            model.addAttribute("error","Field Must Have 1-100 characters");
            return "search/search";
        }

        ArrayList<Item> titles;
        Integer count;
        titles = itemDao.findByTitle(searchForm.getTitleTerm());
        count = titles.size();

        model.addAttribute("count", count);
        model.addAttribute("titles", titles);

        return "search/results";
    }

    @RequestMapping(value = "inventory")
    public String search(Model model) {

        model.addAttribute("count",itemDao.count());
        model.addAttribute("items",itemDao.findAll());

        return "search/inventory";
    }

}
