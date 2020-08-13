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
    @Query(value = "select * from product where seller_id=:id AND category_id=:id1 AND brand=:brand AND name=:name")
    Product findUniqueName(@Param("id") String id, @Param("brand") String brand, @Param("id1") String id1, @Param("name") String name);

    List<Product> findBySeller(Pageable pageable,Seller seller);


    @Query(value = "delete from product where id=:id")
    void deleteByProductId(@Param("id") String id);

    Product findByCategory(Category category1);

    @Query(value = "select * from product where id!=:id AND category_id=:id1")
    List<Product> findOtherProducts(@Param("id")String id,@Param("id1")String id1);

    @Query(value = "select * from product where category_id=:id")
   List<Product> findByCategoryId(@Param("id") String id);



}
