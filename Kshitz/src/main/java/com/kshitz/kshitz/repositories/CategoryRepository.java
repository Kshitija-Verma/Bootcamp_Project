package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.Category;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,String> {
    @Query(value = "select * from category where id!=parent_id")
    List<Category> findAllCategory();
@Query(value = "select * from category where parent_id IS NULL")
    List<Category> findRootNode();
@Query(value = "select * from category c1 LEFT JOIN category c2 ON c1.id = c2.parent_id where c2.parent_id IS NULL")
    List<Category> findLeafNode();
@Query(value ="select * from category c1 JOIN category c2 ON c2.parent_id=c1.id where c1.parent_id IS NOT NULL")
    List<Category> findInnerNode();

    Category findByName(String name);


}
