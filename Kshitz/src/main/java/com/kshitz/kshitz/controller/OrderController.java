package com.kshitz.kshitz.controller;

import com.kshitz.kshitz.dtos.CartDto;
import com.kshitz.kshitz.dtos.OrderProductDto;
import com.kshitz.kshitz.entities.orders.OrderBill;
import com.kshitz.kshitz.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/customer/add-to-cart/{productVariationId}")
    public String addCartItem(@PathVariable Integer productVariationId, @RequestBody CartDto cartDto, Authentication authentication) {
        String username = authentication.getName();
        return orderService.addCartItem(productVariationId, cartDto, username);
    }

    @PostMapping("/customer/buy-cart/{customerAddressId}")
    public OrderBill buyCart(@PathVariable Integer customerAddressId, Authentication authentication) {
        String username = authentication.getName();
        return orderService.buyCart(customerAddressId, username);
    }

    @PostMapping("/customer/buy-product/{customerAddressId}")
    public OrderBill buyProduct(@PathVariable Integer customerAddressId, @RequestBody OrderProductDto orderProductDto, Authentication authentication) {
        String username = authentication.getName();
        return orderService.buyProduct(customerAddressId, orderProductDto, username);
    }

}
