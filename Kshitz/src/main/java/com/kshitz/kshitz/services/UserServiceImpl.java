package com.kshitz.kshitz.services;

import com.kshitz.kshitz.dtos.ForgotPasswordDto;
import com.kshitz.kshitz.entities.tokens.ConfirmationToken;
import com.kshitz.kshitz.entities.tokens.ResetPasswordToken;
import com.kshitz.kshitz.entities.users.User;
import com.kshitz.kshitz.exceptions.EntityNotFoundException;
import com.kshitz.kshitz.exceptions.TokenExpiredException;
import com.kshitz.kshitz.exceptions.ValidationException;
import com.kshitz.kshitz.repositories.ConfirmationTokenRepository;
import com.kshitz.kshitz.repositories.ResetPasswordRepository;
import com.kshitz.kshitz.repositories.UserRepository;
import com.kshitz.kshitz.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.kshitz.kshitz.utilities.StringConstants.HOST_MAIL;
import static com.kshitz.kshitz.utilities.StringConstants.USER_ERROR;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    ResetPasswordRepository resetPasswordRepository;


    @Override
    public void resetUserPassword(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            logger.error(USER_ERROR);
            throw new EntityNotFoundException(USER_ERROR);
        }

        user = userRepository.findByEmail(email);
        ResetPasswordToken resetPasswordToken = resetPasswordRepository.findByUser(user);
        if (resetPasswordToken != null) {
            resetPasswordToken.calculateToken();
        } else {
            resetPasswordToken = new ResetPasswordToken(user);
        }
        resetPasswordRepository.save(resetPasswordToken);
        String emailid = user.getEmail();
        String subject = "Reset your password";
        String text = "To reset your password , please click here "
                + "http://localhost:8080/kshitz/reset-password?token=" + resetPasswordToken.getToken();
        emailService.sendEmail(emailid, subject, text, HOST_MAIL);
        logger.debug("******************* Token sent at email ********************");
    }

    @Override
    public String updatePassword(String resetToken, ForgotPasswordDto forgotPasswordDto) {
        ResetPasswordToken resetPasswordToken = resetPasswordRepository.findByToken(resetToken);
        User user = userRepository.findByEmail(resetPasswordToken.getUser().getEmail());
        Date presentDate = new Date();
        if (resetPasswordToken.getExpiryDate().compareTo(presentDate) < 0 || resetPasswordToken.getExpiryDate().compareTo(presentDate) == 0) {
            resetPasswordRepository.delete(resetPasswordToken);
            ResetPasswordToken resetPasswordToken1 = new ResetPasswordToken(user);
            resetPasswordRepository.save(resetPasswordToken1);
            String emailid = user.getEmail();
            String subject = "Token has been expired! ";
            String text = "Token is expired , please verify you email with this new link "+  "http://localhost:8080/confirm-account?token=" + resetPasswordToken1.getToken(); ;
            emailService.sendEmail(emailid, subject, text, HOST_MAIL);

        } else {


            user.setPassword(forgotPasswordDto.getPassword());
            userRepository.save(user);
            String emailid = user.getEmail();
            String subject = "Password Updated !!";
            String text = "Your password has been changed successfully!!";
            String recepient = "kshivirajput9@gmail.com";
            emailService.sendEmail(emailid, subject, text, recepient);
            resetPasswordRepository.delete(resetPasswordToken);
        }

        logger.info("Password updated successfully");
        return "Password updated successfully!!!";

    }

    @Override
    public void tokenMatch(String token) {
        ConfirmationToken confimationToken = confirmationTokenRepository.findByToken(token);
        User user = userRepository.findByEmail(confimationToken.getUser().getEmail());
        Date presentDate = new Date();
        if (confimationToken.getExpiryDate().compareTo(presentDate) < 0 || confimationToken.getExpiryDate().compareTo(presentDate) == 0) {
            confirmationTokenRepository.delete(confimationToken);
            ConfirmationToken confirmationToken1 = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken1);
            String emailid = user.getEmail();
            String subject = "Token has been expired! ";
            String text = "Token is expired , please verify you email with this new link "+  "http://localhost:8080/confirm-account?token=" + confirmationToken1.getToken(); ;
            emailService.sendEmail(emailid, subject, text, HOST_MAIL);

        } else {


            user.setActive(true);
            userRepository.save(user);
            String emailid = user.getEmail();
            String subject = "Email has been Verified !! ";
            String text = "Your account has been activated now. Enjoy shopping. ";
            emailService.sendEmail(emailid, subject, text, HOST_MAIL);
            confirmationTokenRepository.delete(confimationToken);
            logger.debug("*********** Email verified successfully *****************");
        }
    }

    @Override
    public String resetAccountLock(String username, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new EntityNotFoundException("User not found with this username !!");
        if (passwordEncoder.matches(password, user.getPassword())) {
            user.setAttempts(0);
            user.setActive(true);
            userRepository.save(user);
            logger.debug("Account is unlocked");
            return "Account unlocked successfully !";
        }
        logger.error("*****************Password not matched ******************");
        throw new ValidationException("Password not matched");

    }
}
