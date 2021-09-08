package com.example.cryptobank.database;

import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Transaction;

import java.util.List;
import java.util.Map;

public interface PortfolioDao {

    Map<String, Double> getAssetmapByIban (String iban);

    double updateAssetAmountPositive(Transaction transaction);

    double updateAssetAmountNegative(Transaction transaction);

    List<String> getAbbreviationsByIban(String iban);
}
