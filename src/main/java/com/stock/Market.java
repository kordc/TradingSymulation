package com.stock;

/**
 * An abstract class for every market, where the trading is enabled
 */
public abstract class Market {
    private final String name;
    private final float transactionMargin;
    private final String country;
    private final String city;
    private final String address;

    public String getName() {
        return name;
    }

    public float getTransactionMargin() {
        return transactionMargin;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public Market(String name, float transactionMargin, String country, String city, String address) {
        this.name = name;
        this.transactionMargin = transactionMargin;
        this.country = country;
        this.city = city;
        this.address = address;
    }
}
