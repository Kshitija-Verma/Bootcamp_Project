package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.tokens.ResetPasswordToken;
import com.kshitz.kshitz.entities.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends CrudRepository<ResetPasswordToken,Integer> {

    ResetPasswordToken findByUser(User user);

    ResetPasswordToken findByToken(String resetToken);
}
