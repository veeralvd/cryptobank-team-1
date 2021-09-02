package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Order;
import com.example.cryptobank.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        logger.info("New OrderController");
    }

    /**
     * Endpoint om kooporder voor asset te plaatsen. De koopknop moet het betreffende asset meegeven, dus evt
     * verplaatsen naar AssetController. Ook moet ervoor gezorgd worden dat Iban koper en Iban verkoper meegegeven
     * worden.
     * @return boolean status (voor nu, zie demo restfullwebserver voor ResponseEntity)
     */
    @PostMapping("/buyAsset")
    // public int buyAsset(@RequestParam String ibanBuyer, String ibanSeller, Asset asset, double amount) {
    public int buyAsset(@RequestBody Order order) {
        Order orderToSave = orderService.placeOrder(order);
        if (orderToSave.getBankAccount().getIban() == null) {
            return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST).getStatusCodeValue();
        } else {
            return new ResponseEntity<String>(HttpStatus.OK).getStatusCodeValue();
        }
    }


    @GetMapping("/orders") // /{iban}
    Order FindOrdersByIban(@RequestParam String iban) {
        return null;
    }

}