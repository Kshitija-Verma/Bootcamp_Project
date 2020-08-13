package com.kshitz.kshitz.dtos;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class ProductVariationDto {
    @NotNull
    private String productId;
    @NotNull
    private Integer quantity;
    @NotNull
    private Double price;
    @NotNull
    private String imagePath;
    @NotNull
    private Map<String,String> metadata;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String  productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagepath) {
        this.imagePath = imagepath;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
