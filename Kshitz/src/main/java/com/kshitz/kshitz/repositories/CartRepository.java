package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.orders.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    @Query(value = "select * from cart where customer_id=:id", nativeQuery = true)
    List<Cart> findByCustomer(@Param("id") Integer id);
}
