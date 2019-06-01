package org.launchcode.mybrary.controllers;

import org.launchcode.mybrary.models.Book;
import org.launchcode.mybrary.models.User;
import org.launchcode.mybrary.models.data.BookDao;
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
public class BookController {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddForm(Model model) {

        model.addAttribute(new Book());

        return "add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Book newbook, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "add";
        }

        bookDao.save(newbook);
        model.addAttribute("success","book has been registered!");
        return "add";
    }

    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String confirmRemovebook(Model model, @PathVariable int id) {

        model.addAttribute("book",bookDao.findById(id));

        return "remove";
    }

    @RequestMapping(value = "remove/{id}", method = RequestMethod.POST)
    public String processRemovebook(@PathVariable int id) {

        Book bye = bookDao.findById(id);
        bookDao.delete(bye);

        return "redirect:/home";
    }

    @RequestMapping(value = "edit/{bookId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int bookId) {

        model.addAttribute("book", bookDao.findById(bookId));

        return "edit";
    }

    @RequestMapping(value = "edit/{bookId}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable int bookId,
                                  @ModelAttribute @Valid Book newBook,
                                  Errors errors) {

        if (errors.hasErrors()) {
            return "edit";
        }

        Book editedBook = bookDao.findById(bookId);
        editedBook.setTitle(newBook.getTitle());
        editedBook.setAuthor(newBook.getAuthor());
        editedBook.setStock(newBook.getStock());
        bookDao.save(editedBook);

        return "redirect:/search/inventory";
    }


}
