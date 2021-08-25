package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private final Logger logger = LoggerFactory.getLogger(Portfolio.class);
    private Map<Asset, Double> assetMap;

    public Portfolio(Map<Asset, Double> assetMap) {
        this.assetMap = assetMap;
        logger.info("New Portfolio");
    }

    public Map<Asset, Double> getAssetMap() {
        return assetMap;
    }

    public void setAssetMap(Map<Asset, Double> assetMap) {
        this.assetMap = assetMap;
    }

// TODO @putmapping bij een toevoeging aan de portfolio van een specifieke klant
/*    public void testmethodAddToList(Asset asset, Double numOfAssets){
        List<Asset> testlist = new ArrayList<>();
        // als geen list, maak nieuwe List.
        // als list al bestaat, voeg toe aan bestaande list.
        asset = new Asset("sdf", "sodefo", "sdf", new CurrencyRate("sdf", 0.1, 1.0));

        testlist.set(1, asset); // index in list moet anders, misschien HashSet
        setAmountOfAssets(numOfAssets);
    }

    public void testRemoveAssetFromList(Asset asset, Purchase purchase){
        // gegeven Asset met bijbehorende records moet verwijderd worden uit de List.
    }*/



}
