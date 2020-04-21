package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.entities.users.Customer;
import com.kshitz.kshitz.entities.users.Seller;

public interface AdminService {
    Iterable<Customer> displayCustomer();

    Iterable<Seller> displaySeller();

    Customer updateCustomer(Integer id);

    Seller updateSeller(Integer id);

    String deactivateCustomer(Integer id);

    String deactivateSeller(Integer id);
}
