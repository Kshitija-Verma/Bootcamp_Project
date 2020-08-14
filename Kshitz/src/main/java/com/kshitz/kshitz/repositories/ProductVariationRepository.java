package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.Product;
import com.kshitz.kshitz.entities.products.ProductVariation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductVariationRepository extends CrudRepository<ProductVariation,String> {

    List<ProductVariation> findByProduct(Pageable pageable, Product product);

//    @Query(value = "select * from product_variation")
//    List<ProductVariation> findAllProductVariation(Pageable pageable);

    @Query("{'product.id':?0,'quantityAvailable':?1}")
    List<ProductVariation> findAllSameDetails(String id,Integer quantity);


    List<ProductVariation> findAll(Pageable pageable);

    @Query("{'product.id':?0}")
    List<ProductVariation> findByProductId(String id);

    @Query("{'product.isActive':true}")
    List<ProductVariation> findAllActiveProducts(Pageable pageable);
}
