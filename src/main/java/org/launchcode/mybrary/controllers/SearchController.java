package org.launchcode.mybrary.controllers;

import org.launchcode.mybrary.models.Book;
import org.launchcode.mybrary.models.data.BookDao;
import org.launchcode.mybrary.models.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "search")
public class SearchController {


    @Autowired
    private BookDao bookDao;

    @GetMapping(value = "")
    public String searchForm(Model model) {

        model.addAttribute(new SearchForm());

        return "search/search";
    }

    @PostMapping(value = "results")
    public String searchResults(Model model,
                         @ModelAttribute @Valid SearchForm searchForm, Errors errors) {

        if (errors.hasErrors()) {
            return "search/search";
        }


        ArrayList<Book> titles;
        ArrayList<Book> authors;
        Integer count;
        titles = bookDao.findByTitle(searchForm.getTitleTerm());
        authors = bookDao.findByAuthor(searchForm.getAuthorTerm());
        count = (authors.size() + titles.size());

        model.addAttribute("authors", authors);
        model.addAttribute("count", count);
        model.addAttribute("titles", titles);

        return "search/results";
    }

    @GetMapping(value = "inventory")
    public String viewInventory(Model model) {

        model.addAttribute("count",bookDao.count());
        model.addAttribute("books",bookDao.findAll());

        return "search/inventory";
    }

}
