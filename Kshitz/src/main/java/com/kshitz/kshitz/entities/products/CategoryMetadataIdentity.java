package com.kshitz.kshitz.entities.products;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Document
public class CategoryMetadataIdentity implements Serializable {


    private CategoryMetadataField categoryMetadataField;

    private Category category;

    public CategoryMetadataField getCategoryMetadataField() {
        return categoryMetadataField;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
