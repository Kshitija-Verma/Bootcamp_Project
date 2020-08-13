package com.kshitz.kshitz.dtos;

import javax.validation.constraints.NotNull;

public class CategoryfieldDto {
    @NotNull
    private String metadataId;
    @NotNull
    private String  categoryId;
    @NotNull
    private String value;

    public String  getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String  metadataId) {
        this.metadataId = metadataId;
    }

    public String  getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String  categoryId) {
        this.categoryId = categoryId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
