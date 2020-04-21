package com.kshitz.kshitz.controller;

import com.kshitz.kshitz.dtos.RegisterCustomerDto;
import com.kshitz.kshitz.dtos.RegisterSellerDto;
import com.kshitz.kshitz.services.CustomerServiceImpl;
import com.kshitz.kshitz.services.SellerServiceImpl;
import com.kshitz.kshitz.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@RestController
@EnableAsync(proxyTargetClass = true)
public class RegisterController {
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    SellerServiceImpl sellerService;
    @Autowired
    UserService userService;
    @Autowired
    MessageSource messageSource;

    @PutMapping("/reset-account-lock/{username}")
    public String resetAccountLock(@PathVariable String username, @RequestBody String password) {
        return userService.resetAccountLock(username, password);
    }

    @PostMapping("/register-customer")
    public String registerCustomer(@Valid @NotNull @RequestBody RegisterCustomerDto registerCustomerDto, @RequestHeader(name = "Accept-Language", required = false) Locale locale) {

        String name = customerService.saveCustomer(registerCustomerDto);

        return messageSource.getMessage("register.message", new Object[]{name}, locale);

    }

    @PostMapping("/register-seller")
    public String registerSeller(@Valid @RequestBody RegisterSellerDto registerSellerDto, @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        String name = sellerService.saveSeller(registerSellerDto);
        return messageSource.getMessage("register.message", new Object[]{name}, locale);

    }

    @GetMapping("/confirm-account")
    public void confirmEmail(@RequestParam("token") String confirmationToken) {
        userService.tokenMatch(confirmationToken);


    }


}
