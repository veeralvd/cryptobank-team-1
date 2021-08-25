package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    // Controllerklasse voor @GetMapping en @PutMapping om met de webkant te praten
    // Admin kan bedragen op rekeningen wijzigen (via webapplicatie)
    // Use case: Admin kan geld storten op/opnemen van willekeurige rekening

    // Klant kan eigen rekening inzien, geld storten, ermee betalen

    @GetMapping("/bankaccount")
    public String getTestBankAccount(@RequestParam String iban) {
        String response = "BankAccount met IBAN: " + iban;
        return response;
    }


}