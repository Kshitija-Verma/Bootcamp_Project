package com.kshitz.kshitz.configs;

import com.kshitz.kshitz.entities.users.Role;
import com.kshitz.kshitz.entities.users.User;
import com.kshitz.kshitz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;


    AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        List<Role> role = user.getRole();
        List<GrantAuthorityImpl> grantAuthorities = new ArrayList<>();
        for (Role value : role) {
            grantAuthorities.add(new GrantAuthorityImpl(value.getRole()));
        }

        System.out.println(user);
        if (username != null) {
            if (user.isActive())
                return new AppUser(user.getUsername(), user.getPassword(), !user.isActive(), grantAuthorities);
            else {
                throw new UsernameNotFoundException("Account is not activated");
            }

        } else {
            throw new RuntimeException();
        }

    }

}
