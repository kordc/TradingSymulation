package com.stock;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.stock.App.*;

public class Investor extends Thread{
    private final String name;
    private final int tradingID;
    private float budget;
    private final List<Pair<Asset, Float>> assets;
    private final Currency currency;

    public Investor(String name) {
        this.name = name;
        this.tradingID = randomInt(10000,99999);
        this.budget = randomFloat(100,30000);
        this.assets = new ArrayList<>();
        this.currency = world.getCurrencies().get(randomInt(0, world.getNumberOfCurrencies()));
    }

    public String getInvestorName() {
        return name;
    }

    public int getTradingID() {
        return tradingID;
    }

    public float getBudget() {
        return budget;
    }

    public List<Pair<Asset, Float>> getAssets() {
        return assets;
    }

    public Currency getCurrency() {
        return currency;
    }

    /**
     * The function which performs a buy operation - it buys commodities and companies' stocks
     * @param howMany how many stocks/units we want to buy
     * @param what which asset we want to buy
     */
    public void buy(float howMany, Asset what, Market market){
        if(what instanceof Currency){
            float priceForInvestor = howMany*((Currency) what).getExchangeRate(this.currency)*(1+market.getTransactionMargin());
            if(priceForInvestor < this.budget && this.currency != what && what.getAmount() >= howMany){
                buyBasic(howMany, what, priceForInvestor);
                System.out.println("Buying " + howMany+ " of Currency " + what.getName() + " for: " + this.getInvestorName());
            }
        }
        else if(what instanceof Commodity){
            float priceForInvestor = howMany*((Commodity) what).getTradingCurr().getExchangeRate(this.currency)*((Commodity) what).getCurrPrice()*(1+market.getTransactionMargin());
            if (priceForInvestor < this.budget && what.getAmount() >= howMany){
                buyBasic(howMany, what, priceForInvestor);
                System.out.println("Buying " + howMany+ " of Commodity " + what.getName() + " for: " + this.getInvestorName());
                ((Commodity) what).setCurrPrice(((Commodity) what).getCurrPrice() * 1.01f);
            }
        }
        else if (what instanceof Company){
            float priceForInvestor = howMany*((Company) what).getTradingCurr().getExchangeRate(this.currency)*((Company) what).getCurrPrice()*(1+market.getTransactionMargin());
            if (priceForInvestor < this.budget && what.getAmount() >= howMany){
                buyBasic(howMany, what, priceForInvestor);
                ((Company) what).setTradingVol(((Company) what).getTradingVol()+1);
                ((Company) what).setTotalSales(((Company) what).getTotalSales()+1);
                System.out.println("Buying " + howMany+ " of Company " + what.getName() + " for: " + this.getInvestorName());
                ((Company) what).setCurrPrice(((Company) what).getCurrPrice() * 1.01f);
            }
        }
        else if(what instanceof InvestmentFund){
            float priceForInvestor = howMany*((InvestmentFund) what).getCurrency().getExchangeRate(this.currency)*((InvestmentFund) what).getValue();
            if (priceForInvestor < this.budget && what.getAmount() >= howMany){
                buyBasic(howMany, what, priceForInvestor);
                System.out.println("Buying " + howMany+ " of investemnt's fund unit " + what.getName() + " for: " + this.getInvestorName());
            }
        }
    }

    private void buyBasic(float howMany, Asset what, float priceForInvestor) {
        this.budget -= priceForInvestor;
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

    /**
     * The function which performs a sell operation from the investor to the market
     * @param howMany how many stocks/units we want to buy
     * @param what which asset we want to buy
     * @param idx index of the asset in investor's wallet
     */
    public void sell(float howMany, Asset what, int idx){//howmany=percent(0,100]
        if(what instanceof Currency){
            if(this.currency != what && howMany <= 1 && howMany > 0){
                float priceForInvestor = this.assets.get(idx).getValue()*howMany*((Currency) what).getExchangeRate(this.currency);
                this.budget += priceForInvestor;
                what.setAmount(what.getAmount()+howMany*howMany*assets.get(idx).getValue());
                if(howMany == 1){
                    this.assets.remove(idx);
                }
                else{
                    this.assets.set(idx, new Pair<>(assets.get(idx).getKey(), assets.get(idx).getValue()-howMany*assets.get(idx).getValue()));
                }
                System.out.println("Selling " + howMany+ "% of currency " + what.getName() + " from: " + this.getInvestorName());
            }
        }
        else if(what instanceof Commodity){
            float priceForInvestor = this.assets.get(idx).getValue()*howMany*((Commodity) what).getTradingCurr().getExchangeRate(this.currency)*((Commodity) what).getCurrPrice();
            this.budget += priceForInvestor;
            what.setAmount(what.getAmount()+howMany*howMany*assets.get(idx).getValue());
            if(howMany == 1){
                this.assets.remove(idx);
            }
            else{
                this.assets.set(idx, new Pair<>(assets.get(idx).getKey(), assets.get(idx).getValue()-howMany*assets.get(idx).getValue()));
            }
            System.out.println("Selling " + howMany+ "% of commodity " + what.getName() + " from: " + this.getInvestorName());
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
            System.out.println("Selling " + howMany+ "% of company " + what.getName() + " from: " + this.getInvestorName());
            ((Company) what).setCurrPrice(((Company) what).getCurrPrice() * 0.99f);
            ((Company) what).setTradingVol(((Company) what).getTradingVol()+1);
        }
        else if (what instanceof MarketIndex){
            float priceForInvestor = this.assets.get(idx).getValue()*howMany*((MarketIndex) what).getTradingCurr().getExchangeRate(this.currency)*((MarketIndex) what).getCurrPrice();
            this.budget += priceForInvestor;
            what.setAmount(what.getAmount()+howMany*howMany*assets.get(idx).getValue());
            if(howMany == 1){
                this.assets.remove(idx);
            }
            else{
                this.assets.set(idx, new Pair<>(assets.get(idx).getKey(), assets.get(idx).getValue()-howMany*assets.get(idx).getValue()));
            }
            System.out.println("Selling " + howMany+ "% of market index " + what.getName() + " from: " + this.getInvestorName());
            ((MarketIndex) what).setCurrPrice(((MarketIndex) what).getCurrPrice() * 0.99f);
        }
        else if(what instanceof InvestmentFund){
            float priceForInvestor = this.assets.get(idx).getValue()*howMany*((InvestmentFund) what).getCurrency().getExchangeRate(this.currency)*((InvestmentFund) what).getValue();
            this.budget += priceForInvestor;
            what.setAmount(what.getAmount()+howMany*howMany*assets.get(idx).getValue());
            if(howMany == 1){
                this.assets.remove(idx);
            }
            else{
                this.assets.set(idx, new Pair<>(assets.get(idx).getKey(), assets.get(idx).getValue()-howMany*assets.get(idx).getValue()));
            }
            System.out.println("Selling " + howMany+ "% of market index " + what.getName() + " from: " + this.getInvestorName());
        }
    }

    /**
     * Overridden toString listing all important properties of this object
     */
    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder("NAME:   " + this.getInvestorName() + "\n\n" +
                "Trading ID:\n" + this.getTradingID() + "\n\n" +
                "Budget:\n" + this.getBudget() + "\n\n" +
                "Budget in currency:\n" + this.getCurrency().getName() + "\n\n");
        ans.append("Assets:\n");
        for(Pair<Asset, Float> p: this.assets){
            String type = "(Currency)  ";
            if (p.getKey() instanceof Company) type = "(Company)  ";
            if (p.getKey() instanceof Commodity) type = "(Commodity)  ";
            if (p.getKey() instanceof Currency) type = "(Currency)  ";
            if (p.getKey() instanceof InvestmentFund) type = "(InvestmentFund)  ";
            if (p.getKey() instanceof MarketIndex) type = "(MarketIndex)  ";
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
        while (true){
            if(randomInt(0,100) <= world.getTransactionProbability()){
                int decision = randomInt(0, 100);
                if(decision < world.getBullBearRatio()){
                    //buyDirectly
                    int whatToBuy = randomInt(0,5);
                    if(whatToBuy == 0){
                        //stock
                        StockMarket toTrade = world.getStockMarkets().get(randomInt(0, world.getNumberOfStockMarkets()));
                        float howMany = randomFloat(0,4);
                        Asset toBuy = toTrade.getCompanies().get(randomInt(0, toTrade.getCompanies().size()));
                        this.buy(howMany, toBuy, toTrade);
                    }
                    else if(whatToBuy == 1){
                        //currency
                        CurrencyMarket toTrade = world.getCurrencyMarkets().get(randomInt(0, world.getNumberOfCurrencyMarkets()));
                        float howMany = randomFloat(0,4);
                        Asset toBuy = toTrade.getCurrencies().get(randomInt(0, toTrade.getCurrencies().size()));
                        this.buy(howMany, toBuy, toTrade);
                    }
                    else if(whatToBuy == 2){
                        //commodity
                        CommodityMarket toTrade = world.getCommodityMarkets().get(randomInt(0, world.getNumberOfCommodityMarkets()));
                        float howMany = randomFloat(0,4);
                        Asset toBuy = toTrade.getCommodities().get(randomInt(0, toTrade.getCommodities().size()));
                        this.buy(howMany, toBuy, toTrade);
                    }
                    else if(whatToBuy == 3){
                        //fund
                        InvestmentFund toTrade = world.getFunds().get(randomInt(0, world.getNumberOfFunds()));
                        float howMany = randomFloat(0,4);
                        this.buy(howMany, toTrade, null);
                    }
                    else{
                        //index
                        MarketIndex toTrade = world.getMarketIndexes().get(randomInt(0, world.getNumberOfMarketIndexes()));
                        float howMany = randomFloat(0,4);
                        this.buy(howMany, toTrade, null);
                    }
                }
                if(decision < 100-world.getBullBearRatio()){
                    //sellDirectly
                    if(this.getAssets().size() > 0){
                        int idx = randomInt(0, this.getAssets().size());
                        this.sell(randomFloat(0,1.01f), this.getAssets().get(idx).getKey(), idx);
                    }
                }
            }
            if(randomInt(0,100) <= 20){
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
