package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.products.CategoryMetadataField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMetadataFieldRespository extends CrudRepository<CategoryMetadataField,Integer> {

    CategoryMetadataField findByName(String name);
}
