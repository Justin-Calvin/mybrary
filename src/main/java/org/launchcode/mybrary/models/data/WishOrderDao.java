package org.launchcode.mybrary.models.data;

import org.launchcode.mybrary.models.Customer;
import org.launchcode.mybrary.models.WishOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.Set;
import java.util.Set;

@Repository
@Transactional
public interface WishOrderDao extends CrudRepository<WishOrder, Integer> {

    WishOrder findByTitle(String title);
    WishOrder findByAuthor(String author);
    WishOrder findById(int id);

}
