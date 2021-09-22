package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class CoinDto {
    public String id;
    public String symbol;
    public String name;
    public String image;
    public double current_price;
    public long market_cap;
    public long market_cap_rank;
    public Long fully_diluted_valuation;
    public long total_volume;
    public double high_24h;
    public double low_24h;
    public double price_change_24h;
    public double price_change_percentage_24h;
    public long market_cap_change_24h;
    public double market_cap_change_percentage_24h;
    public double circulating_supply;
    public double total_supply;
    public Long max_supply;
    public double ath;
    public double ath_change_percentage;
    public Date ath_date;
    public double atl;
    public double atl_change_percentage;
    public Date atl_date;
    public Roi roi;
    public Date last_updated;
    private final Logger logger = LoggerFactory.getLogger(CoinDto.class);
    
    public CoinDto() {

    }

    public CoinDto(String id, String symbol, String name, String image, double current_price, long market_cap, long market_cap_rank,
                   Long fully_diluted_valuation, long total_volume, double high_24h, double low_24h, double price_change_24h,
                   double price_change_percentage_24h, long market_cap_change_24h, double market_cap_change_percentage_24h,
                   double circulating_supply, long total_supply, Long max_supply, double ath, double ath_change_percentage,
                   Date ath_date, double atl, double atl_change_percentage, Date atl_date, Roi roi, Date last_updated) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.image = image;
        this.current_price = current_price;
        this.market_cap = market_cap;
        this.market_cap_rank = market_cap_rank;
        this.fully_diluted_valuation = fully_diluted_valuation;
        this.total_volume = total_volume;
        this.high_24h = high_24h;
        this.low_24h = low_24h;
        this.price_change_24h = price_change_24h;
        this.price_change_percentage_24h = price_change_percentage_24h;
        this.market_cap_change_24h = market_cap_change_24h;
        this.market_cap_change_percentage_24h = market_cap_change_percentage_24h;
        this.circulating_supply = circulating_supply;
        this.total_supply = total_supply;
        this.max_supply = max_supply;
        this.ath = ath;
        this.ath_change_percentage = ath_change_percentage;
        this.ath_date = ath_date;
        this.atl = atl;
        this.atl_change_percentage = atl_change_percentage;
        this.atl_date = atl_date;
        this.roi = roi;
        this.last_updated = last_updated;
        logger.info("New CoinDto");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(double current_price) {
        this.current_price = current_price;
    }

    public long getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(long market_cap) {
        this.market_cap = market_cap;
    }

    public long getMarket_cap_rank() {
        return market_cap_rank;
    }

    public void setMarket_cap_rank(long market_cap_rank) {
        this.market_cap_rank = market_cap_rank;
    }

    public Long getFully_diluted_valuation() {
        return fully_diluted_valuation;
    }

    public void setFully_diluted_valuation(Long fully_diluted_valuation) {
        this.fully_diluted_valuation = fully_diluted_valuation;
    }

    public long getTotal_volume() {
        return total_volume;
    }

    public void setTotal_volume(long total_volume) {
        this.total_volume = total_volume;
    }

    public double getHigh_24h() {
        return high_24h;
    }

    public void setHigh_24h(double high_24h) {
        this.high_24h = high_24h;
    }

    public double getLow_24h() {
        return low_24h;
    }

    public void setLow_24h(double low_24h) {
        this.low_24h = low_24h;
    }

    public double getPrice_change_24h() {
        return price_change_24h;
    }

    public void setPrice_change_24h(double price_change_24h) {
        this.price_change_24h = price_change_24h;
    }

    public double getPrice_change_percentage_24h() {
        return price_change_percentage_24h;
    }

    public void setPrice_change_percentage_24h(double price_change_percentage_24h) {
        this.price_change_percentage_24h = price_change_percentage_24h;
    }

    public long getMarket_cap_change_24h() {
        return market_cap_change_24h;
    }

    public void setMarket_cap_change_24h(long market_cap_change_24h) {
        this.market_cap_change_24h = market_cap_change_24h;
    }

    public double getMarket_cap_change_percentage_24h() {
        return market_cap_change_percentage_24h;
    }

    public void setMarket_cap_change_percentage_24h(double market_cap_change_percentage_24h) {
        this.market_cap_change_percentage_24h = market_cap_change_percentage_24h;
    }

    public double getCirculating_supply() {
        return circulating_supply;
    }

    public void setCirculating_supply(double circulating_supply) {
        this.circulating_supply = circulating_supply;
    }

    public double getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(double total_supply) {
        this.total_supply = total_supply;
    }

    public Long getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(Long max_supply) {
        this.max_supply = max_supply;
    }

    public double getAth() {
        return ath;
    }

    public void setAth(double ath) {
        this.ath = ath;
    }

    public double getAth_change_percentage() {
        return ath_change_percentage;
    }

    public void setAth_change_percentage(double ath_change_percentage) {
        this.ath_change_percentage = ath_change_percentage;
    }

    public Date getAth_date() {
        return ath_date;
    }

    public void setAth_date(Date ath_date) {
        this.ath_date = ath_date;
    }

    public double getAtl() {
        return atl;
    }

    public void setAtl(double atl) {
        this.atl = atl;
    }

    public double getAtl_change_percentage() {
        return atl_change_percentage;
    }

    public void setAtl_change_percentage(double atl_change_percentage) {
        this.atl_change_percentage = atl_change_percentage;
    }

    public Date getAtl_date() {
        return atl_date;
    }

    public void setAtl_date(Date atl_date) {
        this.atl_date = atl_date;
    }

    public Roi getRoi() {
        return roi;
    }

    public void setRoi(Roi roi) {
        this.roi = roi;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    @Override
    public String toString() {
        return "CoinDto{" +
                "id='" + id + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", current_price=" + current_price +
                ", market_cap=" + market_cap +
                ", market_cap_rank=" + market_cap_rank +
                ", fully_diluted_valuation=" + fully_diluted_valuation +
                ", total_volume=" + total_volume +
                ", high_24h=" + high_24h +
                ", low_24h=" + low_24h +
                ", price_change_24h=" + price_change_24h +
                ", price_change_percentage_24h=" + price_change_percentage_24h +
                ", market_cap_change_24h=" + market_cap_change_24h +
                ", market_cap_change_percentage_24h=" + market_cap_change_percentage_24h +
                ", circulating_supply=" + circulating_supply +
                ", total_supply=" + total_supply +
                ", max_supply=" + max_supply +
                ", ath=" + ath +
                ", ath_change_percentage=" + ath_change_percentage +
                ", ath_date=" + ath_date +
                ", atl=" + atl +
                ", atl_change_percentage=" + atl_change_percentage +
                ", atl_date=" + atl_date +
                ", roi=" + roi +
                ", last_updated=" + last_updated +
                '}';
    }
}
