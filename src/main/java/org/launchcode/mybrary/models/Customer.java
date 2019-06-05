package org.launchcode.mybrary.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 2, max = 40, message = "Please use between 2 and 40 characters.")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 40, message = "Please use between 2 and 40 characters.")
    private String lastName;

    @NotNull
    @Size(min = 6, max = 40, message = "Please use between 6 and 40 characters.")
    private String email;

    @NotNull
    @Size(min = 10, max = 30, message = "Please use between 10 and 30 characters.")
    private String phone;

    @ManyToMany
    @JoinTable(
            name = "customer_wish_list",
            joinColumns = @JoinColumn(
                    name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "wish_order_id", referencedColumnName = "id"))
    private Set<WishOrder> wishList;

    @ManyToMany
    private Set<Favorite> favorites = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<WishOrder> getWishList() {
        return wishList;
    }

    public void setWishList(Set<WishOrder> wishList) {
        this.wishList = wishList;
    }

    public Set<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Favorite> favorites) {
        this.favorites = favorites;
    }
}