package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.dtos.CartDto;
import com.kshitz.kshitz.dtos.OrderProductDto;
import com.kshitz.kshitz.entities.orders.OrderBill;

public interface OrderService {
    String addCartItem(Integer productVariationId, CartDto cartDto, String username);

    OrderBill buyCart(Integer customerAddressId, String username);

    OrderBill buyProduct(Integer customerAddressId, OrderProductDto orderProductDto, String username);
}
