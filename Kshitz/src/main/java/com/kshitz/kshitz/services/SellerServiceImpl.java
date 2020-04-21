package com.kshitz.kshitz.services;

import com.kshitz.kshitz.dtos.AddressDto;
import com.kshitz.kshitz.dtos.RegisterSellerDto;
import com.kshitz.kshitz.dtos.UpdatePasswordDto;
import com.kshitz.kshitz.dtos.UpdateSellerDto;
import com.kshitz.kshitz.entities.addresses.SellerAddress;
import com.kshitz.kshitz.entities.tokens.ConfirmationToken;
import com.kshitz.kshitz.entities.users.Role;
import com.kshitz.kshitz.entities.users.Seller;
import com.kshitz.kshitz.entities.users.User;
import com.kshitz.kshitz.exceptions.EntityNotFoundException;
import com.kshitz.kshitz.exceptions.NotUniqueException;
import com.kshitz.kshitz.exceptions.ValidationException;
import com.kshitz.kshitz.repositories.ConfirmationTokenRepository;
import com.kshitz.kshitz.repositories.SellerAddressRepository;
import com.kshitz.kshitz.repositories.SellerRepository;
import com.kshitz.kshitz.services.interfaces.SellerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.kshitz.kshitz.utilities.StringConstants.ADDRESS_ERROR;
import static com.kshitz.kshitz.utilities.StringConstants.HOST_MAIL;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    SellerAddressRepository sellerAddressRepository;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(SellerServiceImpl.class);

    @Override
    public String saveSeller(RegisterSellerDto registerSellerDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Seller seller = modelMapper.map(registerSellerDto,Seller.class);
        if(sellerRepository.findByUsername(registerSellerDto.getUsername())!=null||sellerRepository.findByEmail(registerSellerDto.getEmail())!=null)
        {
            logger.error("*********** Email or username is not unique ***************");
            throw new ValidationException("Email and username must be unique");
        }
        SellerAddress sellerAddress = modelMapper.map(registerSellerDto,SellerAddress.class);
       if (!registerSellerDto.getPassword().equals(registerSellerDto.getConfirmPassword()))
       {
           throw new ValidationException("Password not matched !!!");
       }
        seller.setPassword(passwordEncoder.encode(registerSellerDto.getPassword()));
       Role role = new Role();
        role.setRole("ROLE_SELLER");
      seller.setRole(Collections.singletonList(role));
       seller.setActive(false);
      seller.setDeleted(false);
       sellerRepository.save(seller);
       sellerAddress.setSeller(seller);
        sellerAddressRepository.save(sellerAddress);
        logger.info("Seller registered successfully !!!");
        ConfirmationToken confirmationToken = new ConfirmationToken(seller);
        confirmationTokenRepository.save(confirmationToken);
        String emailId = seller.getEmail();

        String subject = "Verify your email address ";
        String text = "To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getToken();

        emailService.sendEmail(emailId, subject, text, HOST_MAIL);
        logger.debug("************** Seller added successfully , now confirm account through email ***********");
        return seller.getUsername();
    }


    @Override
    public User listDetails(String username) {
        logger.info("************ list of sellers ********************");
        return sellerRepository.findByUsername(username);
    }

    @Override
    public String updateProfile(UpdateSellerDto updateSellerDto, String username) {
        boolean flag=false;
        Seller seller = sellerRepository.findByUsername(username);
        if (updateSellerDto.getFirstName() != null) {
            seller.setFirstName(updateSellerDto.getFirstName());
            flag=true;
        }
        if (updateSellerDto.getMiddleName() != null) {
            seller.setMiddleName(updateSellerDto.getMiddleName());
            flag=true;
        }
        if (updateSellerDto.getLastName() != null) {
            seller.setLastName(updateSellerDto.getLastName());
            flag=true;
        }
        if (updateSellerDto.getGst() != null)
        {
            seller.setGst(updateSellerDto.getGst());
            flag=true;
        }
        if (updateSellerDto.getCompanyName() != null)
        {
            seller.setCompanyName(updateSellerDto.getCompanyName());
            flag=true;
        }
        if (updateSellerDto.getEmail() != null) {
            if (!updateSellerDto.getEmail().matches("^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$"))
            {
                logger.error("************Email not valid****************");
                throw new ValidationException("Email must be valid !");
            }
            if((sellerRepository.findByEmail(updateSellerDto.getEmail())!=null))
                throw new NotUniqueException("Email already exists");
            seller.setEmail(updateSellerDto.getEmail());
            flag=true;
        }
        if (updateSellerDto.getCompanyContact() != null) {
            if (!updateSellerDto.getCompanyContact().matches("^[0-9]*$"))
            {
                logger.error("**********Phone number is not valid*************");
                throw new ValidationException("Phone number must contain numbers only");
            }
            seller.setCompanyContact(updateSellerDto.getCompanyContact());
            flag=true;
        }
           sellerRepository.save(seller);
        if(!flag)
            return "No field found for updation";

           return "Profile updated successfully";
    }

    @Override
    public String updatePassword(UpdatePasswordDto updatePasswordDto, String username) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Seller seller = sellerRepository.findByUsername(username);
        if (passwordEncoder.matches(updatePasswordDto.getOldPassword(), seller.getPassword())) {
            if (updatePasswordDto.getNewPassword().matches(updatePasswordDto.getConfirmPassword())) {
                seller.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
                sellerRepository.save(seller);
                logger.debug("**********Password updated**************");
                return "Password updated successfully";
            }
            logger.error("**********Password and confirm password not matched***********");
            throw new ValidationException("password and confirm password not matched !");
        }
        logger.error("*************Old password not correct***********************");

        throw new ValidationException("Old password is not correct");
    }

    @Override
    public String updateSellerAddress(AddressDto addressDto, String username) {
        boolean flag=false;
        Seller seller = sellerRepository.findByUsername(username);
        SellerAddress sellerAddress1 = sellerAddressRepository.findBySeller(seller);
        if (sellerAddress1 == null) {
            throw new EntityNotFoundException(ADDRESS_ERROR);
        }

            if (addressDto.getCity() != null)
            {
                sellerAddress1.setCity(addressDto.getCity());
                flag=true;
            }
            if (addressDto.getCountry() != null)
            {
                sellerAddress1.setCountry(addressDto.getCountry());
                flag=true;
            }
            if (addressDto.getState() != null)
            {
                sellerAddress1.setState(addressDto.getState());
                flag=true;
            }
            if (addressDto.getZipCode() != null)
            {
                sellerAddress1.setZipCode(addressDto.getZipCode());
                flag=true;
            }
            if (addressDto.getLabel() != null)
            {
                sellerAddress1.setLabel(addressDto.getLabel());
                flag=true;
            }
            sellerAddressRepository.save(sellerAddress1);
            if(!flag)
                return "field not found for updation";
            logger.debug("*************Address updated successfully************");
            return "Address updated successfully";

        }
    }
