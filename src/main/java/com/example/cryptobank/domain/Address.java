package com.example.cryptobank.domain;

public class Address {

    private String street;
    private String postalCode;
    private int houseNumber;
    private String addition;

    public Address(String street, String postalCode, int houseNumber, String addition) {
        this.street = street;
        this.postalCode = postalCode;
        this.houseNumber = houseNumber;
        this.addition = addition;
    }

    public Address(String street, String postalCode, int houseNumber) {
        this(street, postalCode, houseNumber, "");
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", houseNumber=" + houseNumber +
                ", addition='" + addition + '\'' +
                '}';
    }
}
