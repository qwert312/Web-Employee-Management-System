package com.daniil.services.validators;

import com.daniil.entities.User;
import com.daniil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isSaveValid(User user) {
        if (user == null)
            return false;

        if (user.getId() != 0 || user.getUsername() == null ||
                user.getPassword() == null ||
                user.getRole() == null)
            return false;

        return userRepository.findUserByUsername(user.getUsername()).isEmpty();
    }

    public boolean isDeleteValid(long userId) {
        return userRepository.existsById(userId);
    }

    public boolean isChangeValid(User user) {
        if (user == null)
            return false;

        if (user.getId() == 0 || user.getUsername() == null ||
                user.getPassword() == null ||
                user.getRole() == null)
            return false;

        return userRepository.existsById(user.getId());
    }
}
