package com.kshitz.kshitz.entities.orders;

import com.kshitz.kshitz.entities.products.ProductVariation;
import com.kshitz.kshitz.entities.users.Customer;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;

@Document
public class Cart implements Serializable {

    private String  id;

    private Customer customer;
    private Integer quantity;

    private ProductVariation productVariation;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String  getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }
}
