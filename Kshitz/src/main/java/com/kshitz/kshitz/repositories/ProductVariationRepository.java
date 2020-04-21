package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.Product;
import com.kshitz.kshitz.entities.products.ProductVariation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductVariationRepository extends CrudRepository<ProductVariation,Integer> {
    List<ProductVariation> findByProduct(Pageable pageable, Product product);
    @Modifying
@Query(value = "delete from product_variation where product_id=:id",nativeQuery = true)
    void deleteByProductId(@Param("id")Integer id);

    @Query(value = "select * from product_variation",nativeQuery = true)
    List<ProductVariation> findAllProductVariation(Pageable pageable);

    @Query(value = "select * from product_variation where product_id=:id AND quantity_available=:quantity",nativeQuery = true)
    List<ProductVariation> findAllSameDetails(@Param("id")Integer id,@Param("quantity")Integer quantity);
}
