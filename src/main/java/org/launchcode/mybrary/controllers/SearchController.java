package org.launchcode.mybrary.controllers;

import org.launchcode.mybrary.models.Item;
import org.launchcode.mybrary.models.data.ItemDao;
import org.launchcode.mybrary.models.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
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

        if (searchForm.getKeyword().length() == 0) {
            model.addAttribute("error","Field Must Not Be Empty");
            return "search/search";
        }

        ArrayList<Item> items;
        Integer count;
        items = itemDao.findByTitle(searchForm.getKeyword());
        count = items.size();

        model.addAttribute("count", count);
        model.addAttribute("items", items);

        return "search/results";
    }

    @RequestMapping(value = "inventory")
    public String search(Model model) {

        model.addAttribute("count",itemDao.count());
        model.addAttribute("items",itemDao.findAll());

        return "search/inventory";
    }

}
