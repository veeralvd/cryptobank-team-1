package com.example.cryptobank.database;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;

public interface CustomerDAO {
    Customer save(Admin admin);

    Customer findByUsername(String username);
}
