package com.example.cryptobank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private final Logger logger = LoggerFactory.getLogger(Portfolio.class);
    private Map<Asset, Double> assetMap;

    public Portfolio() {
        this.assetMap = new HashMap<>();
        logger.info("New Portfolio");
    }

    public Map<Asset, Double> getAssetMap() {
        return assetMap;
    }

}
