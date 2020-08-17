package com.kshitz.kshitz.services;

import com.kshitz.kshitz.dtos.AddressDto;
import com.kshitz.kshitz.dtos.RegisterCustomerDto;
import com.kshitz.kshitz.dtos.UpdateCustomerDto;
import com.kshitz.kshitz.dtos.UpdatePasswordDto;
import com.kshitz.kshitz.entities.addresses.CustomerAddress;
import com.kshitz.kshitz.entities.tokens.ConfirmationToken;
import com.kshitz.kshitz.entities.users.Customer;
import com.kshitz.kshitz.entities.users.Role;
import com.kshitz.kshitz.entities.users.User;
import com.kshitz.kshitz.exceptions.EntityNotFoundException;
import com.kshitz.kshitz.exceptions.NotUniqueException;
import com.kshitz.kshitz.exceptions.ValidationException;
import com.kshitz.kshitz.repositories.ConfirmationTokenRepository;
import com.kshitz.kshitz.repositories.CustomerAddressRepository;
import com.kshitz.kshitz.repositories.CustomerRepository;
import com.kshitz.kshitz.repositories.SellerRepository;
import com.kshitz.kshitz.services.interfaces.CustomerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.kshitz.kshitz.utilities.StringConstants.ADDRESS_ERROR;
import static com.kshitz.kshitz.utilities.StringConstants.HOST_MAIL;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerAddressRepository customerAddressRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    ModelMapper modelMapper;
    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public String saveCustomer(RegisterCustomerDto registerCustomerDto) {
        if (!registerCustomerDto.getProfileImage().matches("([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)")) {
            logger.error("*************Format of image is not correct *****************");
            throw new ValidationException("format of image is not correct(either use jpg or png)");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!registerCustomerDto.getPassword().equals(registerCustomerDto.getConfirmPassword())) {
            logger.error("Please type same password in both the fields !");
            throw new ValidationException("Password not matched!!!");
        }
        Customer customer = modelMapper.map(registerCustomerDto, Customer.class);
        customer.setPassword(passwordEncoder.encode(registerCustomerDto.getPassword()));
        Role role = new Role();
        role.setRole("ROLE_CUSTOMER");
        customer.setRole(Collections.singletonList(role));
        customer.setDeleted(false);
        customer.setActive(false);
        if (sellerRepository.findByUsername(registerCustomerDto.getUserName()) != null || customerRepository.findByEmail(registerCustomerDto.getEmail()) != null) {
            logger.error("****************Email or username not unique***************");
            throw new ValidationException("Email and username must be unique");
        }
        customerRepository.save(customer);
        logger.info("Customer registered successfully !!!");
        ConfirmationToken confirmationToken = new ConfirmationToken(customer);
        confirmationTokenRepository.save(confirmationToken);
        String emailid = customer.getEmail();
        String subject = "Verify your email address ";
        String text = "To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getToken();

        emailService.sendEmail(emailid, subject, text, HOST_MAIL);
        logger.debug("***********Customer added now confirm account through email************");
        return customer.getUsername();

    }


    @Override
    public User listDetails(String username) {
        logger.info("************list of customers***************");
        return customerRepository.findByUsername(username);
    }

    @Override
    public List<CustomerAddress> listAddress(String username) {
        Customer customer = customerRepository.findByUsername(username);
        return customerAddressRepository.findAllByCustomer(customer);
    }

    @Override
    public String updateProfile(UpdateCustomerDto updateCustomerDto, String username) {
        Customer customer = customerRepository.findByUsername(username);
        if (updateCustomerDto.getFirstName() != null)
            customer.setFirstName(updateCustomerDto.getFirstName());
        if (updateCustomerDto.getMiddleName() != null)
            customer.setMiddleName(updateCustomerDto.getMiddleName());
        if (updateCustomerDto.getLastName() != null)
            customer.setLastName(updateCustomerDto.getLastName());
        if (updateCustomerDto.getEmail() != null) {
            if (!updateCustomerDto.getEmail().matches("^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$"))
                throw new ValidationException("Email must be valid !");
            if ((customerRepository.findByEmail(updateCustomerDto.getEmail()) != null))
                throw new NotUniqueException("Email already exists");
            customer.setEmail(updateCustomerDto.getEmail());
        }
        if (updateCustomerDto.getContactNumber() != null) {
            if (!updateCustomerDto.getContactNumber().matches("^[0-9]*$"))
                throw new ValidationException("Phone number must contain numbers only");
            customer.setContact(updateCustomerDto.getContactNumber());
        }
        customerRepository.save(customer);
        logger.info("***********Profile updated*************");
        return "Profile updated successfully";
    }

    @Override
    public String updatePassword(UpdatePasswordDto updatePasswordDto, String username) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Customer customer = customerRepository.findByUsername(username);
        if (passwordEncoder.matches(updatePasswordDto.getOldPassword(), customer.getPassword())) {
            if (updatePasswordDto.getNewPassword().matches(updatePasswordDto.getConfirmPassword())) {
                customer.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
                customerRepository.save(customer);
                logger.debug("****************Password updated successfully*************");
                return "Password updated successfully";
            }
            throw new ValidationException("password and confirm password not matched !");
        }

        throw new ValidationException("Old password is not correct");
    }

    @Override
    public String addNewAddress(AddressDto addressDto, String username) {
        Customer customer = customerRepository.findByUsername(username);
        CustomerAddress customerAddress = modelMapper.map(addressDto, CustomerAddress.class);
        customerAddress.setCustomer(customer);
        customerAddressRepository.save(customerAddress);
        logger.debug("***********Address updated successfully*************");
        return "Address added successfully";
    }

    @Override
    public String deleteCustomerAddress(String id, String username) {
        Customer customer = customerRepository.findByUsername(username);
        Optional<CustomerAddress> customerAddress = this.customerAddressRepository.findById(id);
        if (!customerAddress.isPresent()) {
            throw new EntityNotFoundException("Address not found");
        } else {
            this.logger.debug("****************Address deleted successfully**************");
            this.customerAddressRepository.deleteById(id);
        }
        return "Address deleted successfully";

        }



    @Override
    public String updateCustomerAddress(String id, AddressDto addressDto, String username) {
        Customer customer = customerRepository.findByUsername(username);
        Optional<CustomerAddress> customerAddress = customerAddressRepository.findById(id);
        if (!customerAddress.isPresent()) {
            throw new EntityNotFoundException(ADDRESS_ERROR);
        }
        CustomerAddress customerAddress1 = customerAddress.get();
        if (customerAddress1.getCustomer() == customer) {
            if (addressDto.getCity() != null)
                customerAddress1.setCity(addressDto.getCity());
            if (addressDto.getCountry() != null)
                customerAddress1.setCountry(addressDto.getCountry());
            if (addressDto.getState() != null)
                customerAddress1.setState(addressDto.getState());
            if (addressDto.getZipCode() != null)
                customerAddress1.setZipCode(addressDto.getZipCode());
            if (addressDto.getLabel() != null)
                customerAddress1.setLabel(addressDto.getLabel());
            customerAddressRepository.save(customerAddress1);
            logger.debug("*************Address updated successfully***********");
            return "Address updated successfully";
        }
        throw new EntityNotFoundException("Address not belong to :" + username);
    }
}
