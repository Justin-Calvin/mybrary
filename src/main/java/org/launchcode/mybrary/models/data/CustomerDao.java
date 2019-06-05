package org.launchcode.mybrary.models.data;

import org.launchcode.mybrary.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CustomerDao extends CrudRepository<Customer, Integer> {

    Customer findById(int id);
    Customer findByFirstName(String firstName);
    Customer findByLastName(String lastName);
    Customer findByEmail(String email);

}
