package com.kshitz.kshitz.services.interfaces;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendEmail(String emailId, String subject, String text, String recepient);
}
