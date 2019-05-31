package org.launchcode.mybrary.models.data;

import org.launchcode.mybrary.models.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public interface PrivilegeDao extends CrudRepository<Privilege, Integer> {

    Set<Privilege> findAll();
    Privilege findByName(String name);

}
