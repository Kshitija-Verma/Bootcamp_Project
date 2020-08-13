package com.kshitz.kshitz.repositories;


import com.kshitz.kshitz.entities.users.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends  CrudRepository<User,String> {

    @Query("{'username' : ?0 }")
    User findByUsername(String username);

    @Query("{'email' : ?0 }")
    User findByEmail(String email);

    boolean existsByEmail(String toString);
}
