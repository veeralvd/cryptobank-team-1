package com.knight.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PortfolioDto {
    private String customerName;
    private List<OwnedAssetDto> list;
    private final Logger logger = LoggerFactory.getLogger(PortfolioDto.class);

    public PortfolioDto(String customerName, List<OwnedAssetDto> list) {
        this.customerName = customerName;
        this.list = list;
    }

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