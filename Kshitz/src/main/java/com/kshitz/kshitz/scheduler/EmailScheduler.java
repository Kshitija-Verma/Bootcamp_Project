package com.kshitz.kshitz.scheduler;

import com.kshitz.kshitz.entities.users.Seller;
import com.kshitz.kshitz.repositories.SellerRepository;
import com.kshitz.kshitz.services.interfaces.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;

import static com.kshitz.kshitz.utilities.StringConstants.HOST_MAIL;

@EnableAsync(proxyTargetClass = true)
@Component
public class EmailScheduler {

    @Autowired
    EmailService emailService;

    @Autowired
    SellerRepository sellerRepository;

    @Scheduled(cron = "0 0 00 * * ?")
    public void run(){
       Iterable<Seller> sellers = sellerRepository.findAll();
        Iterator<Seller> sellerIterator = sellers.iterator();
        while (sellerIterator.hasNext()) {
           Seller seller = sellerIterator.next();
            String emailId = seller.getEmail();
            String subject = "Order cancelled";
            String text = "your order has been cancelled";
            emailService.sendEmail(emailId, subject, text, HOST_MAIL);
        }
    }
}
