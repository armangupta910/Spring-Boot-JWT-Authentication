package com.demo.myFirstProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.demo.myFirstProject.model.User;
import com.demo.myFirstProject.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String signup(User user) {
        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username is already taken.";  // Return a message if the username is already taken
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save the new user to the database
        userRepository.save(user);
        return "User registered successfully.";
    }

    public Optional<User> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        
        // Verify the password
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;  // Successful login
        }

        return Optional.empty();  // Invalid login
    }
}

