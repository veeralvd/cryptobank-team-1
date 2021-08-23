package com.example.cryptobank.service;

import com.example.cryptobank.database.AdminDAO;
import com.example.cryptobank.database.CustomerDAO;
import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private AdminDAO customerDAO;

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(AdminDAO customerDAO) {
        this.customerDAO = customerDAO;
        logger.info("New CustomerService");
    }

    public boolean checkIfUserCanBeRegistred(String username) {
        // TODO: 23/08/2021 checken, want vereist een Admin ->  Customer adminToCheck = customerDAO.findByUsername(username);
        //return adminToCheck == null;
        return false;
    }

}
