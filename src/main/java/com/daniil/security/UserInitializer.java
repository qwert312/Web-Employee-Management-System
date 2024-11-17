package com.daniil.security;

import com.daniil.entities.User;
import com.daniil.entities.enums.Role;
import com.daniil.repositories.UserRepository;
import com.daniil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserInitializer implements CommandLineRunner {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            user.setRole(Role.ADMIN);
            userService.saveUser(user);
        }
    }
}
