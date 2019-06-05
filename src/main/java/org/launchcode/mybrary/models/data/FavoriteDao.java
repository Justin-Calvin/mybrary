package org.launchcode.mybrary.models.data;

import org.launchcode.mybrary.models.Customer;
import org.launchcode.mybrary.models.Favorite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Set;

@Repository
@Transactional
public interface FavoriteDao extends CrudRepository<Favorite, Integer> {

    Favorite findByTitle(String title);
    Favorite findByAuthor(String author);
    Favorite findById(int id);

}
