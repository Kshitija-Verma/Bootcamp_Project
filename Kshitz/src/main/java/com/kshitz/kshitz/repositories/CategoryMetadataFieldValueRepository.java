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
   @Query(value = "select * from category_metadata_field_values where category_id IN (select c1.id from category c1 LEFT JOIN category c2 ON c1.id = c2.parent_id where c2.parent_id IS NULL) ")
    List<CategoryMetadataFieldValues> findAllLeafCategory();


   @Query("{'categoryMetadataIdentity.categoryMetadataField.id' : ?0 , 'categoryMetadataIdentity.category.id' : ?1}")
   CategoryMetadataFieldValues findByAllId(String id,String id2);

    @Query("{'categoryMetadataIdentity.category.id':?0}")
    List<CategoryMetadataFieldValues> findByCategory(String id);

    @Query("{'categoryMetadataIdentity.category.id':?0}")
    List<CategoryMetadataFieldValues> findByCategoryId(String categoryId);
}