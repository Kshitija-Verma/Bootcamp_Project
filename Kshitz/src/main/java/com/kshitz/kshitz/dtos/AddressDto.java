package com.kshitz.kshitz.dtos;

import javax.validation.constraints.NotNull;

public class AddressDto {
    @NotNull
    private String city;
    @NotNull
    private String country;
    @NotNull
    private String state;
    @NotNull
    private String zipCode;
    @NotNull
    private String label;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipcode) {
        this.zipCode = zipcode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
