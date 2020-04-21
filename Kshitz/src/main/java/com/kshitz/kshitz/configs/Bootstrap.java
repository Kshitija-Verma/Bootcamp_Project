package com.kshitz.kshitz.configs;

import com.kshitz.kshitz.entities.users.Role;
import com.kshitz.kshitz.entities.users.User;
import com.kshitz.kshitz.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class Bootstrap implements ApplicationRunner {
    Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() < 1) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User user = new User();
            user.setAttempts(0);
            user.setFirstName("Kshitija");
            user.setMiddleName("Verma");
            user.setLastName("Rajput");
            user.setEmail("kshitija@gmail.com");
            user.setUsername("kshitija");
            user.setPassword(passwordEncoder.encode("Kshitz@123"));
            Role role = new Role();
            role.setRole("ROLE_ADMIN");
            user.setRole(Collections.singletonList(role));
            user.setDeleted(false);
            user.setActive(true);
            userRepository.save(user);
            logger.info("*********************Admin added successfully*****************");

        }
    }
}