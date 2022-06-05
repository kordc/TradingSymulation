package com.stock;

import java.util.ArrayList;
import java.util.List;

import static com.stock.App.*;

/**
 * Market class, here you can trade companies' stocks
 */
public class StockMarket extends Market {
    private final Currency tradingCurr;
    private final List<MarketIndex> marketIndexes;
    private final List<Company> companies;

    public StockMarket(String name, float transactionMargin, String country, String city, String address) {
        super(name, transactionMargin, country, city, address);
        if(world.getNumberOfCurrencies() == 0){
            Currency c = new Currency(currencies_names.get(world.getNumberOfCurrencies()));
            world.setNumberOfCurrencies(world.getNumberOfCurrencies()+1);
            world.addCurrencies(c);
        }
        this.tradingCurr = world.getCurrencies().get(randomInt(0, world.getNumberOfCurrencies()));
        this.marketIndexes = new ArrayList<>();
        this.companies = new ArrayList<>();
        for (Company c: world.getCompanies()){
            if(!c.isOnMarket()){
                this.companies.add(c);
                c.setTradingCurr(this.tradingCurr);
            }
        }
        for(MarketIndex mi: world.getMarketIndexes()){
            if(!mi.isOnMarket()){
                this.marketIndexes.add(mi);
            }
        }
    }

    public Currency getTradingCurr() {
        return tradingCurr;
    }

    public List<MarketIndex> getMarketIndexes() {
        return marketIndexes;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void addCompany(Company company) {
        this.companies.add(company);
    }

    public void addMarketIndex(MarketIndex index){
        this.marketIndexes.add(index);
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
        ans.append("Market indexes:\n");
        for(MarketIndex mi: this.getMarketIndexes()){
            ans.append(mi.getName()).append("\n");
        }
        ans.append("\nCompanies:\n");
        for(Company c: this.getCompanies()){
            ans.append(c.getName()).append("\n");
        }
        return ans.toString();
    }
}