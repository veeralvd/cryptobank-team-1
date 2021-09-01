package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Purchase {
    private final Logger logger = LoggerFactory.getLogger(Purchase.class);

    private Customer customer;
    private LocalDateTime localDateTime;
    private Asset asset;
    private double amount;
    private Integer transactionNumber;

    public Purchase(Customer customer, LocalDateTime localDateTime, Asset asset, double amount, int transactionNumber) {
        this.customer = customer;
        this.localDateTime = localDateTime;
        this.asset = asset;
        this.amount = amount;
        this.transactionNumber = transactionNumber;
        logger.info("New Purchase");
    }

    public Purchase(Customer customer, LocalDateTime localDateTime, Asset asset, double amount) {
        this(customer, localDateTime, asset, amount, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (Double.compare(purchase.amount, amount) != 0) return false;
        if (!localDateTime.equals(purchase.localDateTime)) return false;
        return asset.equals(purchase.asset);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = localDateTime.hashCode();
        result = 31 * result + asset.hashCode();
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Asset getAsset() {
        return asset;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "customer=" + customer +
                ", localDateTime=" + localDateTime +
                ", asset=" + asset +
                ", amount=" + amount +
                '}';
    }
}