package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OADto {
    private final Logger logger = LoggerFactory.getLogger(OADto.class);

    String abbreviation;
    double ownedAssetAmount;
    String assetName;
    double currentSinglePrice;
    double subTotal;

    public OADto(String abbreviation, double ownedAssetAmount, String assetName,
                 double currentSinglePrice, double subTotal) {
        this.abbreviation = abbreviation;
        this.ownedAssetAmount = ownedAssetAmount;
        this.assetName = assetName;
        this.currentSinglePrice = currentSinglePrice;
        this.subTotal = subTotal;
        logger.info("New OADto");
    }

    @Override
    public String toString() {
        return "OADto{" +
                "abbreviation='" + abbreviation + '\'' +
                ", ownedAssetAmount=" + ownedAssetAmount +
                ", assetName='" + assetName + '\'' +
                ", currentSinglePrice=" + currentSinglePrice +
                ", subTotal=" + subTotal +
                '}';
    }
}