package org.launchcode.mybrary.controllers;

import org.launchcode.mybrary.models.Customer;
import org.launchcode.mybrary.models.Favorite;
import org.launchcode.mybrary.models.WishOrder;
import org.launchcode.mybrary.models.data.CustomerDao;
import org.launchcode.mybrary.models.data.FavoriteDao;
import org.launchcode.mybrary.models.data.WishOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class CustomerController {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    FavoriteDao favoriteDao;

    @Autowired
    WishOrderDao wishOrderDao;

    @RequestMapping(value = "customer/menu")
    public String customerMenu() {

        return "customer/menu";

    }
    @GetMapping(value = "customer/list")
    public String displayCustomers(Model model) {

        model.addAttribute("customers",customerDao.findAll());

        return "customer/contactList";
    }

    @GetMapping(value = "customer/add")
    public String displayAddCustomer(Model model) {

        model.addAttribute(new Customer());

        return "customer/add";

    }

    @PostMapping(value = "customer/add")
    public String processAddCustomer(@ModelAttribute @Valid Customer newCustomer, Model model, Errors errors) {

        if (errors.hasErrors()) {
            return "customer/add";
        }
        customerDao.save(newCustomer);
        model.addAttribute("success","Customer has been registered!");

        return "customer/add";
    }
    @GetMapping(value = "customer/remove/{id}")
    public String confirmRemoveCustomer(Model model, @PathVariable int id) {

        model.addAttribute("customer", customerDao.findById(id));

        return "customer/remove";
    }
    @PostMapping(value = "customer/remove/{id}")
    public String processRemoveCustomer(Model model, @PathVariable int id) {

        Customer bye = customerDao.findById(id);
        customerDao.delete(bye);

        return "redirect:/customer/list";
    }
    @GetMapping(value = "customer/edit/{id}")
    public String displayCustomerEdit(Model model, @PathVariable int id) {

        model.addAttribute("customer", customerDao.findById(id));

        return "customer/edit";
    }
    @PostMapping(value = "customer/edit/{id}")
    public String processCustomerEdit(Model model, @PathVariable int id,
                                      @ModelAttribute @Valid Customer newCustomer, Errors errors) {

        if (errors.hasErrors()) {
            return "/customer/edit";
        }
        Customer edit = customerDao.findById(id);
        edit.setFirstName(newCustomer.getFirstName());
        edit.setLastName(newCustomer.getLastName());
        edit.setEmail(newCustomer.getEmail());
        edit.setPhone(newCustomer.getPhone());
        customerDao.save(edit);

        return "redirect:/customer/list";
    }
    @GetMapping(value = "customer/{id}/favorites")
    public String displayFavorites(Model model, @PathVariable int id) {

        model.addAttribute("customer",(customerDao.findById(id)));
        model.addAttribute("favorites", (customerDao.findById(id)).getFavorites());

        return "customer/favorites";
    }
    @GetMapping(value = "customer/{id}/favorite/add")
    public String displayAddFavorite(Model model, @PathVariable int id) {

        model.addAttribute("customer",(customerDao.findById(id)));
        model.addAttribute("favorite", new Favorite());

        return "customer/favorite/add";
    }

    @PostMapping(value = "customer/{id}/favorite/add")
    public String processAddFavorite(Model model, @PathVariable int id,
                                     @ModelAttribute @Valid Favorite newFavorite,
                                     Errors errors) {
        if (errors.hasErrors()) {
            return "customer/favorite/add";
        }
        Favorite favorite = favoriteDao.save(newFavorite);
        Customer customer = customerDao.findById(id);
        customer.getFavorites().add(favorite);
        customerDao.save(customer);

        model.addAttribute("customer",(customerDao.findById(id)));
        model.addAttribute("favorites", (customer.getFavorites()));

        return "customer/favorites";
    }

    @GetMapping(value = "customer/favorite/remove/{id}")
    public String confirmRemovefavorite(Model model, @PathVariable int id) {

        Favorite favorite = favoriteDao.findById(id);
        model.addAttribute("favorite",favorite);

        return "customer/favorite/remove";
    }
    @PostMapping(value = "customer/favorite/remove/{id}")
    public String processRemovefavorite(Model model, @PathVariable int id) {

        Favorite bye = favoriteDao.findById(id);
        favoriteDao.delete(bye);

        return "redirect:/customer/list";
    }
    @GetMapping(value = "customer/favorite/edit/{id}")
    public String displayfavoriteEdit(Model model, @PathVariable int id) {

        model.addAttribute("favorite", favoriteDao.findById(id));

        return "customer/favorite/edit";
    }
    @PostMapping(value = "customer/favorite/edit/{id}")
    public String processfavoriteEdit(Model model, @PathVariable int id,
                                      @ModelAttribute @Valid Favorite newFavorite, Errors errors) {

        if (errors.hasErrors()) {
            return "customer/favorite/edit";
        }
        Favorite edit = favoriteDao.findById(id);
        edit.setTitle(newFavorite.getTitle());
        edit.setAuthor(newFavorite.getAuthor());

        favoriteDao.save(edit);

        return "redirect:/customer/list";
    }

    @GetMapping(value = "customer/{id}/wishList")
    public String displayWishlist(Model model, @PathVariable int id) {

        model.addAttribute("customer", customerDao.findById(id));
        model.addAttribute("wishList", (customerDao.findById(id)).getWishList());

        return "customer/wishList";
    }
    @GetMapping(value = "customer/{id}/wish/add")
    public String displayAddWish(Model model, @PathVariable int id) {

        model.addAttribute("customer",(customerDao.findById(id)));
        model.addAttribute("wishOrder", new WishOrder());

        return "customer/wish/add";
    }

    @PostMapping(value = "customer/{id}/wish/add")
    public String processAddWish(Model model, @PathVariable int id,
                                 @ModelAttribute @Valid WishOrder newWishOrder,
                                 Errors errors) {
        if (errors.hasErrors()) {
            return "customer/wish/add";
        }

        WishOrder wish = wishOrderDao.save(newWishOrder);
        Customer customer = customerDao.findById(id);
        customer.getWishList().add(wish);
        customerDao.save(customer);

        model.addAttribute("customer",(customer));
        model.addAttribute("wishList", (customer.getWishList()));

        return "customer/wishList";
    }

    @GetMapping(value = "customer/wish/remove/{id}")
    public String confirmRemoveWish(Model model, @PathVariable int id) {


        model.addAttribute("wishOrder", wishOrderDao.findById(id));

        return "customer/wish/remove";
    }
    @PostMapping(value = "customer/wish/remove/{id}")
    public String processRemoveWish(Model model, @PathVariable int id) {

        WishOrder bye = wishOrderDao.findById(id);
        wishOrderDao.delete(bye);

        return "redirect:/customer/list";
    }
    @GetMapping(value = "customer/wish/edit/{id}")
    public String displayWishEdit(Model model, @PathVariable int id) {


        model.addAttribute("wishOrder", wishOrderDao.findById(id));

        return "customer/wish/edit";
    }
    @PostMapping(value = "customer/wish/edit/{id}")
    public String processWishEdit(Model model, @PathVariable int id,
                                      @ModelAttribute @Valid WishOrder newWish, Errors errors) {

        if (errors.hasErrors()) {
            return "customer/wish/edit";
        }
        WishOrder edit = wishOrderDao.findById(id);
        edit.setTitle(newWish.getTitle());
        edit.setAuthor(newWish.getAuthor());

        wishOrderDao.save(edit);

        return "redirect:/customer/list";
    }
    @GetMapping(value = "customer/fullWishList")
    public String fullWishlist(Model model) {


        model.addAttribute("wishList", (wishOrderDao.findAll()));

        return "customer/fullWishList";
    }
}



