package com.example.cryptobank.controller;

import com.example.cryptobank.database.AssetDao;
import com.example.cryptobank.database.PortfolioDao;
import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.dto.PortfolioDto;
import com.example.cryptobank.service.PortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PortfolioController {
    private final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

    private PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
        logger.info("New PortfolioController");
    }

    @GetMapping("/portfolio")
    public Portfolio getPortfolio (@RequestParam String iban){
        return portfolioService.getPortfolio(iban);
    }

    @GetMapping("/getdto")
    public PortfolioDto getDto (@RequestParam String token){
        return portfolioService.showPortfolioDto(token);
    }

}