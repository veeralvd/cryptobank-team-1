package com.knight.cryptobank.database;

import com.knight.cryptobank.domain.CryptoCurrencyRate;

import java.util.ArrayList;
import java.util.Map;

public interface CryptoCurrencyRateDAO {

    public CryptoCurrencyRate save(CryptoCurrencyRate cryptoCurrencyRate);
    public ArrayList<CryptoCurrencyRate> getAll();
    public ArrayList<CryptoCurrencyRate> findByAbbreviation(String abbreviation);
    public Map<String, Double> getAllCurrentRates();



}