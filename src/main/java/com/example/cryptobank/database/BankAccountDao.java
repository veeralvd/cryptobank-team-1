package com.example.cryptobank.database;

import com.example.cryptobank.domain.BankAccount;

public interface BankAccountDao {

    //BankAccount save(BankAccount bankAccount);
    double getBalanceByIban(String iban);
    double withdraw(String iban, double amount);
    double deposit(String iban, double amount);
    boolean checkBankAccount(String iban, double amount);
    //void deleteBankAccount(String iban);

}
