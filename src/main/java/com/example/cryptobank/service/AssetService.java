package com.example.cryptobank.service;

import com.example.cryptobank.database.AssetDao;
import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Asset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Sarah-Jayne Nogarede
 * Dit is de service klasse voor het object 'Asset'. Deze klasse bevat de methoden...//TODO aanvullen
 */
@Service
public class AssetService {

    private RootRepository rootRepository;

    private final Logger logger = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    public AssetService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New AssetService");
    }

    public ArrayList<Asset> getAssets() {
        ArrayList<Asset> allAssets = rootRepository.getAll();
        return allAssets;
    }

    public Asset getByAbbreviation(String abbreviation) {
        Asset assetToShow = rootRepository.getByAbbreviation(abbreviation);
        return assetToShow;
    }

}
