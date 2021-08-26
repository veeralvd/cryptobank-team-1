package com.example.cryptobank.domain;

import com.example.cryptobank.service.IbanGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankAccount {
    private final Logger logger = LoggerFactory.getLogger(BankAccount.class);
    private static long ibanStartingNumber = 5000000000L;
    private static final String IBAN_PREFIX_CRYPTOKNIGHTS = "NL69COKI";

    private String iban;
    private double balance;

    public BankAccount() {
        this.iban = IbanGenerator.generate();
        this.balance = BonusAmount.getAmountBonus();

        logger.info("New BankAccount created met iban:" + iban);
    }

    // TODO: 24/08/2021 betere iban maken
    private String generateIban() {
        ibanStartingNumber += 1;
        return new String(IBAN_PREFIX_CRYPTOKNIGHTS + ibanStartingNumber);
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