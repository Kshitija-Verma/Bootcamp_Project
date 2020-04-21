package com.kshitz.kshitz.entities.addresses;

import com.kshitz.kshitz.entities.users.Seller;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;


@Entity
@PrimaryKeyJoinColumn(name = "addressId")
public class SellerAddress extends Address implements Serializable {
    @OneToOne
    @JoinColumn(name = "sellerId", referencedColumnName = "id", unique = true)
    private Seller seller;

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
