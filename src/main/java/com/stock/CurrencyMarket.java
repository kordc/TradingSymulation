package com.stock;

import java.util.ArrayList;
import java.util.List;

/**
 * CurrencyMarket - a place to trade currencies
 */
public class CurrencyMarket extends Market{
    private final List<Currency> currencies;

    public CurrencyMarket(String name, float transactionMargin, String country, String city, String address) {
        super(name, transactionMargin, country, city, address);
        this.currencies = new ArrayList<>();
        for (Currency c: App.world.getCurrencies()){
            if(!c.isOnMarket()){
                this.currencies.add(c);
            }
        }
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void addCurrency(Currency currency) {
        this.currencies.add(currency);
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
        ans.append("Currencies:\n");
        for(Currency mi: this.getCurrencies()){
            ans.append(mi.getName()).append("\n");
        }
        return ans.toString();
    }
}
