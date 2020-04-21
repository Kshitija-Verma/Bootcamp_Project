package com.kshitz.kshitz.dtos;

public class OrderProductDto {
    private Integer productVariationId;
    private Integer quantity;

    public Integer getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(Integer productVariationId) {
        this.productVariationId = productVariationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
