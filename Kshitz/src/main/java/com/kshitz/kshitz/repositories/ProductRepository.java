package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.Category;
import com.kshitz.kshitz.entities.products.Product;
import com.kshitz.kshitz.entities.users.Seller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query(value = "select * from product where seller_id=:id AND category_id=:id1 AND brand=:brand AND name=:name", nativeQuery = true)
    Product findUniqueName(@Param("id") Integer id, @Param("brand") String brand, @Param("id1") Integer id1, @Param("name") String name);

    List<Product> findBySeller(Pageable pageable,Seller seller);

    @Modifying
    @Query(value = "delete from product where id=:id", nativeQuery = true)
    void deleteByProductId(@Param("id") Integer id);

    Product findByCategory(Category category1);

    @Query(value = "select * from product where id!=:id AND category_id=:id1",nativeQuery = true)
    List<Product> findOtherProducts(@Param("id")Integer id,@Param("id1")Integer id1);

    @Query(value = "select * from product where category_id=:id",nativeQuery = true)
   List<Product> findByCategoryId(@Param("id") Integer id);
}
