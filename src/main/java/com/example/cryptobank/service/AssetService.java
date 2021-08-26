package com.example.cryptobank.service;

import com.example.cryptobank.database.AssetDao;
import com.example.cryptobank.database.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
