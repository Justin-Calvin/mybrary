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
    @Size(min = 1, max = 100, message = "Title must be between 1-100 characters")
    private String title;

    @NotNull
    @Size(min = 1, max = 100, message = "Author must be between 1-100 characters")
    private String author;

    @Min(value = 0, message = "Stock must be a positive number")
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
