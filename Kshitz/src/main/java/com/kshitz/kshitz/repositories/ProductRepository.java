package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.Category;
import com.kshitz.kshitz.entities.products.Product;
import com.kshitz.kshitz.entities.users.Seller;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

    @Query("{'seller_id':?0,'category_id':?1,'brand':?2,'name':?3}")
    Product findUniqueName( String id, String brand,String id1, String name);


    Product findByCategory(Category category1);

    @Query("{'id':{$not:?0},'category_id':?1}")
    List<Product> findOtherProducts(String id,String id1);


    @Query("{'category_id':?0}")
   List<Product> findByCategoryId(String id);


    @Query("{'seller.id':?0}")
    List<Product> findBySellerId(String id);

}
