package com.example.cryptobank.database;

import com.example.cryptobank.domain.Transaction;

import java.util.ArrayList;

public interface TransactionDao {

    Transaction save(Transaction transaction);
    Transaction findByTransactionId(int transactionId);
    //ArrayList<Transaction> getAllTransactionsByIban(String iban);

}
