package org.launchcode.mybrary.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 30, message = "Title must not be empty")
    private String title;

    @NotNull
    @Size(min = 1, message = "Description must not be empty")
    private String author;

    public Item(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Item() { }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
