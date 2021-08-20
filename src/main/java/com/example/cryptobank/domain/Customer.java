package com.example.cryptobank.domain;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User{

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int socialSecurityNumber;
    //private BankAccount bankAccount;
    private Address address;
    private List<Asset> assetList;


    public Customer(String username, String password, String salt,
                    String firstName, String lastName, LocalDate dateOfBirth, int socialSecurityNumber,
                    Address address) {
        super(username, password, salt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.socialSecurityNumber = socialSecurityNumber;
        this.address = address;
        this.assetList = new ArrayList<>();
    }

    public Customer(String username, String password) {
        super(username, password);
    }


    // TODO: 20/08/2021 We moeten de usernames gaan vergelijken op username dus moeten we de comparable correct implementeren
    // de vraag die ik hier bij heb is of je dit steeds moet gaan willen vergelijken met de DB of dat je een lijst hebt met alle usernames en de
    // vergelijking uitvoert in java ipv de DB

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
