package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.Category;
import com.kshitz.kshitz.entities.products.CategoryMetadataFieldValues;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryMetadataFieldValueRepository extends CrudRepository<CategoryMetadataFieldValues,String> {



   @Query("{'categoryMetadataIdentity.categoryMetadataField.id' : ?0 , 'categoryMetadataIdentity.category.id' : ?1}")
   CategoryMetadataFieldValues findByAllId(String id,String id2);

    @Query("{'categoryMetadataIdentity.category.id':?0}")
    List<CategoryMetadataFieldValues> findByCategory(String id);

    @Query("{'categoryMetadataIdentity.category.id':?0}")
    List<CategoryMetadataFieldValues> findByCategoryId(String categoryId);
}