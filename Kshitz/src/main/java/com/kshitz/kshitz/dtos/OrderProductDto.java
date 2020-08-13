package com.kshitz.kshitz.dtos;

public class OrderProductDto {
    private String  productVariationId;
    private Integer quantity;

    public String getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(String  productVariationId) {
        this.productVariationId = productVariationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
