package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.addresses.SellerAddress;
import com.kshitz.kshitz.entities.users.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SellerAddressRepository extends CrudRepository<SellerAddress,String> {

    SellerAddress findBySeller(Seller seller);
}
