package com.example.cryptobank.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.security.CreateToken;
import com.example.cryptobank.security.TokenKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private RootRepository rootRepository;
    private HashHelper hashHelper;
    private CreateToken createToken;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final static String BEARER = "Bearer:";

    @Autowired
    public AuthenticationService(RootRepository rootRepository, HashHelper hashHelper, CreateToken createToken) {
        this.rootRepository = rootRepository;
        this.hashHelper = hashHelper;
        this.createToken = createToken;
        logger.info("New AuthenticationService");
    }


    public Admin authenticateAdminToken(String accessToken) {
        if (accessToken.startsWith(BEARER)) {
            try {
                String token = accessToken.substring(BEARER.length());
                Algorithm algorithm = Algorithm.HMAC256(TokenKeyService.getAdminKey().getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                Admin admin = rootRepository.findAdminByUsername(decodedJWT.getSubject());
                return admin;
            } catch (Exception exception) {
                logger.info(exception.getMessage());
                return null;
            }
        } else
            return null;
    }

    // TODO: 08/09/2021 Afmaken die hap
    public CustomerDto authenticateCustomerToken(String accessToken) {
        if (accessToken.startsWith(BEARER)) {
            try {
                String token = accessToken.substring(BEARER.length());
                Algorithm algorithm = Algorithm.HMAC256(TokenKeyService.getCustomerKey().getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                Customer customer = rootRepository.findCustomerByUsername(decodedJWT.getSubject());
                CustomerDto customerDto = new CustomerDto(customer.getUsername(), null,
                        customer.getFirstName(), customer.getBankAccount().getIban(), customer.getEmail());
                customerDto.setAccessToken(accessToken);
                return customerDto;
            } catch (Exception exception) {
                logger.info(exception.getMessage());
                return null;
            }
        } else
            return null;
    }



    public void refreshAdminToken(Admin adminToRefreshToken) {
        adminToRefreshToken.setAccessToken(createToken.createAccessToken(
                adminToRefreshToken.getUsername(), TokenKeyService.getAdminKey()));
        adminToRefreshToken.setRefreshToken(createToken.createRefreshToken(
                adminToRefreshToken.getUsername(), TokenKeyService.getAdminKey()));
    }

    public void refreshCustomerToken(CustomerDto customerToRefreshToken) {
        customerToRefreshToken.setAccessToken(createToken.createAccessToken(
                customerToRefreshToken.getUsername(), TokenKeyService.getCustomerKey()));
        customerToRefreshToken.setRefreshToken(createToken.createRefreshToken(
                customerToRefreshToken.getUsername(), TokenKeyService.getCustomerKey()));
    }
}

