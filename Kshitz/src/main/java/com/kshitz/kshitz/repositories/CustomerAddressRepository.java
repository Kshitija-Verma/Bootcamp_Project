package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.addresses.CustomerAddress;
import com.kshitz.kshitz.entities.users.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerAddressRepository extends CrudRepository<CustomerAddress,String> {


    List<CustomerAddress> findAllByCustomer(User user);


}

