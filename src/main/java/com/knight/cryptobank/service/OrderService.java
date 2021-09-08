package com.knight.cryptobank.service;

import com.knight.cryptobank.database.RootRepository;
import com.knight.cryptobank.domain.Asset;
import com.knight.cryptobank.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {

    private RootRepository rootRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New OrderService");
    }

    /**
     * Methode placeOrder
     * @return boolean status (voor nu, straks order teruggeven) -> kan je niet gewoon de save methode gebruiken? Ik heb het alvast even gedaan, je oude boolean is uitgecomment.
     */

    public Order placeOrder(Order orderPlaced) {
        return rootRepository.placeOrder(orderPlaced);
    }

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

    public Order findByOrderId(int orderId) {
        return rootRepository.findByOrderId(orderId);
    }

    public ArrayList<Order> getAllByIban(String iban) {
        return rootRepository.getAllByIban(iban);
    }
}