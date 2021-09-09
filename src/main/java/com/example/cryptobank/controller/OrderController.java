package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Order;
import com.example.cryptobank.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    @PostMapping("/buyasset")
    // public int buyAsset(@RequestParam String ibanBuyer, String ibanSeller, Asset asset, double amount) {
    public int buyAsset(@RequestBody Order order) {
        Order orderToSave = orderService.placeOrder(order);
        if (orderToSave.getBankAccount().getIban() == null) {
            return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST).getStatusCodeValue();
        } else {
            return new ResponseEntity<String>(HttpStatus.OK).getStatusCodeValue();
        }
    }

    // TODO geeft voor asset nog steeds lege rate, en geheel leeg bankAccount
    @GetMapping("/orders")
    public Order findByOrderId(@RequestParam int orderId) {
        return orderService.findByOrderId(orderId);
    }

   /* @GetMapping("/orders/{orderid}")
    public Order findByOrderId(@PathVariable("orderid") int orderId) {
        return orderService.findByOrderId(orderId);
    }*/

    @GetMapping("/orders/iban")
    public ArrayList<Order> getAllByIban(@RequestParam String iban) {
        return orderService.getAllByIban(iban);
    }

    @PostMapping("/orders/save")
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        Order orderToSave = orderService.placeOrder(order);
        if (orderToSave == null) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(orderToSave.toString(), HttpStatus.OK);
        }
    }

} // end of class OrderController