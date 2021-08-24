package com.example.cryptobank.domain;

public class Address {

    private String street;
    private String zipcode;
    private int houseNumber;
    private String addition;

    public Address(String street, String zipcode, int houseNumber, String addition) {
        this.street = street;
        this.zipcode = zipcode;
        this.houseNumber = houseNumber;
        this.addition = addition;
    }

    public Address(String street, String zipcode, int houseNumber) {
        this(street, zipcode, houseNumber, "");
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getzipcode() {
        return zipcode;
    }

    public void setzipcode(String zipcode) {
        this.zipcode = zipcode;
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
                ", postalCode='" + zipcode + '\'' +
                ", houseNumber=" + houseNumber +
                ", addition='" + addition + '\'' +
                '}';
    }
}
