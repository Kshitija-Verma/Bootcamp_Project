package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.dtos.AddressDto;
import com.kshitz.kshitz.dtos.RegisterSellerDto;
import com.kshitz.kshitz.dtos.UpdatePasswordDto;
import com.kshitz.kshitz.dtos.UpdateSellerDto;
import com.kshitz.kshitz.entities.users.User;

public interface SellerService {
    String saveSeller(RegisterSellerDto registerSellerDto);

    User listDetails(String username);

    String updateProfile(UpdateSellerDto updateSellerDto, String username);

    String updatePassword(UpdatePasswordDto updatePasswordDto, String username);

    String updateSellerAddress( AddressDto addressDto, String username);
}
