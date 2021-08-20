package com.example.cryptobank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Asset {
    private final Logger logger = LoggerFactory.getLogger(Asset.class);

    private String abbreviation;
    private String name;
    private String description;
    private CurrencyRate rate;

    public Asset(String abbreviation, String name, String description, CurrencyRate rate) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.description = description;
        this.rate = rate;
        logger.info("New Asset");
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
