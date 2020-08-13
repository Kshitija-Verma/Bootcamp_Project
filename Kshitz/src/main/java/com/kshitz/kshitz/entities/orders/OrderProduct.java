package com.kshitz.kshitz.entities.orders;

import com.kshitz.kshitz.entities.products.ProductVariation;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class OrderProduct implements Serializable {

    private String id;

    private OrderBill orderBill;
    private Integer quantity;
    private Double price;

    private ProductVariation productVariation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderBill getOrderBill() {
        return orderBill;
    }

    public void setOrderBill(OrderBill orderBill) {
        this.orderBill = orderBill;
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

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }
}
