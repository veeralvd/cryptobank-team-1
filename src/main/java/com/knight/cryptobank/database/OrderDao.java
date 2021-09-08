package com.knight.cryptobank.database;


import com.knight.cryptobank.domain.Order;

import java.util.ArrayList;

/**
 * @author Sarah-Jayne Nogarede
 * Dit is de DAO interface voor het object 'Order', waarin wordt aangegeven welke methoden een
 * OrderDao moet implementeren.
 */
public interface OrderDao {

    Order save (Order order);
    Order findByOrderId (int orderId);
    ArrayList<Order> getAllByIban (String iban);
    String getAssetAbbrFromOrderId(int orderId);
    String getIbanFromOrderId(int orderId);

}
