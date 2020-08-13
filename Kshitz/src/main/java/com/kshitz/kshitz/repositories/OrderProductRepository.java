package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.orders.OrderProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct,String> {
}
