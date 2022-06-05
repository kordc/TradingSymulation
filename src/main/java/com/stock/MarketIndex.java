package com.stock;

import javafx.util.Pair;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.stock.App.*;

/**
 * A market index stores specified companies to help in trading
 */
public class MarketIndex extends Asset {
    //based only on the current capital (<=X)
    private final List<Company> companies;
    private float currPrice;
    private float minPrice;
    private float maxPrice;
    private final float threshold;
    private boolean isOnMarket;
    private final Currency tradingCurr;
    public List<Pair<LocalTime, Float>> getPricesList() {
        return pricesList;
    }

    private final List <Pair<LocalTime, Float>> pricesList;

    public boolean isOnMarket() {
        return isOnMarket;
    }

    public Currency getTradingCurr() {
        return tradingCurr;
    }

    public MarketIndex(String name) {
        super(name);
        this.companies = new ArrayList<>();
        this.currPrice = randomFloat(2,150);
        this.minPrice = this.currPrice;
        this.maxPrice = this.currPrice;
        this.threshold = randomFloat(900000,20000000);
        this.isOnMarket = false;
        this.tradingCurr = world.getCurrencies().get(randomInt(0, world.getNumberOfCurrencies()));
        this.pricesList = new ArrayList<>();
        this.pricesList.add(new Pair<>(LocalTime.now().withNano(0), this.currPrice));
        for(Company c: world.getCompanies()){
            if(c.getCapital() >= this.threshold){
                this.companies.add(c);
            }
        }
        if(world.getNumberOfMarketIndexes() > 0){
            int id = App.randomInt(0,App.world.getNumberOfMarketIndexes());
            world.getStockMarkets().get(id).addMarketIndex(this);
            this.isOnMarket = true;
        }
    }


    public List<Company> getCompanies() {
        return companies;
    }

    public float getCurrPrice() {
        return currPrice;
    }

    /**
     * Setter method setting current price, but moreover, updating the maximum and minimum price and adding the price to the list
     */
    public void setCurrPrice(float currPrice){
        this.currPrice = currPrice;
        this.pricesList.add(new Pair<>(LocalTime.now().withNano(0), this.currPrice));
        this.maxPrice = Math.max(this.maxPrice, this.currPrice);
        this.minPrice = Math.min(this.minPrice, this.currPrice);
    }

    public float getMinPrice() {
        return minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public float getThreshold() {
        return threshold;
    }

    public void addCompany(Company company){
        this.companies.add(company);
    }

    /**
     * Overridden toString listing all important properties of this object
     */
    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder();
        ans.append("NAME:   ").append(this.getName()).append("\n\n");
        ans.append("Current price:\n").append(this.getCurrPrice()).append("\n\n");
        ans.append("Minimum price:\n").append(this.getMinPrice()).append("\n\n");
        ans.append("Maximum price:\n").append(this.getMaxPrice()).append("\n\n");
        ans.append("Capital threshold for companies to be here:\n").append(this.getThreshold()).append("\n\n");
        ans.append("Companies:\n");
        for(Company c: this.getCompanies()){
            ans.append(c.getName()).append("\n");
        }
        return ans.toString();
    }
}
