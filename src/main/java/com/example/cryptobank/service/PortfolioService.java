package com.example.cryptobank.service;

import com.example.cryptobank.database.AssetDao;
import com.example.cryptobank.database.JdbcPortfolioDao;
import com.example.cryptobank.database.PortfolioDao;
import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.dto.OADto;
import com.example.cryptobank.dto.PortfolioDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioService {
    private final Logger logger = LoggerFactory.getLogger(PortfolioService.class);
    private RootRepository rootRepository;
    private JdbcPortfolioDao jdbcPortfolioDao;


    public PortfolioService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New PortfolioService");
    }

    public Portfolio getPortfolio (String iban){
        return rootRepository.getPortfolioByIban(iban);
    }

    public PortfolioDto showPortfolioDto (String token){
//        private String customerName;
//        private String assetAbbreviation;
//        private String assetName;
//        private double assetPrice;
//        private LocalDateTime purchaseDate;
//        private double ownedAssetAmount;
//        private double purchaseCost;
//        private double currentValue;

        String username = rootRepository.findCustomerUsernameByToken(token);
        System.out.println("USERNAME IS: " + username);
        Customer klant = rootRepository.findCustomerByUsername(username);
        String customerName = klant.getFirstName();

        String iban = klant.getBankAccount().getIban();
        Map<String, Double> assetMap = klant.getPortfolio().getAssetMap();
        // pak afkorting. -> trek asset eruit obv afkorting -> trek assetPrice uit asset
        // Amount haal je uit assetMap (value)
        List<OADto> lijst = new ArrayList<>();
        for(Map.Entry<String, Double> entry: assetMap.entrySet()) {
            String abbreviation = entry.getKey();
            double ownedAssetAmount = entry.getValue();
            String assetName = rootRepository.getByAbbreviation(abbreviation).getName();
            double currentSinglePrice = rootRepository.getByAbbreviation(abbreviation).getRate().getCryptoRate();
            double subTotal = ownedAssetAmount*currentSinglePrice;
            lijst.add(new OADto(abbreviation, ownedAssetAmount, assetName, currentSinglePrice, subTotal));
        }
        System.out.println(lijst); // TEST TEST
        return new PortfolioDto(customerName, lijst);
    }
}