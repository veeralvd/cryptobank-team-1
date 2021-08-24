package com.example.cryptobank.database;

import com.example.cryptobank.domain.BankAccount;

public interface BankAccountDao {

    BankAccount save(BankAccount bankAccount);
    BankAccount findByIban(String iban);

}
