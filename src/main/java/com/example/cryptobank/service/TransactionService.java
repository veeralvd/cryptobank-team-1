package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    private RootRepository rootRepository;

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository) {
        logger.info("New TransactionService");
    }

    /**
     * Methode placeOrder
     * @return boolean status (voor nu, straks order teruggeven)
     */
    public Transaction placeOrder(Transaction orderPlaced) {
        double assetCost = calculateAssetCost(orderPlaced.getAsset(), orderPlaced.getAmount());
        orderPlaced.setSellingPrice(assetCost);

        return rootRepository.placeOrder(orderPlaced);
    }

    /**
     * Hulpmethode om bedrag asset koerwwaarde * aantal te berekenen
     */
    private double calculateAssetCost(Asset asset, double amount) {
        return asset.getRate().getCryptoRate() * amount;
    }

    /**
     * Hulpmethode om transactiekosten te berekenen op basis van assetCost
     * Tijdelijk vast op 3%
     */
    private double calculateTransactionCost(double assetCost) {
        return assetCost * 0.03;
    }

}