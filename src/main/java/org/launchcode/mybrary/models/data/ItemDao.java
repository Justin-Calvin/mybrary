package org.launchcode.mybrary.models.data;


import org.launchcode.mybrary.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.*;


@Repository
@Transactional
public interface ItemDao extends CrudRepository<Item, Integer> {

    ArrayList<Item> findByTitle(String title);
    ArrayList<Item> findByAuthor(String author);
    Item findById(int id);

}

