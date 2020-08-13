package com.kshitz.kshitz.entities.addresses;

import com.kshitz.kshitz.entities.users.Customer;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;

@Document(collection = "address")
public class CustomerAddress extends Address implements Serializable {


    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}