package com.kshitz.kshitz.entities.orders;

import com.kshitz.kshitz.entities.products.ProductVariation;
import com.kshitz.kshitz.entities.users.Customer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Customer customer;
    private Integer quantity;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }
}
