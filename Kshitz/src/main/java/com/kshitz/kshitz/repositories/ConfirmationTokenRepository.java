package com.kshitz.kshitz.repositories;

import com.kshitz.kshitz.entities.tokens.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken,Integer> {

    ConfirmationToken findByToken(String token);
}
