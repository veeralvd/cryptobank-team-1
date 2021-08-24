package com.example.cryptobank.controller;

import com.example.cryptobank.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

    private BankAccountService bankAccountService;

    private final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
        logger.info("New BankAccountController");
    }

}