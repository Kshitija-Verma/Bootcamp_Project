package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.users.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends CrudRepository<Seller,Integer> {

    Seller findByUsername(String username);

    Seller findByEmail(String email);
}
