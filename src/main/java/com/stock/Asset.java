package com.stock;

import static com.stock.App.*;

/**
 * Abstract class for a general type asset - to trade
 */
public abstract class Asset {
    private String name;
    private volatile float amount;

    /**
     * Constructor for the Asset class
     * @param name Name for the Asset
     */
    public Asset(String name) {
        this.name = name;
        this.amount = (float) randomInt(500, 10000);
    }

    public synchronized float getAmount() {
        return amount;
    }

    public synchronized void setAmount(float amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
