package com.demo.myFirstProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.demo.myFirstProject.model.User;
import com.demo.myFirstProject.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Optional;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import com.demo.myFirstProject.util.JwtResponse;
import java.util.Base64;



@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        return userService.signup(user);
    }

    // Use a manually defined secret key
    private static final String SECRET_KEY_STRING = "your-256-bit-secret-should-be-32-bytes-long";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));


    public static String generateToken(String username) {
        long expirationTimeMillis = 1000 * 60 * 60; // 1 hour expiration time

        return Jwts.builder()
                .setSubject(username)  // Payload: subject is typically the username
                .setIssuedAt(new Date())  // Set token creation time
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))  // Expiration time
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)  // Sign the token with the secret key
                .compact();  // Build and return the JWT as a String
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User loginRequest) {
        Optional<User> user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        
        if (user.isPresent()) {
            // Generate the JWT token
            String jwt = generateToken(loginRequest.getUsername());

            // Split the JWT into its components
            String[] jwtParts = jwt.split("\\.");

            // Decode the Header and Payload (they are Base64 encoded)
            String header = new String(Base64.getUrlDecoder().decode(jwtParts[0]), StandardCharsets.UTF_8);
            String payload = new String(Base64.getUrlDecoder().decode(jwtParts[1]), StandardCharsets.UTF_8);
            String signature = jwtParts[2]; // Signature is not Base64 encoded, it's already a string

            // Create the response with the token and its parts
            JwtResponse jwtResponse = new JwtResponse(jwt, header, payload, signature);

            // Return the structured response as JSON
            return ResponseEntity.ok(jwtResponse);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
}

