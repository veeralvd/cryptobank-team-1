package com.example.cryptobank.domain;

public class Admin extends User{


    public Admin(String username, String password, String salt, String token) {
        super(username, password, salt, token);
    }

    public Admin(String username, String password) {
        this(username, password, null, null);
    }


    @Override
    public int compareTo(User o) {
        return 0;
    }

}
