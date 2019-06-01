package org.launchcode.mybrary.models.data;


import org.launchcode.mybrary.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.*;


@Repository
@Transactional
public interface BookDao extends CrudRepository<Book, Integer> {

    ArrayList<Book> findByTitle(String title);
    ArrayList<Book> findByAuthor(String author);
    Book findById(int id);

}

