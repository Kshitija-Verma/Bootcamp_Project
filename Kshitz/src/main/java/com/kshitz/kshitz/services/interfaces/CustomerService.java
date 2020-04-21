package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.dtos.AddressDto;
import com.kshitz.kshitz.dtos.RegisterCustomerDto;
import com.kshitz.kshitz.dtos.UpdateCustomerDto;
import com.kshitz.kshitz.dtos.UpdatePasswordDto;
import com.kshitz.kshitz.entities.addresses.CustomerAddress;
import com.kshitz.kshitz.entities.users.User;

import java.util.List;

public interface CustomerService {
    String saveCustomer(RegisterCustomerDto registerCustomerDto);

    User listDetails(String username);

    List<CustomerAddress> listAddress(String username);

    String updateProfile(UpdateCustomerDto updateCustomerDto, String username);

    String updatePassword(UpdatePasswordDto updatePasswordDto, String username);

    String addNewAddress(AddressDto addressDto, String username);

    String deleteCustomerAddress(Integer id, String username);

    String updateCustomerAddress(Integer id, AddressDto addressDto, String username);
}
