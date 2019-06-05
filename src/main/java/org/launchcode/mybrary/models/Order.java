package org.launchcode.mybrary.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @NotNull
    @Size(min = 1, max = 100, message = "Title must be between 1-100 characters")
    @Column
    protected String title;

    @NotNull
    @Size(min = 1, max = 100, message = "Author must be between 1-100 characters")
    @Column
    protected String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
