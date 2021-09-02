package com.example.cryptobank.database;

import com.example.cryptobank.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RootRepository {
    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);
    private AdminDAO adminDAO;
    private CustomerDAO customerDAO;
    private AssetDao assetDao;
    private BankAccountDao bankAccountDao;
    private CryptoCurrencyRateDAO cryptoCurrencyRateDAO;
    private PortfolioDao portfolioDao;

    @Autowired
    public RootRepository(AdminDAO adminDAO, AssetDao assetDao, CustomerDAO customerDAO, BankAccountDao bankAccountDao,
                          CryptoCurrencyRateDAO cryptoCurrencyRateDAO, PortfolioDao portfolioDao) {
        logger.info("New RootRepository");
        this.adminDAO = adminDAO;
        this.assetDao = assetDao;
        this.customerDAO = customerDAO;
        this.bankAccountDao = bankAccountDao;
        this.cryptoCurrencyRateDAO = cryptoCurrencyRateDAO;
        this.portfolioDao = portfolioDao;
    }

    public Admin findAdminByUsername(String username) {
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
        ArrayList<CryptoCurrencyRate> cryptoCurrencyRateList = cryptoCurrencyRateDAO.findByAbbreviation(abbreviation);
        CryptoCurrencyRate currentRate = cryptoCurrencyRateList.get(cryptoCurrencyRateList.size()-1);
        asset.setRate(currentRate);
        logger.info("currentRate " + currentRate);
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

    public double getBalanceByIban(String iban) {
        double balanceRetrieved = bankAccountDao.getBalanceByIban(iban);
        return balanceRetrieved;
    }

    public double deposit(String iban, double amount) {
        double balanceUpdated = bankAccountDao.deposit(iban, amount);
        return balanceUpdated;
    }

    public double withdraw(String iban, double amount) {
        double balanceUpdated = bankAccountDao.withdraw(iban, amount);
        return balanceUpdated;
    }

    public Customer save(Customer customer) {
        bankAccountDao.save(customer.getBankAccount());
        Customer customerToSave = customerDAO.save(customer);
        return customerToSave;
    }

    public Customer findCustomerByUsername(String username) {
        Customer customer = customerDAO.findByUsername(username);
        if(customer != null) {
            customer.getBankAccount().setBalance(getBalanceByIban(customer.getBankAccount().getIban()));
        }
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAll();
    }

    public Portfolio getPortfolioByIban(String iban){
        Map<String, Double> portfolioMap = portfolioDao.getAssetmapByIban(iban);
        Portfolio portfolio = new Portfolio();
        portfolio.setAssetMap(portfolioMap);
        return portfolio;
    }

    public String findAdminUsernameByToken(String token) {
        return adminDAO.findAdminUsernameByToken(token);
    }

    public String findCustomerUsernameByToken(String token) {
        return customerDAO.findCustomerUsernameByToken(token);
    }

    public void insertTokenByAdminUsername(String username, String token) {
        adminDAO.insertTokenByAdminUsername(username, token);
    }

    public void insertTokenByCustomerUsername(String username, String token) {
        customerDAO.insertTokenByCustomerUsername(username, token);
    }

    public Order placeOrder(Order orderPlaced) {
        return null;
    }
} // end of class RootRepository