package com.kshitz.kshitz.entities.products;

import javax.persistence.*;
import java.io.Serializable;
@Embeddable
public class CategoryMetadataIdentity implements Serializable {

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private CategoryMetadataField categoryMetadataField;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
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
