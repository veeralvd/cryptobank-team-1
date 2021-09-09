package com.example.cryptobank.domain;

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

    public BankAccount(String iban, double balance) {
        this.iban = iban;
        this.balance = balance;
        logger.info("New BankAccount all-args");
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

    @Override
    public String toString() {
        return "BankAccount{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                '}';
    }
}