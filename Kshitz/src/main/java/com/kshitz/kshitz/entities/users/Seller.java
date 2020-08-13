package com.kshitz.kshitz.entities.users;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "user")
public class Seller extends User implements Serializable  {

    private Integer gst;

    private String companyName;
    private String companyContact;



    public Integer getGst() {
        return gst;
    }

    public void setGst(Integer gst) {
        this.gst = gst;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "gst=" + gst +
                ", companyName='" + companyName + '\'' +
                ", companyContact='" + companyContact + '\'' +
                '}';
    }
}
