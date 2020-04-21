package com.kshitz.kshitz.services;

import com.kshitz.kshitz.repositories.ConfirmationTokenRepository;
import com.kshitz.kshitz.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
@Override
@Async
  public void sendEmail(String emailId, String subject, String text, String recepient) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailId);
        msg.setSubject(subject);
        msg.setText(text);
        msg.setFrom(recepient);

        javaMailSender.send(msg);

    }
}
