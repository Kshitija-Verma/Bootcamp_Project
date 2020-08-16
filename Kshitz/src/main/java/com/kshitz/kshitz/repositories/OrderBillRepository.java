package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.orders.OrderBill;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderBillRepository extends CrudRepository<OrderBill,String> {

    @Query("{'customer.id':?0}")
    List<OrderBill> findByCustomerId(String id);
}
