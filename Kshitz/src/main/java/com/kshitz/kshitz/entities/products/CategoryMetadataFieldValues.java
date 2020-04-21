package com.kshitz.kshitz.entities.products;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class CategoryMetadataFieldValues implements Serializable {
    @EmbeddedId
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
