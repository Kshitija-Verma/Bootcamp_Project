package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.dtos.CartDto;
import com.kshitz.kshitz.dtos.OrderProductDto;
import com.kshitz.kshitz.entities.orders.OrderBill;

public interface OrderService {
    String addCartItem(String productVariationId, CartDto cartDto, String username);

    OrderBill buyCart(String customerAddressId, String username);

    OrderBill buyProduct(String customerAddressId, OrderProductDto orderProductDto, String username);
}
