package com.kshitz.kshitz.entities.products;

import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;

@Document
public class CategoryMetadataFieldValues implements Serializable {

    private CategoryMetadataIdentity categoryMetadataIdentity;

    private String value;

    public CategoryMetadataIdentity getCategoryMetadataIdentity() {
        return categoryMetadataIdentity;
    }

    public void setCategoryMetadataIdentity(CategoryMetadataIdentity categoryMetadataIdentity) {
        this.categoryMetadataIdentity = categoryMetadataIdentity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
