package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrencyRate {
    private final Logger logger = LoggerFactory.getLogger(CurrencyRate.class);
    private final static double BANK_FEE = 0.15; //TODO moeten in Transaction komen
    private final static double SPLIT_FEE = 0.05; //TODO moeten in Transaction komen
    private double buyRate;
    private double sellRate;
    private String abbreviation;

    public CurrencyRate(String abbreviation, double buyRate, double sellRate) {
        this.abbreviation = abbreviation;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        logger.info("New CurrencyRate");
    }

    public static double getBankFee() {
        return BANK_FEE;
    }

    public static double getSplitFee() {
        return SPLIT_FEE;
    }

    public double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(double buyRate) {
        this.buyRate = buyRate;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
