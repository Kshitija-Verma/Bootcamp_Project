package com.kshitz.kshitz.services;

import com.kshitz.kshitz.dtos.CartDto;
import com.kshitz.kshitz.dtos.OrderProductDto;
import com.kshitz.kshitz.entities.addresses.CustomerAddress;
import com.kshitz.kshitz.entities.orders.Cart;
import com.kshitz.kshitz.entities.orders.OrderBill;
import com.kshitz.kshitz.entities.orders.OrderProduct;
import com.kshitz.kshitz.entities.products.ProductVariation;
import com.kshitz.kshitz.entities.users.Customer;
import com.kshitz.kshitz.exceptions.EntityNotFoundException;
import com.kshitz.kshitz.exceptions.NotActiveException;
import com.kshitz.kshitz.exceptions.ValidationException;
import com.kshitz.kshitz.repositories.*;
import com.kshitz.kshitz.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.kshitz.kshitz.utilities.StringConstants.ADDRESS_ERROR;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerAddressRepository customerAddressRepository;
    @Autowired
    OrderProductRepository orderProductRepository;

    @Override
    public String addCartItem(Integer productVariationId, CartDto cartDto, String username) {
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariationId);
        if (!productVariationOptional.isPresent()) {
            throw new EntityNotFoundException("No product variation found with this id");
        }
        ProductVariation productVariation = productVariationOptional.get();
        if (productVariation.getQuantityAvailable() < cartDto.getQuantity()) {
            return "Not enough stock ";
        }
        if (!productVariation.getProduct().isActive()) {
            throw new NotActiveException("Product is not active");
        }
        Cart cart = new Cart();
        cart.setProductVariation(productVariation);
        cart.setQuantity(cartDto.getQuantity());
        cart.setCustomer(customerRepository.findByUsername(username));
        int quantityLeft = productVariation.getQuantityAvailable() - cartDto.getQuantity();
        productVariation.setQuantityAvailable(quantityLeft);
        productVariationRepository.save(productVariation);
        cartRepository.save(cart);
        return "Item added to cart successfully";
    }

    @Override
    public OrderBill buyCart(Integer customerAddressId, String username) {
        Optional<CustomerAddress> customerAddressOptional = customerAddressRepository.findById(customerAddressId);
        if (!customerAddressOptional.isPresent()) {
            throw new EntityNotFoundException(ADDRESS_ERROR);
        }
        CustomerAddress customerAddress = customerAddressOptional.get();

        Customer customer = customerRepository.findByUsername(username);
        List<Cart> carts = cartRepository.findByCustomer(customer.getId());
        Double price = 0.0;
        for (Cart cart1 : carts) {
            price += cart1.getProductVariation().getPrice()*cart1.getQuantity();
        }

        OrderBill orderBill = new OrderBill();
        orderBill.setAmountPaid(price);
        orderBill.setCustomer(customer);
        orderBill.setCustomerAddress(customerAddress);
        orderBill.setDateCreated(new Date().toString());
        orderBill.setPaymentMethod("COD");
        orderRepository.save(orderBill);
        for(Cart cart:carts){
            cartRepository.deleteById(cart.getId());
        }

        return orderBill;
    }

    @Override
    public OrderBill buyProduct(Integer customerAddressId, OrderProductDto orderProductDto, String username) {
        Optional<CustomerAddress> customerAddressOptional = customerAddressRepository.findById(customerAddressId);
        if (!customerAddressOptional.isPresent()) {
            throw new EntityNotFoundException(ADDRESS_ERROR);
        }
        CustomerAddress customerAddress = customerAddressOptional.get();
        Customer customer = customerRepository.findByUsername(username);

        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(orderProductDto.getProductVariationId());
        if (!productVariationOptional.isPresent()) {
            throw new EntityNotFoundException("No product variation found with this id");
        }
        ProductVariation productVariation = productVariationOptional.get();
        if (!productVariation.getProduct().isActive()) {
            throw new NotActiveException("Product is not active");
        }
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductVariation(productVariation);
        if (productVariation.getQuantityAvailable() < orderProductDto.getQuantity()) {
            throw new ValidationException("Not enough stock");
        }
        orderProduct.setQuantity(orderProductDto.getQuantity());
        orderProduct.setPrice(productVariation.getPrice() * orderProductDto.getQuantity());
        OrderBill orderBill = new OrderBill();
        orderBill.setPaymentMethod("COD");
        orderBill.setDateCreated(new Date().toString());
        orderBill.setCustomerAddress(customerAddress);
        orderBill.setCustomer(customer);
        orderBill.setAmountPaid(productVariation.getPrice() * orderProductDto.getQuantity());
        orderRepository.save(orderBill);
        orderProduct.setOrderBill(orderBill);
        orderProductRepository.save(orderProduct);
        return orderBill;

    }
}
