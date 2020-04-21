package com.kshitz.kshitz.dtos;

import javax.validation.constraints.NotNull;

public class CategoryfieldDto {
    @NotNull
    private Integer metadataId;
    @NotNull
    private Integer categoryId;
    @NotNull
    private String value;

    public Integer getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(Integer metadataId) {
        this.metadataId = metadataId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
