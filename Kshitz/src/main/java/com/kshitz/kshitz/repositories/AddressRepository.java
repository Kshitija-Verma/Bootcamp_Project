package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.addresses.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {
}
