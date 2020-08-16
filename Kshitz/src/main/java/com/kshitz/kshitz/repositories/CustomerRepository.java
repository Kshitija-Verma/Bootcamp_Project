package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.users.Customer;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,String> {


    @Query("{'username':?0}")
    Customer findByUsername(String username);

    @Query("{'email':?0}")
    Customer findByEmail(String email);

    @Query("{'role.roles':'ROLE_CUSTOMER'}")
    Iterable<Customer> findAllByCustomer();
}
