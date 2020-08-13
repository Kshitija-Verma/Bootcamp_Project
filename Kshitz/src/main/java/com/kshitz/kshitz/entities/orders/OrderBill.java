package com.kshitz.kshitz.entities.orders;

import com.kshitz.kshitz.entities.addresses.CustomerAddress;
import com.kshitz.kshitz.entities.users.Customer;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class OrderBill {

    private Integer id;

    private Customer customer;

    private Double amountPaid;
    private String dateCreated;
    private String paymentMethod;


    private CustomerAddress customerAddress;


    private List<OrderProduct> orderProducts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }
}
