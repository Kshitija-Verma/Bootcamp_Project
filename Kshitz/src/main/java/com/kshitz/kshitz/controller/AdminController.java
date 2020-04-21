package com.kshitz.kshitz.controller;

import com.kshitz.kshitz.entities.users.Customer;
import com.kshitz.kshitz.entities.users.Seller;
import com.kshitz.kshitz.services.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;

    @GetMapping("/admin/customer-details")
    public Iterable<Customer> showCustomerDetails() {
        return adminService.displayCustomer();
    }

    @GetMapping("/admin/seller-details")
    public Iterable<Seller> showSellerDetails() {

        return adminService.displaySeller();
    }

    @PutMapping("/admin/activate-customer/{id}")
    public ResponseEntity<Object> activateCustomer(@PathVariable Integer id) {
        Customer customer1 = adminService.updateCustomer(id);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer1.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/admin/activate-seller/{id}")
    public ResponseEntity<Object> activateSeller(@PathVariable Integer id) {
        Seller seller1 = adminService.updateSeller(id);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(seller1.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/admin/deactivate-customer/{id}")
    public String deactivateCustomer(@PathVariable Integer id) {
        return adminService.deactivateCustomer(id);

    }

    @PutMapping("/admin/deactivate-seller/{id}")
    public String deactivateSeller(@PathVariable Integer id) {
        return adminService.deactivateSeller(id);

    }
}
