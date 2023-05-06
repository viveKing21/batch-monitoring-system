package com.watchman.entities;

import java.io.Serializable;

public class UserDetails implements Serializable {
    public String number;
    public String state;
    public String city;
    public int zipcode;
    public UserDetails(String number, String state, String city, int zipcode) {
        this.number = number;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
    }

    
}
