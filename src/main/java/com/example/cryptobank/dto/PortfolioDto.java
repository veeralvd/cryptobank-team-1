package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PortfolioDto {
    private String customerName;
    private List<OwnedAssetDto> list;

//    private String assetAbbreviation;
//    private String assetName;
//    private double assetPrice;

//    private LocalDateTime purchaseDate;
//    private double ownedAssetAmount;
//    private double purchaseCost;
//    private double currentValue;

    private final Logger logger = LoggerFactory.getLogger(PortfolioDto.class);

    public PortfolioDto(String customerName, List<OwnedAssetDto> list) {
        this.customerName = customerName;
        this.list = list;
    }

    /*    public PortfolioDto(String customerName, String assetAbbreviation, String assetName, double assetPrice,
                        LocalDateTime purchaseDate, double ownedAssetAmount, double purchaseCost, double currentValue) {
        this.customerName = customerName;
        this.assetAbbreviation = assetAbbreviation;
        this.assetName = assetName;
        this.assetPrice = assetPrice;
        this.purchaseDate = purchaseDate;
        this.ownedAssetAmount = ownedAssetAmount;
        this.purchaseCost = purchaseCost;
        this.currentValue = currentValue;
        logger.info("New PortfolioDto");
    }*/

    public PortfolioDto() {
        logger.info("New PortfolioDto");
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OwnedAssetDto> getList() {
        return list;
    }

    public void setList(List<OwnedAssetDto> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PortfolioDto{" +
                "customerName='" + customerName + '\'' +
                ", list=" + list +
                '}';
    }
}