package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.users.Seller;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends CrudRepository<Seller,String> {

    @Query("{'username':?0}")
    Seller findByUsername(String username);

    @Query("{'email':?0}")
    Seller findByEmail(String email);

    @Query("{'role.roles':'ROLE_SELLER'}")
    Iterable<Seller> findAllBySeller();
}
