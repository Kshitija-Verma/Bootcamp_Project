package com.kshitz.kshitz.entities.products;

import com.kshitz.kshitz.entities.users.Customer;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class ProductReview {

    private String id;

    Customer customer;

    private String review;
    private Integer rating;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
