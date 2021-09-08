package com.knight.cryptobank.database;

import com.knight.cryptobank.domain.Transaction;

public interface TransactionDao {

    Transaction save(Transaction transaction);
    Transaction findByTransactionId(int transactionId);
    //ArrayList<Transaction> getAllTransactionsByIban(String iban);

    String findAssetForTransaction(int transactionId);

    String findBuyerAccountForTransaction(int transactionId);

    String findSellerAccountForTransaction(int transactionId);
}
