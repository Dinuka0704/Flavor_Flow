package com.flavorflow.recipe_api.service;

import com.flavorflow.recipe_api.model.User;
import com.flavorflow.recipe_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // 1. Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 2. Assign default role
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles("ROLE_USER");
        }

        // 3. Save to database
        return userRepository.save(user);
    }

    // The login logic will be primarily handled by AuthenticationManager in the Controller
}