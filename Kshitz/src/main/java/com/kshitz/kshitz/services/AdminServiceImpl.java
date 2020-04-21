package com.kshitz.kshitz.services;

import com.kshitz.kshitz.entities.users.Customer;
import com.kshitz.kshitz.entities.users.Seller;
import com.kshitz.kshitz.exceptions.EntityNotFoundException;
import com.kshitz.kshitz.repositories.CustomerRepository;
import com.kshitz.kshitz.repositories.SellerRepository;
import com.kshitz.kshitz.services.interfaces.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.kshitz.kshitz.utilities.StringConstants.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmailServiceImpl emailService;
    Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public Iterable<Customer> displayCustomer() {
        logger.info("******************List of all customers **********************");
        return customerRepository.findAll();

    }

    @Override
    public Iterable<Seller> displaySeller() {
        return sellerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Integer id) {

        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent())
            throw new EntityNotFoundException("Customer with this id is not present");
        Customer customer1 = customer.get();
        if (customer1.isActive())
            return customer1;
        else
            customer1.setActive(true);
        logger.debug("*********************Customer is not activated *********************");
        return customer1;

    }

    @Override
    public Seller updateSeller(Integer id) {

        Optional<Seller> seller = sellerRepository.findById(id);
        if (!seller.isPresent())
            throw new EntityNotFoundException("Seller with this id is not found");
        Seller seller1 = seller.get();
        if (seller1.isActive())
            return seller1;
        else
            seller1.setActive(true);
        logger.debug("**********************Seller is now activated ***********************");
        return seller1;

    }

    @Override
    public String deactivateCustomer(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            logger.error(USER_ERROR);
            throw new EntityNotFoundException(USER_ERROR);
        }
        Customer customer1 = customer.get();
        if (customer1.isActive()) {
            customer1.setActive(false);
            String emailid = customer1.getEmail();
            String subject = "Your account has been deactivated";
            String text = "Your account is deactivated by the organisation due to violation of certain norms";
            emailService.sendEmail(emailid, subject, text, HOST_MAIL);
            logger.debug(ACCOUNT_DEACTIVATION);
            return ACCOUNT_DEACTIVATION;
        }
        if (!customer1.isActive()) {
            logger.debug(ACCOUNT_DEACTIVATION);
            return ACCOUNT_DEACTIVATION;

        }
        return null;
    }

    @Override
    public String deactivateSeller(Integer id) {
        Optional<Seller> seller = sellerRepository.findById(id);
        if (!seller.isPresent()) {
            logger.error(SELLER_ERROR);
            throw new EntityNotFoundException(SELLER_ERROR);
        }
        Seller seller1 = seller.get();
        if (seller1.isActive()) {
            seller1.setActive(false);
            String emailid = seller1.getEmail();
            String subject = "Your account has been deactivated";
            String text = "Your account is deactivated by the organisation, due to violation of certain norms.";
            emailService.sendEmail(emailid, subject, text, HOST_MAIL);
            logger.debug(ACCOUNT_DEACTIVATION);
            return ACCOUNT_DEACTIVATION;
        } else if (!seller1.isActive()) {
            logger.debug("Account is already deactivated.");
            return ACCOUNT_DEACTIVATION;

        }
        return null;

    }
}
