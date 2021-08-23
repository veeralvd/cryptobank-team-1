package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Purchase {
    private final Logger logger = LoggerFactory.getLogger(Purchase.class);

    private LocalDateTime localDateTime;
    private Asset asset;
    private int amount;

    public Purchase(LocalDateTime localDateTime, Asset asset, int amount) {
        this.localDateTime = localDateTime;
        this.asset = asset;
        this.amount = amount;
        logger.info("New Purchase");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (amount != purchase.amount) return false;
        if (!localDateTime.equals(purchase.localDateTime)) return false;
        return asset.equals(purchase.asset);
    }

    @Override
    public int hashCode() {
        int result = localDateTime.hashCode();
        result = 31 * result + asset.hashCode();
        result = 31 * result + amount;
        return result;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Asset getAsset() {
        return asset;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "localDateTime=" + localDateTime +
                ", asset=" + asset +
                ", amount=" + amount +
                '}';
    }
}