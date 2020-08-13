package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.Product;
import com.kshitz.kshitz.entities.products.ProductVariation;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProductVariationRepository extends CrudRepository<ProductVariation,String> {
    List<ProductVariation> findByProduct(Pageable pageable, Product product);

@Query(value = "delete from product_variation where product_id=:id")
    void deleteByProductId(@Param("id")String id);

    @Query(value = "select * from product_variation")
    List<ProductVariation> findAllProductVariation(Pageable pageable);

    @Query(value = "select * from product_variation where product_id=:id AND quantity_available=:quantity")
    List<ProductVariation> findAllSameDetails(@Param("id")String id,@Param("quantity")Integer quantity);


}
