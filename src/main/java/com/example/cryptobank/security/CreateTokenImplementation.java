package com.example.cryptobank.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class CreateTokenImplementation implements CreateToken{
    private final Logger logger = LoggerFactory.getLogger(CreateTokenImplementation.class);
   // private final static String BEARER = "Bearer=";


    public CreateTokenImplementation() {
        logger.info("New Token creation requested");
    }

    // TODO: 13/09/2021 code smell @ line 26 and 36, magic numbers 
    @Override
    public String createAccessToken(String username, String key) {
        Algorithm algorithm = Algorithm.HMAC256(key.getBytes());
         String access_token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .sign(algorithm);
         return access_token;
    }

    @Override
    public String createRefreshToken(String username, String key) {
        Algorithm algorithm = Algorithm.HMAC256(key.getBytes());
        String refresh_token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .sign(algorithm);
        return refresh_token;
    }
}