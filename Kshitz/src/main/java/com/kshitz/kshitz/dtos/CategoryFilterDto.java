package com.kshitz.kshitz.dtos;

import com.kshitz.kshitz.entities.products.CategoryMetadataFieldValues;
import com.kshitz.kshitz.entities.products.Product;
import com.kshitz.kshitz.entities.products.ProductVariation;

import java.util.List;
import java.util.Map;

public class CategoryFilterDto {
    private Map<String,String> categoryMetadataFieldValues;
    private Map<String,String> brands;
    private Map<String,Double> productVariations;

    public Map<String, String> getCategoryMetadataFieldValues() {
        return categoryMetadataFieldValues;
    }

    public void setCategoryMetadataFieldValues(Map<String, String> categoryMetadataFieldValues) {
        this.categoryMetadataFieldValues = categoryMetadataFieldValues;
    }

    public Map<String,String> getBrands() {
        return brands;
    }

    public void setBrands(Map<String,String> brands) {
        this.brands = brands;
    }

    public Map<String, Double> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(Map<String, Double> productVariations) {
        this.productVariations = productVariations;
    }
}
