package com.example.cryptobank.database;


import com.example.cryptobank.domain.Customer;

import java.util.List;

public interface CustomerDAO {
    Customer save(Customer customer);

    Customer findByUsername(String username);

    List<Customer> getAll();

    String findCustomerUsernameByToken(String username);

    void insertTokenByCustomerUsername(String username, String token);
}
