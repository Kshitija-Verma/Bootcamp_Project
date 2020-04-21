package com.kshitz.kshitz.dtos;

import com.kshitz.kshitz.validations.Email;
import com.kshitz.kshitz.validations.Phone;
import com.kshitz.kshitz.validations.ValidPassword;

import javax.validation.constraints.NotNull;

public class RegisterCustomerDto {
    @NotNull
    private String firstName;
    @NotNull
    private String middleName;
    @NotNull
    private String lastName;
    @NotNull
    private String userName;
    @NotNull
    private String profileImage;
    @Phone(message = "Phone number must contain only digits")
    @NotNull
    private String contact;
    @Email(message = "email should be valid !!!")
    @NotNull
    private String email;
    @ValidPassword
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
