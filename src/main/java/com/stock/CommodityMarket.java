package com.stock;

import java.util.ArrayList;
import java.util.List;

import static com.stock.App.*;

/**
 * Market class, here you can trade commodities
 */
public class CommodityMarket extends Market {
    private final Currency tradingCurr;
    private final List<Commodity> commodities;

    public CommodityMarket(String name, float transactionMargin, String country, String city, String address) {
        super(name, transactionMargin, country, city, address);
        if(world.getNumberOfCurrencies() == 0){
            Currency c = new Currency(currencies_names.get(world.getNumberOfCurrencies()));
            world.setNumberOfCurrencies(world.getNumberOfCurrencies()+1);
            world.addCurrencies(c);
        }
        this.tradingCurr = world.getCurrencies().get(randomInt(0, world.getNumberOfCurrencies()));
        this.commodities = new ArrayList <>();
        for (Commodity c: world.getCommodities()){
            if(!c.isOnMarket()){
                this.commodities.add(c);
            }
        }
    }

    public Currency getTradingCurr() {
        return tradingCurr;
    }

    public List<Commodity> getCommodities() {
        return commodities;
    }

    public void addCommodity(Commodity commodity) {
        this.commodities.add(commodity);
    }

    /**
     * Overridden toString listing all important properties of this object
     */
    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder();
        ans.append("NAME:   ").append(this.getName()).append("\n\n");
        ans.append("Transaction margin:\n").append(this.getTransactionMargin()).append("\n\n");
        ans.append("Country:\n").append(this.getCountry()).append("\n\n");
        ans.append("City:\n").append(this.getCity()).append("\n\n");
        ans.append("Address:\n").append(this.getAddress()).append("\n\n");
        ans.append("Trading currency:\n").append(this.getTradingCurr().getName()).append("\n\n");
        ans.append("Commodities:\n");
        for(Commodity mi: this.getCommodities()){
            ans.append(mi.getName()).append("\n");
        }
        return ans.toString();
    }
}
