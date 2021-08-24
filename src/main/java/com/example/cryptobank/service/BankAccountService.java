package com.example.cryptobank.service;

import com.example.cryptobank.database.BankAccountDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    private BankAccountDao bankAccountDao;

    private final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    @Autowired
    public BankAccountService(BankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
        logger.info("New BankAccountService");
    }

}