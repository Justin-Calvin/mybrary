package org.launchcode.mybrary.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 5, max = 40, message = "Please use between 5 and 40 characters.")
    private String username;

    @NotNull
    @Size(min = 5, message = "Please use between 5 and 40 characters.")
    private String password;

    @Transient
    private String passwordConfirm;


    @ManyToMany
    private Set<Role> roles;

    @ManyToMany
    private Set<BookOrder> orders;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public Set<BookOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<BookOrder> orders) {
        this.orders = orders;
    }

}

