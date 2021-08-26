package com.example.cryptobank.database;

import com.example.cryptobank.domain.BankAccount;

public interface BankAccountDao {

    //BankAccount save(BankAccount bankAccount);
    double getBalanceByIban(String iban);
    BankAccount withdraw(String iban, double amount, String description);
    BankAccount deposit(String iban, double amount, String description);
    boolean checkBankAccount(String iban, double amount);

}
