package org.launchcode.mybrary.controllers;

import org.launchcode.mybrary.models.data.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "search")
public class SearchController {

    @Autowired
    private ItemDao itemDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        return "search/index";
    }

}
