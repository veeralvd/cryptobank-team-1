package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.CryptoCurrencyRate;
import com.example.cryptobank.dto.CoinDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleBiFunction;

@Configuration
@EnableScheduling
@Service
public class CoinGeckoUpdateService implements CoinApiUpdate {
    private RootRepository rootRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private List<CoinDto> newRates = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(CoinGeckoUpdateService.class);
    private static URL URL;

    static {
        try {
            URL = new URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&category=cryptocurrency&order=market_cap_desc&per_page=250&page=1&sparkline=false");
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }

    public CoinGeckoUpdateService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New CurrencyUpdateService");
        //startUpdate();
    }

    @Override
    @Scheduled(fixedRate = 3600000)
    public void updateRates() {
        newRates = getRates();

        Map<String, Double> currentRates = rootRepository.getAllCurrentRates();
        for(Map.Entry<String, Double> entry: currentRates.entrySet()) {
            String abbreviation = entry.getKey().toLowerCase();
            double currentRate = entry.getValue();
            //double newRate = checkActualRate(abbreviation);
            for (CoinDto coin: newRates) {
                if (coin.symbol.equals(abbreviation)) {
                    rootRepository.saveTransaction(new CryptoCurrencyRate(abbreviation.toUpperCase(),coin.current_price,
                            LocalDateTime.ofInstant(coin.last_updated.toInstant(), ZoneId.systemDefault())));
                    break;
                }
            }

        }
    }

    public ArrayList<CoinDto> getRates(){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<CoinDto> list = new ArrayList<>();
        try {
            CoinDto[] result = mapper.readValue(URL,CoinDto[].class);
            for (int i = 0; i < result.length; i++) {
                list.add(result[i]);
            }

        } catch (IOException error) {
            logger.info(error.getMessage());
        }
        return list;
    }



    private double checkActualRate(String abbreviation) {


        return 0;
    }

    // TODO: 19/09/2021 methode verwijderen en overige code + comments opschonen 
    public void startUpdate() {
        logger.info("startUpdate started");
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                updateRates();
            }
        }, 0, 2, TimeUnit.MINUTES);
        service.shutdown();
        logger.info("startUpdate finished");
    }

}
