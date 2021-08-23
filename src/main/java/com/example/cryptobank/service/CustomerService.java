package com.example.cryptobank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    //private DAO -> invoegen na push

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService() {
        logger.info("New CustomerService");
    }

}
