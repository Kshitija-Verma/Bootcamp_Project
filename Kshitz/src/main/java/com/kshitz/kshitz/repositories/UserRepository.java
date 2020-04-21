package com.kshitz.kshitz.repositories;


import com.kshitz.kshitz.entities.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends  CrudRepository<User,Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByEmail(String toString);
}
