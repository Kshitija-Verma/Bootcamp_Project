package com.kshitz.kshitz.services;

import com.kshitz.kshitz.dtos.CartDto;
import com.kshitz.kshitz.dtos.OrderProductDto;
import com.kshitz.kshitz.entities.addresses.CustomerAddress;
import com.kshitz.kshitz.entities.orders.Cart;
import com.kshitz.kshitz.entities.orders.OrderBill;
import com.kshitz.kshitz.entities.orders.OrderProduct;
import com.kshitz.kshitz.entities.orders.ViewOrderDetails;
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
import java.util.Iterator;
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
    @Autowired
    OrderBillRepository orderBillRepository;
    @Autowired
    ViewOrderDetailsRepository viewOrderDetailsRepository;

    @Override
    public String addCartItem(String  productVariationId, CartDto cartDto, String username) {
        Optional<ProductVariation> productVariationOptional = this.productVariationRepository.findById(productVariationId);
        if (!productVariationOptional.isPresent()) {
            throw new EntityNotFoundException("No product variation found with this id");
        } else {
            ProductVariation productVariation = (ProductVariation)productVariationOptional.get();
            if (productVariation.getQuantityAvailable() < cartDto.getQuantity()) {
                return "Not enough stock ";
            } else if (!productVariation.getProduct().isActive()) {
                throw new NotActiveException("Product is not active");
            } else {
                Cart cart = new Cart();
                cart.setProductVariation(productVariation);
                cart.setQuantity(cartDto.getQuantity());
                cart.setCustomer(this.customerRepository.findByUsername(username));
                int quantityLeft = productVariation.getQuantityAvailable() - cartDto.getQuantity();
                productVariation.setQuantityAvailable(quantityLeft);
                this.productVariationRepository.save(productVariation);
                this.cartRepository.save(cart);
                return "Item added to cart successfully";
            }
        }
    }

    @Override
    public OrderBill buyCart(String customerAddressId, String username) {
        Optional<CustomerAddress> customerAddressOptional = this.customerAddressRepository.findById(customerAddressId);
        if (!customerAddressOptional.isPresent()) {
            throw new EntityNotFoundException("Address not found");
        } else {
            CustomerAddress customerAddress = (CustomerAddress)customerAddressOptional.get();
            Customer customer = this.customerRepository.findByUsername(username);
            List<Cart> carts = this.cartRepository.findByCustomer(customer.getId());
            Double price = 0.0D;

            Cart cart1;
            for(Iterator var8 = carts.iterator(); var8.hasNext(); price = price + cart1.getProductVariation().getPrice() * (double)cart1.getQuantity()) {
                cart1 = (Cart)var8.next();
            }

            OrderBill orderBill = new OrderBill();
            orderBill.setAmountPaid(price);
            orderBill.setCustomer(customer);
            orderBill.setCity(customerAddress.getCity());
            orderBill.setCountry(customerAddress.getCountry());
            orderBill.setState(customerAddress.getState());
            orderBill.setZipCode(customerAddress.getZipCode());
            orderBill.setLabel(customerAddress.getLabel());
            orderBill.setDateCreated((new Date()).toString());
            orderBill.setPaymentMethod("COD");
            this.orderRepository.save(orderBill);
            Iterator var12 = carts.iterator();

            while(var12.hasNext()) {
                Cart cart = (Cart)var12.next();
                this.cartRepository.deleteById(cart.getId());
            }

            return orderBill;
        }
    }

    @Override
    public ViewOrderDetails viewOrderDetails(String username) {
        Customer customer = this.customerRepository.findByUsername(username);
        List<Cart> carts = this.cartRepository.findByCustomer(customer.getId());
        Double price = 0.0D;

        Cart cart1;
        for(Iterator var5 = carts.iterator(); var5.hasNext(); price = price + cart1.getProductVariation().getPrice() * (double)cart1.getQuantity()) {
            cart1 = (Cart)var5.next();
        }

        ViewOrderDetails viewOrderDetails = new ViewOrderDetails();
        viewOrderDetails.setAmountPaid(price);
        viewOrderDetails.setPaymentMethod("COD");
        viewOrderDetails.setDateCreated((new Date()).toString());
        return viewOrderDetailsRepository.save(viewOrderDetails);
    }

    @Override
    public OrderBill buyProduct(String customerAddressId, OrderProductDto orderProductDto, String username) {
        Optional<CustomerAddress> customerAddressOptional = customerAddressRepository.findById(customerAddressId);
        if (!customerAddressOptional.isPresent()) {
            throw new EntityNotFoundException("Address not found");
        } else {
            CustomerAddress customerAddress = (CustomerAddress)customerAddressOptional.get();
            Customer customer = customerRepository.findByUsername(username);
            Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(orderProductDto.getProductVariationId());
            if (!productVariationOptional.isPresent()) {
                throw new EntityNotFoundException("No product variation found with this id");
            } else {
                ProductVariation productVariation = (ProductVariation)productVariationOptional.get();
                if (!productVariation.getProduct().isActive()) {
                    throw new NotActiveException("Product is not active");
                } else {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProductVariation(productVariation);
                    if (productVariation.getQuantityAvailable() < orderProductDto.getQuantity()) {
                        throw new ValidationException("Not enough stock");
                    } else {
                        orderProduct.setQuantity(orderProductDto.getQuantity());
                        orderProduct.setPrice(productVariation.getPrice() * (double)orderProductDto.getQuantity());
                        OrderBill orderBill = new OrderBill();
                        orderBill.setPaymentMethod("COD");
                        orderBill.setDateCreated((new Date()).toString());
                        orderBill.setCity(customerAddress.getCity());
                        orderBill.setCountry(customerAddress.getCountry());
                        orderBill.setState(customerAddress.getState());
                        orderBill.setZipCode(customerAddress.getZipCode());
                        orderBill.setLabel(customerAddress.getLabel());
                        orderBill.setCustomer(customer);
                        orderBill.setAmountPaid(productVariation.getPrice() * (double)orderProductDto.getQuantity());
                        this.orderRepository.save(orderBill);
                        orderProduct.setOrderBill(orderBill);
                        this.orderProductRepository.save(orderProduct);
                        return orderBill;
                    }
                }
            }
        }
    }

    @Override
    public List<Cart> viewCart(String username) {
        Customer customer = this.customerRepository.findByUsername(username);
        return this.cartRepository.findByCustomer(customer.getId());
    }

    @Override
    public List<Cart> removeFromCart(String id, String username) {
        Customer customer = this.customerRepository.findByUsername(username);
        this.cartRepository.deleteById(id);
        return this.cartRepository.findByCustomer(customer.getId());
    }

    @Override
    public List<OrderBill> viewOrders(String username) {
        Customer customer = this.customerRepository.findByUsername(username);
        return this.orderBillRepository.findByCustomerId(customer.getId());
    }
}
