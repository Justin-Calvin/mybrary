package org.launchcode.mybrary.models.data;


import org.launchcode.mybrary.models.Role;
import org.launchcode.mybrary.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public interface RoleDao extends CrudRepository<Role, Integer> {

    Set<Role> findAll();

}
