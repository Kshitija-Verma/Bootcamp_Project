package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.dtos.CartDto;
import com.kshitz.kshitz.dtos.OrderProductDto;
import com.kshitz.kshitz.entities.orders.Cart;
import com.kshitz.kshitz.entities.orders.OrderBill;
import com.kshitz.kshitz.entities.orders.ViewOrderDetails;

import java.util.List;

public interface OrderService {
    String addCartItem(String productVariationId, CartDto cartDto, String username);

    OrderBill buyCart(String customerAddressId, String username);

    OrderBill buyProduct(String customerAddressId, OrderProductDto orderProductDto, String username);

    List<Cart> viewCart(String username);

    List<Cart> removeFromCart(String id, String username);

    ViewOrderDetails viewOrderDetails(String username);

    List<OrderBill> viewOrders(String username);
}
