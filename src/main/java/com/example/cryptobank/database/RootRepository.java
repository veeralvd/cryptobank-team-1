package com.example.cryptobank.database;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.Purchase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RootRepository {
    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);
    private AdminDAO adminDAO;
    private CustomerDAO customerDAO;
    private AssetDao assetDao;
    private PortfolioDao portfolioDao;

    @Autowired
    public RootRepository(AdminDAO adminDAO, AssetDao assetDao, CustomerDAO customerDAO, PortfolioDao portfolioDao) {
        logger.info("New RootRepository");
        this.adminDAO = adminDAO;
        this.assetDao = assetDao;
        this.customerDAO = customerDAO;
        this.portfolioDao = portfolioDao;
    }

    public Admin getAdminByUsername(String username) {
        Admin admin = adminDAO.findByUsername(username);
        return admin;
    }

    public Admin save(Admin admin) {
        return adminDAO.save(admin);
    }

    public Asset save(Asset asset) {
        return assetDao.save(asset);
    }

    public Asset getByAbbreviation(String abbreviation) {
        Asset asset = assetDao.findByAbbreviation(abbreviation);
        return asset;
    }

    public Asset getByName(String name) {
        Asset asset = assetDao.findByName(name);
        return asset;
    }

    public ArrayList<Asset> getAll() {
        ArrayList allAssets = assetDao.getAll();
        return allAssets;
    }

    public Portfolio getPortfolioByIban(String iban){
        Map<String, Double> daoMap = portfolioDao.getAssetmapByIban(iban);
        // test
        System.out.println("ROOTREPO: " + daoMap);
        Map<Asset, Double> assetMap = new HashMap<>();
        for (Map.Entry<String, Double> entry : daoMap.entrySet()){
            String abbreviation = entry.getKey();
            double amount = entry.getValue();

            Asset asset = assetDao.findByAbbreviation(abbreviation);
            assetMap.put(asset, amount);
        }
        Portfolio portfolio = new Portfolio();
        portfolio.setAssetMap(assetMap);
        return portfolio;
    }

} // end of class RootRepository