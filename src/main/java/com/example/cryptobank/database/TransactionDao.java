package com.example.cryptobank.database;


import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Transaction;

import java.util.ArrayList;

/**
 * @author Sarah-Jayne Nogarede
 * Dit is de DAO interface voor het object 'Transaction', waarin wordt aangegeven welke methoden een
 * TransactionDao moet implementeren.
 */
public interface TransactionDao {

    Transaction save (Transaction transaction);
    Transaction findByTransactionNumber (int transactionNumber);
    ArrayList<Transaction> getAllByIban (String iban);

}
