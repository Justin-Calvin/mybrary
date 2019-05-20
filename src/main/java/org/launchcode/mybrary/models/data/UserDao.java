package org.launchcode.mybrary.models.data;

import org.launchcode.mybrary.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;



@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

    User findById(int id);

}