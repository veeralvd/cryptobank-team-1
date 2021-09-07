package com.example.cryptobank.database;

import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Transaction;

import java.util.Map;

public interface PortfolioDao {

    Map<String, Double> getAssetmapByIban (String iban);

    double updateAssetAmountPositive(double transactionAssetAmount, Customer customer, Transaction transaction);

    double updateAssetAmountNegative(double transactionAssetAmount, Customer customer, Transaction transaction);
}
