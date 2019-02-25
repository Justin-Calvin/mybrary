package org.launchcode.mybrary.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 30, message = "Title must not be empty")
    private String title;

    @NotNull
    @Size(min = 1, max = 30, message = "Author must not be empty")
    private String author;

    @NotNull
    @Min(value = 1, message = "Item stock must be 1 or higher")
    private int stock;

    public Item(String title, String author, Integer stock) {
        this.title = title;
        this.author = author;
        this.stock = stock;
    }

    public Item() { }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
