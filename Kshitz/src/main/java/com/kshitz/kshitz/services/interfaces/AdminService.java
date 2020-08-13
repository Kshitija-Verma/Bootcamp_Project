package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.entities.users.Customer;
import com.kshitz.kshitz.entities.users.Seller;

public interface AdminService {
    Iterable<Customer> displayCustomer();

    Iterable<Seller> displaySeller();

    Customer updateCustomer(String id);

    Seller updateSeller(String id);

    String deactivateCustomer(String id);

    String deactivateSeller(String id);
}
