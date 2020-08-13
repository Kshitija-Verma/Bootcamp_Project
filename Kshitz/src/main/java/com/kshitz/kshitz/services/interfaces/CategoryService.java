package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.dtos.*;
import com.kshitz.kshitz.entities.products.Category;
import com.kshitz.kshitz.entities.products.CategoryMetadataField;
import com.kshitz.kshitz.entities.products.CategoryMetadataFieldValues;

import java.util.List;

public interface CategoryService {
    String addCategoryMetadata(CategoryMetadataDto categoryMetadataDto);

    Iterable<CategoryMetadataField> listData();

    String addNewCategory(CategoryDto categoryDto);

    List<CategoryDisplayDto> viewCategory(String id);

    List<Category> viewAllCategory();

    String updateCategory(String id, CategoryDto categoryDto);

    String addFieldValueCategory(CategoryfieldDto categoryfieldDto);

    List<CategoryMetadataFieldValues> viewSellerCategories();

    List<Category> viewCustomerCategory();

    CategoryFilterDto viewFilterCategory(String id);
}
