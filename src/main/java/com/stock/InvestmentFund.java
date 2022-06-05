package com.stock;

import javafx.util.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.stock.App.*;

/**
 * InvestmentFund - funds buy assets as investors, creates bundles and sells it in behaviour of an asset
 * @see Investor
 */
public class InvestmentFund extends Asset implements Runnable{
    private final String name;
    private final String managerName;
    private final List<Pair<Asset, Float>> assets;
    private final Currency currency;
    private float budget;
    private final float allShares;
    private float value;
    private final List<Pair<LocalTime, Float>> pricesList;

    public List<Pair<LocalTime, Float>> getPricesList() {
        return pricesList;
    }


    public InvestmentFund(String name) {
        super(name);
        this.name = name;
        this.managerName = App.names.get(App.randomInt(0,200));
        this.assets = new ArrayList<>();
        this.currency = world.getCurrencies().get(randomInt(0, world.getNumberOfCurrencies()));
        this.budget = randomFloat(100,30000);
        this.allShares = this.getAmount();
        this.value = getValueInit();
        this.pricesList = new ArrayList<>();
        this.pricesList.add(new Pair<>(LocalTime.now().withNano(0), this.value));
    }

    public float getValue() {
        return value;
    }

    /**
     * Calculate the current value of the fund's wallet
     */
    public float getValueInit(){
        int sum = 0;
        for (Pair<Asset, Float> p: this.assets) {
            if(p.getKey() instanceof Commodity){
                sum += p.getValue() * ((Commodity) p.getKey()).getCurrPrice();
            }
            if(p.getKey() instanceof Company){
                sum += p.getValue() * ((Company) p.getKey()).getCurrPrice();
            }
        }
        return sum/this.allShares;
    }

    /**
     * The function which performs a buy operation - it buys commodities and companies' stocks
     * @param howMany how many stocks/units we want to buy
     * @param what which asset we want to buy
     */
    public void buy(float howMany, Asset what){
        if(what instanceof Commodity){
            float priceForFund = howMany*((Commodity) what).getTradingCurr().getExchangeRate(this.currency)*((Commodity) what).getCurrPrice();
            if (priceForFund < this.budget && what.getAmount() >= howMany){
                buyBasic(howMany, what, priceForFund);
                System.out.println("Buying " + howMany+ " of Commodity " + what.getName() + " for: " + this.getFundName());
                ((Commodity) what).setCurrPrice(((Commodity) what).getCurrPrice() * 1.01f);
            }
        }
        else if (what instanceof Company){
            float priceForFund = howMany*((Company) what).getTradingCurr().getExchangeRate(this.currency)*((Company) what).getCurrPrice();
            if (priceForFund < this.budget && what.getAmount() >= howMany){
                buyBasic(howMany, what, priceForFund);
                System.out.println("Buying " + howMany+ " of Company " + what.getName() + " for: " + this.getFundName());
                ((Company) what).setCurrPrice(((Company) what).getCurrPrice() * 1.01f);
            }
        }
        this.value = getValueInit();
        this.pricesList.add(new Pair<>(LocalTime.now().withNano(0), this.value));
    }

    private void buyBasic(float howMany, Asset what, float priceForFund) {
        this.budget -= priceForFund;
        int i=0;
        boolean found = false;
        for(Pair<Asset, Float> p: this.assets){
            if(Objects.equals(p.getKey().getName(), what.getName())){
                this.assets.set(i, new Pair<>(p.getKey(), p.getValue()+howMany));
                found = true;
            }
            i++;
        }
        if(!found){
            this.assets.add(new Pair<>(what, howMany));
        }
        what.setAmount(what.getAmount()-howMany);

    }

    public List<Pair<Asset, Float>> getAssets() {
        return assets;
    }

    public Currency getCurrency() {
        return currency;
    }

    /**
     * The function which performs a sell operation
     * @param howMany how many stocks/units we want to buy
     * @param what which asset we want to buy
     * @param idx index of the asset in investor's wallet
     */
    public void sell(float howMany, Asset what, int idx){
        if(what instanceof Commodity){
            float priceForInvestor = this.assets.get(idx).getValue()*howMany*((Commodity) what).getTradingCurr().getExchangeRate(this.currency)*((Commodity) what).getCurrPrice();
            this.budget += priceForInvestor;
            what.setAmount(what.getAmount()+howMany*howMany*assets.get(idx).getValue());
            if(howMany == 1){
                this.assets.remove(idx);
            }
            else{
                this.assets.set(idx, new Pair<>(assets.get(idx).getKey(), assets.get(idx).getValue()-howMany*assets.get(idx).getValue()));
            }
            System.out.println("Selling " + howMany+ " of commodity " + what.getName() + " from: " + this.getFundName());
            ((Commodity) what).setCurrPrice(((Commodity) what).getCurrPrice() * 0.99f);
        }
        else if (what instanceof Company){
            float priceForInvestor = this.assets.get(idx).getValue()*howMany*((Company) what).getTradingCurr().getExchangeRate(this.currency)*((Company) what).getCurrPrice();
            this.budget += priceForInvestor;
            what.setAmount(what.getAmount()+howMany*howMany*assets.get(idx).getValue());
            if(howMany == 1){
                this.assets.remove(idx);
            }
            else{
                this.assets.set(idx, new Pair<>(assets.get(idx).getKey(), assets.get(idx).getValue()-howMany*assets.get(idx).getValue()));
            }
            System.out.println("Selling " + howMany+ " of company " + what.getName() + " from: " + this.getFundName());
            ((Company) what).setCurrPrice(((Company) what).getCurrPrice() * 0.99f);
        }
        this.value = getValueInit();
        this.pricesList.add(new Pair<>(LocalTime.now().withNano(0), this.value));
    }

    public String getFundName() {
        return name;
    }

    public String getManagerName() {
        return managerName;
    }

    /**
     * Overridden toString listing all important properties of this object
     */
    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder("NAME:   " + this.getFundName() + "\n\n" +
                "Manager name:\n" + this.getManagerName() + "\n\n");
        ans.append("ASSETS:\n");
        for(Pair<Asset, Float> p: this.assets){
            String type = "(Currency)  ";
            if (p.getKey() instanceof Company) type = "(Company)  ";
            if (p.getKey() instanceof Commodity) type = "(Commodity)  ";
            ans.append(type).append(p.getKey().getName()).append("  ").append(p.getValue()).append("\n");
        }
        return ans.toString();
    }

    /**
     * Main function which performs trading, activated when the thread is started
     */
    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            if (randomInt(0, 100) <= world.getTransactionProbability()) {
                //buyDirectly
                int decision = randomInt(0, 100);
                if (decision < world.getBullBearRatio()) {
                    int iff = randomInt(0,100);
                    if(iff%4 == 0){
                        this.buy(randomFloat(0,2), world.getCurrencies().get(randomInt(0, world.getNumberOfCurrencies())));
                    }
                    else if(iff%4 == 1){
                        this.buy(randomFloat(0,2), world.getCommodities().get(randomInt(0, world.getNumberOfCommodities())));
                    }
                    else if(iff%4==2){
                        this.buy(randomFloat(0,2), world.getCompanies().get(randomInt(0, world.getNumberOfCompanies())));
                    }
                    else{
                        this.buy(randomFloat(0,2), world.getMarketIndexes().get(randomInt(0, world.getNumberOfMarketIndexes())));
                    }
                }
                if(decision < 100-world.getBullBearRatio()){
                    if(this.getAssets().size() > 0){
                        int idx = randomInt(0, this.getAssets().size());
                        this.sell(randomFloat(0,1.01f), this.getAssets().get(idx).getKey(), idx);
                    }
                }
            }
            if (randomInt(0, 100) <= 50) {
                this.budget *= 1.2;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
