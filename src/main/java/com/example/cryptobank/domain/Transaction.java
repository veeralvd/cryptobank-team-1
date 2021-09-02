package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Transaction {
    private final Logger logger = LoggerFactory.getLogger(Transaction.class);

    private int transactionNumber;
    private BankAccount buyerAccount;
    private BankAccount sellerAccount;
    private LocalDateTime localDateTime;
    private Asset asset;
    private double amount;
    private double sellingPrice;



    public Transaction(int transactionNumber, BankAccount buyerAccount, BankAccount sellerAccount, LocalDateTime localDateTime,
                       Asset asset, double amount, double sellingPrice) {
        this.transactionNumber = transactionNumber;
        this.buyerAccount = buyerAccount;
        this.sellerAccount = sellerAccount;
        this.localDateTime = localDateTime;
        this.asset = asset;
        this.amount = amount;
        this.sellingPrice = sellingPrice;
        logger.info("New Transaction");
    }

    public Transaction(BankAccount buyerAccount, BankAccount sellerAccount, LocalDateTime localDateTime, Asset asset,
                       double amount, double sellingPrice) {
        this(0, buyerAccount, sellerAccount, localDateTime, asset, amount, sellingPrice);
    }

    //TODO klopt equals method nog wel na aanpassen attributen?
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction transaction = (Transaction) o;

        if (Double.compare(transaction.amount, amount) != 0) return false;
        if (!localDateTime.equals(transaction.localDateTime)) return false;
        return asset.equals(transaction.asset);
    }

    //TODO klopt hashcode method nog wel na aanpassen attributen?
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


    public int getTransactionNumber() {
        return transactionNumber;
    }

    public BankAccount getBuyerAccount() {
        return buyerAccount;
    }

    public BankAccount getSellerAccount() {
        return sellerAccount;
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

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionNumber=" + transactionNumber +
                ", buyerAccount=" + buyerAccount +
                ", sellerAccount=" + sellerAccount +
                ", localDateTime=" + localDateTime +
                ", asset=" + asset +
                ", amount=" + amount +
                ", sellingPrice=" + sellingPrice +
                '}';
    }

} // end of class Transaction