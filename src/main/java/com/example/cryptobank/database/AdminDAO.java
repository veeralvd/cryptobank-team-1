package com.example.cryptobank.database;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.User;

public interface AdminDAO {

    Admin save(Admin admin);

    Admin findByUsername(String username);
}
