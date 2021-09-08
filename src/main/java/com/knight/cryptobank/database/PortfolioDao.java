package com.knight.cryptobank.database;

import java.util.Map;

public interface PortfolioDao {

    public Map<String, Double> getAssetmapByIban (String iban);
}
