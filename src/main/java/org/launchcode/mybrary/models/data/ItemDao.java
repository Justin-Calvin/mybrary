package org.launchcode.mybrary.models.data;


import org.launchcode.mybrary.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface ItemDao extends CrudRepository<Item, Integer> {

}

