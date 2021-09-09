package com.example.cryptobank.controller;

import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.service.CustomerService;
import com.example.cryptobank.service.PortfolioService;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.dto.PortfolioDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {
    private final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

    private PortfolioService portfolioService;
    private CustomerService customerService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService, CustomerService customerService) {
        this.portfolioService = portfolioService;
        this.customerService = customerService;
        logger.info("New PortfolioController");
    }

    @GetMapping("/portfolio")
    public Portfolio getPortfolio (@RequestParam String iban){
        return portfolioService.getPortfolio(iban);
    }


    @GetMapping("/getdto")
    public ResponseEntity<?> getDto (@RequestHeader("Authorization") String accessToken){
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer != null){
            PortfolioDto portfolioDto = portfolioService.showPortfolioDto(customer.getIban(), customer.getFirstName());
            return new ResponseEntity<String>(portfolioDto.toString(), HttpStatus.OK);
        } else{
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/getportfoliovalue")
    public double getPortfolioValue (@RequestParam String iban){
        return portfolioService.getTotalValuePortfolio(iban);
    }
}