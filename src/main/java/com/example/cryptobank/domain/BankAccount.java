package com.example.cryptobank.domain;

import com.example.cryptobank.service.IbanGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankAccount {
    private final Logger logger = LoggerFactory.getLogger(BankAccount.class);

    private String iban;
    private double balance;

    public BankAccount() {
        logger.info("empty bankaccount aangemaakt");
    }

    public BankAccount(String iban) {
        this.iban = iban;
        this.balance = BonusAmount.getAmountBonus();
        logger.info("New BankAccount created met iban:" + iban);
    }


    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}