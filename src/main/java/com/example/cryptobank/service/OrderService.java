package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private RootRepository rootRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(RootRepository rootRepository) {
        logger.info("New OrderService");
    }

    /**
     * Methode placeOrder
     * @return boolean status (voor nu, straks order teruggeven)
     */
//    public Order placeOrder(Order orderPlaced) {
//        return rootRepository.placeOrder(orderPlaced);
//    }

    /**
     * TODO nu gaan we uit van prijs per eenheid
     * Hulpmethode om bedrag asset koerwwaarde * aantal te berekenen
     */
    private double calculateAssetCost(Asset asset, double amount) {
        return asset.getRate().getCryptoRate() * amount;
    }

    /**
     * TODO naar Transaction
     * Hulpmethode om transactiekosten te berekenen op basis van assetCost
     * Tijdelijk vast op 3%
     */
    private double calculateTransactionCost(double assetCost) {
        return assetCost * 0.03;
    }

}