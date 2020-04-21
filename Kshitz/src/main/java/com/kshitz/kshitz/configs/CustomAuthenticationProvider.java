package com.kshitz.kshitz.configs;

import com.kshitz.kshitz.entities.users.Role;
import com.kshitz.kshitz.entities.users.User;
import com.kshitz.kshitz.repositories.UserRepository;
import com.kshitz.kshitz.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Username is not correct");

        if (user.getUsername().equals(username) && passwordEncoder.matches(password, user.getPassword())) {

            if (user.getAttempts() < 3 && user.isActive()) {
                List<Role> role = user.getRole();
                Role roles = role.get(0);
                return new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(new GrantAuthorityImpl(roles.getRole())));

            }
            String emailid = user.getEmail();
            String subject = "Account is Unreachable";
            String text = "Your account is either locked or not activated yet !! ";
            String recepient = "kshivirajput9@gmail.com";
            emailService.sendEmail(emailid, subject, text, recepient);
            throw new LockedException("User account is unreachable due to either being locked or not active !");

        } else {
            Integer numberOfAttempts = user.getAttempts();
            user.setAttempts(++numberOfAttempts);
            userRepository.save(user);


            if (user.getAttempts() >= 3) {
                String emailid = user.getEmail();
                String subject = "Account Locked ";
                String text = "Your account has been locked !! ";
                String recepient = "kshivirajput9@gmail.com";
                emailService.sendEmail(emailid, subject, text, recepient);
                throw new LockedException("User account is locked");

            }

            throw new BadCredentialsException("Password is incorrect");

        }
    }


    @Override
    public boolean supports(Class<?> aClass) {

        return aClass.equals(UsernamePasswordAuthenticationToken.class);

    }

}
