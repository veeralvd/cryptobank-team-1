package com.example.cryptobank.database;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.CryptoCurrencyRate;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JdbcAssetDaoTest {

    private AssetDao assetDaoUnderTest;
    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDaoTest.class);

    @Autowired
    public JdbcAssetDaoTest(AssetDao assetDao) {
        this.assetDaoUnderTest = assetDao;
        logger.info("New JdbcAssetDaoTest");
    }

    @Test
    public void AssetDaoNotNull() {
        assertThat(assetDaoUnderTest).isNotNull();
    }

    @Test
    void saveAssetTest() {
        CryptoCurrencyRate rateSaarCoin = new CryptoCurrencyRate("SAAR", 13585, LocalDateTime.now());
        Asset saarCoin = new Asset("SAAR","saarCoin", "Keiharde Cryptonite", rateSaarCoin);
        Asset actual = assetDaoUnderTest.save(saarCoin);
        assertThat(actual).isEqualTo(saarCoin);
    }

    @Test
    void findByAbbreviation() {
    }

    @Test
    void findByName() {

    }

    @Test
    void getAll() {
    }
}