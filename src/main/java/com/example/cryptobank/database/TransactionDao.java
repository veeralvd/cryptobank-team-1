package com.example.cryptobank.database;


import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Transaction;

import java.util.ArrayList;

/**
 * @author Sarah-Jayne Nogarede
 * Dit is de DAO interface voor het object 'Purchase', waarin wordt aangegeven welke methoden een
 * PurchaseDao moet implementeren.
 */
public interface TransactionDao {

    Transaction save (Transaction transaction);
    Transaction findByTransactionNumber (Integer transactionNumber);
    ArrayList<Transaction> getAllByCustomer (Customer customer);

}
