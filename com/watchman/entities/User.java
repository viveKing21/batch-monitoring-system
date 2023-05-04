package com.watchman.entities;

import java.io.Serializable;

import com.watchman.services.DatabaseService;

public class User implements DatabaseService, Serializable{
    final private String ID;
    final public String USERNAME;
    final public String ROLE;
    final public String PASSWORD;
    public UserDetails details;
    
    public User(String id, String username, String password, String role, UserDetails userDetails) {
        ID = id;
        USERNAME = username;
        ROLE = role;
        PASSWORD = password;
        details = userDetails;
    }

    @Override
    public String getID() {
        return ID;
    }
}
