package org.launchcode.mybrary.models.data;

import org.launchcode.mybrary.models.BookOrder;
import org.launchcode.mybrary.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Set;

@Repository
@Transactional
public interface BookOrderDao extends CrudRepository<BookOrder, Integer> {

    ArrayList<BookOrder> findByTitle(String title);
    ArrayList<BookOrder> findByAuthor(String author);
    ArrayList<BookOrder> findByUsers(Set<User> users);
    BookOrder findById(int id);

}
