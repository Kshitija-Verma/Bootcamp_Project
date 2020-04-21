package com.kshitz.kshitz.entities.addresses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kshitz.kshitz.entities.users.Customer;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name = "addressId")
public class CustomerAddress extends Address implements Serializable {

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}