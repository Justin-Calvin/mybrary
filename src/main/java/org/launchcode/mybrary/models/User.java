package org.launchcode.mybrary.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 10, message = "Username must be between 1-10 characters")
    private String username;

    @NotNull
    @Size(min = 1, max = 20, message = "Password must be between 1-20 characters")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() { }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }


}
