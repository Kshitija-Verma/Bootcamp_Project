package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.Category;
import com.kshitz.kshitz.entities.products.CategoryMetadataFieldValues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryMetadataFieldValueRepository extends CrudRepository<CategoryMetadataFieldValues,Integer> {
   @Query(value = "select * from category_metadata_field_values where category_id IN (select c1.id from category c1 LEFT JOIN category c2 ON c1.id = c2.parent_id where c2.parent_id IS NULL) ",nativeQuery = true)
    List<CategoryMetadataFieldValues> findAllLeafCategory();

   @Query(value = "select * from category_metadata_field_values where category_metadata_field_id=:id AND category_id=:id2 ",nativeQuery = true)
   CategoryMetadataFieldValues findByAllId(@Param("id")Integer id,@Param("id2")Integer id2);

@Query(value = "select * from category_metadata_field_values where category_id=:id",nativeQuery = true)
    List<CategoryMetadataFieldValues> findByCategory(@Param("id")Integer id);
}