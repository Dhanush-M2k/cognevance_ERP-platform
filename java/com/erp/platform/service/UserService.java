package com.erp.platform.service;

import com.erp.platform.model.Role;
import com.erp.platform.model.User;
import com.erp.platform.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User registerCustomer(String username, String email, String rawPassword, String fullName) {
        User user = new User(username, email, passwordEncoder.encode(rawPassword), fullName, Role.CUSTOMER);
        return userRepository.save(user);
    }

    public long countUsers() {
        return userRepository.count();
    }
}
