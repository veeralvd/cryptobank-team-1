package com.example.cryptobank.database;


import com.example.cryptobank.domain.Customer;

public interface CustomerDAO {
    Customer save(Customer customer);

    Customer findByUsername(String username);
}
