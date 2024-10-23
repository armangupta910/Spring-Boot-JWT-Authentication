package com.demo.myFirstProject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password; // Consider hashing passwords in production

    public String getPassword(){
        return password;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String newPass){
        password = newPass;
    }

}

