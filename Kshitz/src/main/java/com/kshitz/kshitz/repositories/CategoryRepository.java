package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.Category;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,String> {

    @Query("{ id: { $not: parent_id} } ")
    List<Category> findAllCategory();
@Query("{parent_id:null}")
    List<Category> findRootNode();


    Category findByName(String name);


}
