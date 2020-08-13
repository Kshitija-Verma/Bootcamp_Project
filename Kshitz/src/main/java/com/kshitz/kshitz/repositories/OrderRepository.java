package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.orders.OrderBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderBill, String> {
}
