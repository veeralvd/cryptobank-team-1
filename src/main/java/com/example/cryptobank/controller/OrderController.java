package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Order;
import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.dto.OrderDto;
import com.example.cryptobank.dto.TransactionDto;
import com.example.cryptobank.service.CustomerService;
import com.example.cryptobank.service.OrderService;
import com.example.cryptobank.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;
    private CustomerService customerService;
    private TransactionService transactionService;

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService, TransactionService transactionService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.transactionService = transactionService;
        logger.info("New OrderController");
    }

    /**
     * Endpoint om kooporder voor asset te plaatsen. De koopknop moet het betreffende asset meegeven, dus evt
     * verplaatsen naar AssetController. Ook moet ervoor gezorgd worden dat Iban koper en Iban verkoper meegegeven
     * worden.
     */
    @PostMapping(value = "/buyasset", produces = "application/json")
    public ResponseEntity<?> buyAsset(@RequestParam String assetAbbr, @RequestParam double assetAmount, @RequestHeader("Authorization") String accessToken) {
        logger.info("/buyAsset aangeroepen");
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer == null) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        String iban = customer.getIban();
        OrderDto orderToSave = orderService.assembleOrderTemp(iban, assetAbbr, assetAmount);
        OrderDto orderSaved = orderService.saveOrder(orderToSave);
        if (orderSaved == null) {
            return new ResponseEntity<String>("Failed to save order", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<String>("Order saved", HttpStatus.CREATED);
        }
    }

    // Tijdelijk endpoint om buy now te testen
    @PostMapping(value = "buyassetnow", produces = "application/json")
    public ResponseEntity<?> buyAssetnow(@RequestParam String iban, String assetAbbr, double assetAmount) { //, @RequestHeader("Authorization") String accessToken
        logger.info("/buyassetnow aangeroepen");
       /* CustomerDto customer = customerService.authenticate(accessToken);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String iban = customer.getIban();*/
        OrderDto orderToSave = orderService.assembleOrderTemp(iban, assetAbbr, assetAmount);
        OrderDto orderSaved = orderService.saveOrder(orderToSave);
        TransactionDto transactionCompleted = transactionService.completeTransaction(orderSaved);
        if (transactionCompleted == null) {
            return new ResponseEntity<String>("Failed to complete transaction", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<String>("Transaction saved and completed", HttpStatus.CREATED);
        }
    }


    @GetMapping("/orders")
    public OrderDto findByOrderId(@RequestParam int orderId) {
        return orderService.findByOrderId(orderId);
    }

    @GetMapping("/orders/iban")
    public List<OrderDto> getAllOrdersByIban(@RequestParam String iban) {
        return orderService.getAllOrdersByIban(iban);
    }

    //TODO tijdelijke endpoints om postman te testen opruimen
    /*@PostMapping("/orders/save")
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        Order orderToSave = orderService.saveOrder(order);
        if (orderToSave == null) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(orderToSave.toString(), HttpStatus.OK);
        }
    }*/

} // end of class OrderController