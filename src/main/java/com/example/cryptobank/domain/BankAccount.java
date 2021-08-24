package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankAccount {
    private final Logger logger = LoggerFactory.getLogger(BankAccount.class);
    private static long ibanStartingNumber = 5000000000L;
    private static final String IBAN_PREFIX_CRYPTOKNIGHTS = "NL69COKI";

    private String iban;
    private double balance;

    //Ik heb hier een bonusamount klasse voor aangemaakt omdat een admin het startbedrag wat klanten krijgen moet kunnen aanpassen.
    //We moeten natuurlijk onze nieuwe klanten wel met een goede reclame van 500euro startkapitaal oid kunnen lokken
    public BankAccount() {
        this.iban = generateIban();
        this.balance = BonusAmount.getAmountBonus();

        logger.info("New BankAccount created met iban:" + iban);
    }

    // TODO: 23/08/2021 Vragen aan Huub: wat betekent de hint die intellij hier geeft? een 'contract notnull'
    private String generateIban() {
        ibanStartingNumber += 3;
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