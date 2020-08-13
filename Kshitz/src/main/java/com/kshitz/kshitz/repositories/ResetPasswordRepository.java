package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.tokens.ResetPasswordToken;
import com.kshitz.kshitz.entities.users.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends CrudRepository<ResetPasswordToken,String> {

    ResetPasswordToken findByUser(User user);


    @Query("{'token':?0}")
    ResetPasswordToken findByToken(String resetToken);
}
