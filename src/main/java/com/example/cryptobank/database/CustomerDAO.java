package com.example.cryptobank.database;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;

import java.util.List;

public interface CustomerDAO {
    Customer save(Customer customer);

    Customer findByUsername(String username);

    List<Customer> getAll();
}
