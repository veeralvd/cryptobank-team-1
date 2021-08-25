package com.example.cryptobank.controller;

import com.example.cryptobank.service.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetController {

    private AssetService assetService;

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
        logger.info("New AssetController");
    }

}
