package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.users.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {


    Customer findByUsername(String username);

    Customer findByEmail(String email);
}
