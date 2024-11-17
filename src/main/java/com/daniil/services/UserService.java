package com.daniil.services;

import com.daniil.entities.User;
import com.daniil.repositories.UserRepository;
import com.daniil.services.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserValidator userValidator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> findAllUsersPageable(Pageable pageable) {
        return userRepository.findAllUsersPageable(pageable);
    }

    public Optional<User> findUserById(long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    //boolean-заглушки, возможно стоит поменять на полноценную обработку с исключениями
    public boolean saveUser(User user) {
        if (!userValidator.isSaveValid(user))
            return false;

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(long userId) {
        if (!userValidator.isDeleteValid(userId))
            return false;

        userRepository.deleteById(userId);
        return true;
    }

    public boolean changeUser(User user) {
        if (!userValidator.isChangeValid(user))
            return false;

        if (user.getPassword().isEmpty())
            userRepository.updateUsername(user.getUsername(), user.getId());
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.updateUsernameAndPassword(user.getUsername(), user.getPassword(), user.getId());
        }
        return true;
    }
}
