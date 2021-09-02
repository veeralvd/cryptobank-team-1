package com.example.cryptobank.service;

import com.example.cryptobank.database.JdbcPortfolioDao;
import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.dto.OwnedAssetDto;
import com.example.cryptobank.dto.PortfolioDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        return rootRepository.getPortfolioByIban(iban);
    }

    public PortfolioDto showPortfolioDto (String token){

        String username = rootRepository.findCustomerUsernameByToken(token);
        Customer customer = rootRepository.findCustomerByUsername(username);

        Map<String, Double> assetMap = rootRepository.getPortfolioByIban(customer.getBankAccount().getIban()).getAssetMap();

        List<OwnedAssetDto> lijst = new ArrayList<>();
        for(Map.Entry<String, Double> entry: assetMap.entrySet()) {
            String abbreviation = entry.getKey();
            double ownedAssetAmount = entry.getValue();
            String assetName = rootRepository.getByAbbreviation(abbreviation).getName();
            double currentSinglePrice = rootRepository.getByAbbreviation(abbreviation).getRate().getCryptoRate();
            double subTotal = ownedAssetAmount*currentSinglePrice;
            lijst.add(new OwnedAssetDto(abbreviation, ownedAssetAmount, assetName, currentSinglePrice, subTotal));
        }
        return new PortfolioDto(customer.getFirstName(), lijst);
    }
}