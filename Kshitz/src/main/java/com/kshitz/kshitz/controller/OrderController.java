package com.kshitz.kshitz.controller;

import com.kshitz.kshitz.dtos.CartDto;
import com.kshitz.kshitz.dtos.OrderProductDto;
import com.kshitz.kshitz.entities.orders.Cart;
import com.kshitz.kshitz.entities.orders.OrderBill;
import com.kshitz.kshitz.entities.orders.ViewOrderDetails;
import com.kshitz.kshitz.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/customer/add-to-cart/{productVariationId}")
    public String addCartItem(@PathVariable String productVariationId, @RequestBody CartDto cartDto, Authentication authentication) {
        String username = authentication.getName();
        return orderService.addCartItem(productVariationId, cartDto, username);
    }

    @PostMapping("/customer/buy-cart/{customerAddressId}")
    public OrderBill buyCart(@PathVariable String customerAddressId, Authentication authentication) {
        String username = authentication.getName();
        return orderService.buyCart(customerAddressId, username);
    }

    @PostMapping("/customer/buy-product/{customerAddressId}")
    public OrderBill buyProduct(@PathVariable String customerAddressId, @RequestBody OrderProductDto orderProductDto, Authentication authentication) {
        String username = authentication.getName();
        return orderService.buyProduct(customerAddressId, orderProductDto, username);
    }
    @GetMapping({"/customer/view-cart"})
    public List<Cart> viewCart(Authentication authentication) {
        String username = authentication.getName();
        return orderService.viewCart(username);
    }
    @PostMapping({"/customer/remove-from-cart/{id}"})
    public List<Cart> removeFromCart(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        return orderService.removeFromCart(id, username);
    }

    @GetMapping({"/customer/view-orders"})
    public List<OrderBill> viewOrders(Authentication authentication) {
        String username = authentication.getName();
        return orderService.viewOrders(username);
    }
    @GetMapping({"/customer/view-cart-details"})
    public ViewOrderDetails viewOrderDetails(Authentication authentication) {
        String username = authentication.getName();
        return this.orderService.viewOrderDetails(username);
    }

}
