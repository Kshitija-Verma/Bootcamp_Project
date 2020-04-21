package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {
    @Query(value = "select * from category where id!=parent_id",nativeQuery = true)
    List<Category> findAllCategory();
@Query(value = "select * from category where parent_id IS NULL",nativeQuery = true)
    List<Category> findRootNode();
@Query(value = "select * from category c1 LEFT JOIN category c2 ON c1.id = c2.parent_id where c2.parent_id IS NULL",nativeQuery = true)
    List<Category> findLeafNode();
@Query(value ="select * from category c1 JOIN category c2 ON c2.parent_id=c1.id where c1.parent_id IS NOT NULL",nativeQuery = true)
    List<Category> findInnerNode();

    Category findByName(String name);
}
