package com.stock;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.stock.App.world;

/**
 * Currency class which can be traded on CurrencyMarkets
 */
public class Currency extends Asset {
    private final List <Pair<Currency, Float>> exchangeRates;
    private final List<String> legalCountries;
    private float mainRate;
    private boolean isOnMarket;

    public boolean isOnMarket() {
        return isOnMarket;
    }

    public List<Pair<Currency, Float>> getExchangeRates() {
        return exchangeRates;
    }

    public List<String> getLegalCountries() {
        return legalCountries;
    }

    public Currency(String name) {
        super(name);
        this.mainRate = 1;
        this.exchangeRates = new ArrayList<>();
        this.legalCountries = new ArrayList<>();
        int starting_country = App.randomInt(1, 190);
        int finishing_country = starting_country + Math.min(App.randomInt(1, 10), 195);
        this.isOnMarket = false;
        for (int i=starting_country; i< Math.min(finishing_country, 195); i++){
            this.legalCountries.add(App.countries_names.get(i));
        }

        for (int i = 0; i< world.getNumberOfCurrencies(); i++){
            if (i == 0){
                this.mainRate = App.randomFloat(0.1f, 10);
                this.exchangeRates.add(new Pair<>(world.getCurrencies().get(i), this.mainRate));
                world.getCurrencies().get(i).exchangeRates.add(new Pair<>(this, 1/this.mainRate));
            }
            else{
                float itsMainRate = world.getCurrencies().get(i).mainRate;
                this.exchangeRates.add(new Pair<>(world.getCurrencies().get(i), this.mainRate/itsMainRate));
                world.getCurrencies().get(i).exchangeRates.add(new Pair<>(this, itsMainRate/this.mainRate));
            }
        }

        if(world.getNumberOfCurrencyMarkets() > 0){
            int id = App.randomInt(0, world.getNumberOfCurrencyMarkets());
            world.getCurrencyMarkets().get(id).addCurrency(this);
            this.isOnMarket = true;
        }
    }

    /**
     * Overridden toString listing all important properties of this object
     */
    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder("NAME:   " + this.getName() + "\n\n" +
                "AMOUNT:   " + this.getAmount() + "\n\n" +
                "Legal countries: \n");
        for(String s: this.getLegalCountries()){
            ans.append(s).append("\n");
        }
        ans.append("\nExchange rates: \n");
        for (Pair<Currency, Float> p: this.getExchangeRates()){
            Currency curr = p.getKey();
            ans.append(curr.getName()).append("   ").append(p.getValue()).append("\n");
        }
        return ans.toString();
    }

    /**
     * Function that looks  for the exchange rate of given currency
     * @param c we are looking for the exchange rate of this currency
     */
    public float getExchangeRate(Currency c){
        for(Pair<Currency,Float> p: this.exchangeRates){
            if(p.getKey() == c){
                return p.getValue();
            }
        }
        return 1;
    }
}
