package com.kshitz.kshitz.entities.users;

import com.kshitz.kshitz.entities.addresses.CustomerAddress;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Customer extends User implements Serializable  {
    private String profileImage;
    private String contact;

@OneToMany(mappedBy = "customer")
private List<CustomerAddress> customerAddresses;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Customer{" +
                ", firstName='" + super.getFirstName() + '\'' +
                ", middleName='" + super.getMiddleName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", email=" + super.getEmail() +
                ", isActive=" + super.isActive() +
                "contact='" + contact + '\'' +
                '}';
    }
}
