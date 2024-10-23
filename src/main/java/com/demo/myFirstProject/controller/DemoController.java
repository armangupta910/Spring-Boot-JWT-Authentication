package com.demo.myFirstProject.controller;

import com.demo.myFirstProject.model.Demo;
import com.demo.myFirstProject.service.DemoService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class DemoController {
    private final DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    // Secret key used for signing the JWT (same as the one used for token creation)
    // Use a manually defined secret key
    private static final String SECRET_KEY_STRING = "your-256-bit-secret-should-be-32-bytes-long";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));

    @GetMapping("/hurr")
    public ResponseEntity<String> getAllDemos(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });

        // Extract the JWT from the Authorization header
        String authorizationHeader = headers.get("authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                // Check expiration manually
                if (claims.getExpiration().before(new Date())) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT Token has expired");
                }

                String subject = claims.getSubject();
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("JWT Token Valid, Subject: " + subject);

            } catch (SignatureException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT signature");
            } catch (ExpiredJwtException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT Token has expired");
            } catch (MalformedJwtException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Malformed JWT Token");
            } catch (UnsupportedJwtException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unsupported JWT Token");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT claims string is empty");
            }

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header missing or invalid");
        }
    }

}
