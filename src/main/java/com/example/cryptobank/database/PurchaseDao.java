package com.example.cryptobank.database;


import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Purchase;

import java.util.ArrayList;

/**
 * @author Sarah-Jayne Nogarede
 * Dit is de DAO interface voor het object 'Purchase', waarin wordt aangegeven welke methoden een
 * PurchaseDao moet implementeren.
 */
public interface PurchaseDao {

    Purchase save (Purchase purchase);
    Purchase findByTransactionNumber (Integer transactionNumber);
    ArrayList<Purchase> getAllByCustomer (Customer customer);

}
