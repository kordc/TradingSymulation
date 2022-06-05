package com.stock;

import javafx.util.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A Commodity class which is a type of Asset, may be used in trading on a Commodity Markets
 * @see CommodityMarket
 */
public class Commodity extends Asset {
    private final String tradingUnit;
    private final Currency tradingCurr;
    private float currPrice;
    private float minPrice;
    private float maxPrice;
    private boolean isOnMarket;
    private final List<Pair<LocalTime, Float>> pricesList;


    public String getTradingUnit() {
        return tradingUnit;
    }

    public Currency getTradingCurr() {
        return tradingCurr;
    }

    public float getCurrPrice() {
        return currPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    /**
     * Getter method checking whether the commodity is on a market
     */
    public boolean isOnMarket() {
        return isOnMarket;
    }

    public List<Pair<LocalTime, Float>> getPricesList() {
        return pricesList;
    }

    /**
     * Setter method setting current price, but moreover, updating the maximum and minimum price and adding the price to the list
     */
    public void setCurrPrice(float currPrice) {
        this.currPrice = currPrice;
        this.pricesList.add(new Pair<>(LocalTime.now().withNano(0), this.currPrice));
        this.maxPrice = Math.max(this.maxPrice, this.currPrice);
        this.minPrice = Math.min(this.minPrice, this.currPrice);
    }

    public Commodity(String name) {
        super(name);
        this.tradingUnit = App.commodities_units.get(App.randomInt(0, 6));
        this.tradingCurr = App.world.getCurrencies().get(App.randomInt(0, App.world.getNumberOfCurrencies()));
        this.currPrice = App.randomFloat(10, 100);
        this.minPrice = App.randomFloat(10, 100);
        this.maxPrice = App.randomFloat(this.minPrice, 100);
        this.isOnMarket = false;

        if(App.world.getNumberOfCommodityMarkets() > 0){
            int id = App.randomInt(0,App.world.getNumberOfCommodityMarkets());
            App.world.getCommodityMarkets().get(id).addCommodity(this);
            this.isOnMarket = true;
        }
        this.pricesList = new ArrayList<>();
        this.pricesList.add(new Pair<>(LocalTime.now().withNano(0), this.currPrice));
    }

    /**
     * Overridden toString listing all important properties of this object
     */
    @Override
    public String toString(){
        return "NAME:   " + this.getName() + "\n\n" +
                "AMOUNT:   " + this.getAmount() + "\n\n" +
                "Trading Unit:\n" + this.getTradingUnit() + "\n\n" +
                "Trading currency:\n" + this.getTradingCurr().getName() + "\n\n" +
                "Current price:\n" + this.getCurrPrice() + "\n\n" +
                "Minimum price:\n" + this.getMinPrice() + "\n\n" +
                "Maximum price:\n" + this.getMaxPrice() + "\n\n";
    }
}
