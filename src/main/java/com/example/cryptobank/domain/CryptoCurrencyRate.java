package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class CryptoCurrencyRate {

    private final Logger logger = LoggerFactory.getLogger(CryptoCurrencyRate.class);
    private double cryptoRate;
    private LocalDateTime dateTime;

    public CryptoCurrencyRate(double cryptoRate, LocalDateTime dateTime) {
        this.cryptoRate = cryptoRate;
        this.dateTime = dateTime;
    }

    public CryptoCurrencyRate(double cryptoRate) {
        this(cryptoRate, LocalDateTime.now());
    }

    public double getCryptoRate() {
        return cryptoRate;
    }

    public void setCryptoRate(double cryptoRate) {
        this.cryptoRate = cryptoRate;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
