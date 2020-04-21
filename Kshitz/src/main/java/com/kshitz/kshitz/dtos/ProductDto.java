package com.kshitz.kshitz.dtos;

import javax.validation.constraints.NotNull;

public class ProductDto {
    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    private String description;
    @NotNull
    private Integer categoryId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
