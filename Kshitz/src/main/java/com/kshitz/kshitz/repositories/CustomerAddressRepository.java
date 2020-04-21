package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.addresses.CustomerAddress;
import com.kshitz.kshitz.entities.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerAddressRepository extends CrudRepository<CustomerAddress,Integer> {

    List<CustomerAddress> findAllByCustomer(User user);
}
