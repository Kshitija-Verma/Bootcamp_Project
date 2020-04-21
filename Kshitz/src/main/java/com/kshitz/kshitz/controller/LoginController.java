package com.kshitz.kshitz.controller;

import com.kshitz.kshitz.dtos.*;
import com.kshitz.kshitz.entities.addresses.CustomerAddress;
import com.kshitz.kshitz.entities.users.User;
import com.kshitz.kshitz.services.CustomerServiceImpl;
import com.kshitz.kshitz.services.SellerServiceImpl;
import com.kshitz.kshitz.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    TokenStore tokenStore;
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    SellerServiceImpl sellerService;


    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
            return "Logged out successfully!!";
        }
        return "access token not received!";
    }

    @PostMapping("/kshitz/forgot-password")
    public void resetPassword(@RequestBody String email) {
        userService.resetUserPassword(email);

    }

    @PutMapping("/kshitz/reset-password")
    public String setPassword(@RequestParam("token") String resetToken, @RequestBody ForgotPasswordDto forgotPasswordDto) {
        return userService.updatePassword(resetToken, forgotPasswordDto);
    }

    @GetMapping("/customer/user-details")
    @ResponseBody
    public User currentUserName(Authentication authentication) {
        String username = authentication.getName();
        return customerService.listDetails(username);

    }

    @GetMapping("/customer/customer-address")
    @ResponseBody
    public List<CustomerAddress> urrentUserName(Authentication authentication) {
        String username = authentication.getName();
        return customerService.listAddress(username);

    }

    @PatchMapping("/customer/update-customer")
    public String updateDetails(@Valid @RequestBody UpdateCustomerDto updateCustomerDto, Authentication authentication) {
        String username = authentication.getName();
        return customerService.updateProfile(updateCustomerDto, username);
    }

    @PatchMapping("/customer/update-customer-password")
    public String updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto, Authentication authentication) {
        String username = authentication.getName();
        return customerService.updatePassword(updatePasswordDto, username);

    }

    @PostMapping("/customer/add-customer-address")
    public String addAddress(@Valid @RequestBody AddressDto addressDto, Authentication authentication) {
        String username = authentication.getName();
        return customerService.addNewAddress(addressDto, username);
    }

    @DeleteMapping("/customer/delete-customer-address/{id}")
    public String deleteAddress(@PathVariable Integer id, Authentication authentication) {
        String username = authentication.getName();
        return customerService.deleteCustomerAddress(id, username);
    }

    @PatchMapping("/customer/update-customer-address/{id}")
    public String updateAddress(@PathVariable Integer id, @RequestBody AddressDto addressDto, Authentication authentication) {
        String username = authentication.getName();
        return customerService.updateCustomerAddress(id, addressDto, username);
    }

    @GetMapping("/seller/seller-details")
    @ResponseBody
    public User currentSeller(Authentication authentication) {
        String username = authentication.getName();
        return sellerService.listDetails(username);

    }

    @PatchMapping("/seller/update-seller")
    public String updateDetails(@Valid @RequestBody UpdateSellerDto updateSellerDto, Authentication authentication) {
        String username = authentication.getName();
        return sellerService.updateProfile(updateSellerDto, username);
    }

    @PatchMapping("/seller/update-seller-password")
    public String updateSellerPassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto, Authentication authentication) {
        String username = authentication.getName();
        return sellerService.updatePassword(updatePasswordDto, username);

    }

    @PatchMapping("/seller/update-seller-address")
    public String updateSellerAddress(@RequestBody AddressDto addressDto, Authentication authentication) {
        String username = authentication.getName();
        return sellerService.updateSellerAddress(addressDto, username);
    }


}
