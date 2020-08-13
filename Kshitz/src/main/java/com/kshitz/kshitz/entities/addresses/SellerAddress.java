package com.kshitz.kshitz.entities.addresses;

import com.kshitz.kshitz.entities.users.Seller;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection="address")
public class SellerAddress extends Address implements Serializable {

    private Seller seller;

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
