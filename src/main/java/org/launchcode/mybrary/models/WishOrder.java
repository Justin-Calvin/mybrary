package org.launchcode.mybrary.models;

import javax.persistence.*;
import java.util.Set;


@Entity(name = "WishOrder")
public class WishOrder extends Order{



    @ManyToMany(mappedBy = "wishList")
    private Set<Customer> customers;


    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
}
