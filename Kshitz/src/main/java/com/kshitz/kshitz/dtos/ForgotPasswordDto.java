package com.kshitz.kshitz.dtos;

import com.kshitz.kshitz.validations.ValidPassword;

import javax.validation.constraints.NotNull;

public class ForgotPasswordDto {
    @ValidPassword
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;

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
