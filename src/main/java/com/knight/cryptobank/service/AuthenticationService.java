package com.knight.cryptobank.service;

import com.knight.cryptobank.database.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private RootRepository rootRepository;
    private HashHelper hashHelper;
    public static final String REGEX_UUID = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    public AuthenticationService(RootRepository rootRepository, HashHelper hashHelper) {
        this.rootRepository = rootRepository;
        this.hashHelper = hashHelper;
        logger.info("New AuthenticationService");
    }


    public boolean authenticateAdminToken(String token) {
        // optioneel UUID format controle
        if(!token.matches(REGEX_UUID)){
            return false;
        }

        String username = rootRepository.findAdminUsernameByToken(token);
        // als er een user gevonden wordt is er natuurlijk ook een token
        return username != null; // tis nie mooi
    }

    public boolean authenticateCustomerToken(String token) {
        // optioneel UUID format controle
        if(!token.matches(REGEX_UUID)){
            return false;
        }

        String username = rootRepository.findCustomerUsernameByToken(token);
        // als er een user gevonden wordt is er natuurlijk ook een token
        return username != null; // tis nie mooi
    }
}

