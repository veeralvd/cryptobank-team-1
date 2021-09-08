package com.example.cryptobank.database;

import java.util.Map;

public interface PortfolioDao {

    public Map<String, Double> getAssetmapByIban (String iban);
}
