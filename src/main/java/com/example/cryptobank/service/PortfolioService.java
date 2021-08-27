package com.example.cryptobank.service;

import com.example.cryptobank.database.AssetDao;
import com.example.cryptobank.database.PortfolioDao;
import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Portfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PortfolioService {
    private final Logger logger = LoggerFactory.getLogger(PortfolioService.class);
    private RootRepository rootRepository;


    public PortfolioService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New PortfolioService");
    }

    public Portfolio getPortfolio (String iban){
        Portfolio portfolio = rootRepository.getPortfolioByIban(iban);
        logger.info("PortServrice: TEST TEST: PUNT WORDT BEREIKT");
        System.out.println("PRINT PRINT PRINT" + portfolio.getAssetMap());
        return portfolio;
    }
}