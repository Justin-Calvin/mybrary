package org.launchcode.mybrary.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity(name = "BookOrder")
public class BookOrder extends Order {

    @Column
    private boolean placed;


    @ManyToMany(mappedBy = "orders")
    private Set<User> users;

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
