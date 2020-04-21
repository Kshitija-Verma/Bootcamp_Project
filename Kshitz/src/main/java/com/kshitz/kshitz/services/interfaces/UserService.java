package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.dtos.ForgotPasswordDto;

public interface UserService  {
    void resetUserPassword(String email);

    String updatePassword(String resetToken, ForgotPasswordDto forgotPasswordDto);

    void tokenMatch(String token);

    String resetAccountLock(String username, String password);
}
