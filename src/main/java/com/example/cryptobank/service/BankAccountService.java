package com.example.cryptobank.service;

import com.example.cryptobank.database.BankAccountDao;
import com.example.cryptobank.database.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    private BankAccountDao bankAccountDao;
    private RootRepository rootRepository;

    private final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    @Autowired
    public BankAccountService(RootRepository rootRepository) {
        //this.bankAccountDao = bankAccountDao;
        this.rootRepository = rootRepository;
        logger.info("New BankAccountService");
    }

    // TODO implementeren
    public double getBalanceByIban(String iban) {

        return 0.0;
    }

}