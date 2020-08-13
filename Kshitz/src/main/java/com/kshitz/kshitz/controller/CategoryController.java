package com.kshitz.kshitz.controller;

import com.kshitz.kshitz.dtos.*;
import com.kshitz.kshitz.entities.products.Category;
import com.kshitz.kshitz.entities.products.CategoryMetadataField;
import com.kshitz.kshitz.entities.products.CategoryMetadataFieldValues;
import com.kshitz.kshitz.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;

    @PostMapping("/admin/add-metadata-fields")
    public String addMetadata(@Valid @RequestBody CategoryMetadataDto categoryMetadataDto) {
        String id = categoryService.addCategoryMetadata(categoryMetadataDto);
        return "Metadata field added with id =" + id;
    }

    @GetMapping("/admin/view-metadata-fields")
    @ResponseBody
    public Iterable<CategoryMetadataField> viewMetadata(Authentication authentication) {
        return categoryService.listData();

    }

    @PostMapping("/admin/add-category")
    @ResponseBody
    public String addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        String id = categoryService.addNewCategory(categoryDto);
        return "Category added with id =" + id;
    }

    @GetMapping("/admin/view-category/{id}")
    public List<CategoryDisplayDto> viewSingleCategory(@PathVariable String  id) {
        return categoryService.viewCategory(id);
    }

    @GetMapping("/admin/view-all-category")
    public List<Category> viewAllCategory() {
        return categoryService.viewAllCategory();
    }

    @PutMapping("/admin/update-category/{id}")
    public String updateCategory(@PathVariable String  id, @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    @PostMapping("/admin/add-field-value-category")
    public String addFieldValue(@RequestBody CategoryfieldDto categoryfieldDto) {
        return categoryService.addFieldValueCategory(categoryfieldDto);
    }

    @GetMapping("/seller/view-categories")
    public List<CategoryMetadataFieldValues> viewCategories() {
        return categoryService.viewSellerCategories();
    }

    @GetMapping("/customer/view-categories")
    public List<Category> viewCustomerCategory() {
        return categoryService.viewCustomerCategory();
    }

    @GetMapping("/customer/view-filtered-categories/{categoryId}")
    public CategoryFilterDto viewFilterCategory(@PathVariable String  categoryId) {
        return categoryService.viewFilterCategory(categoryId);
    }

}
