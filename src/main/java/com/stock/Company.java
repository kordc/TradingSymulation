package com.stock;

import javafx.util.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.stock.App.*;

/**
 * Company class which isrun as a thread, its shares (amount) can be traded on a StockMarket
 */
public class Company extends Asset implements Runnable{
    private final LocalTime dateIPO;
    //private final Date dateIPO;
    private final float shareValueIPO;
    private final float openingPrice;
    private volatile float currPrice;
    private float minPrice;
    private float maxPrice;
    private float profit;
    private float revenue;
    private final float capital;
    private volatile int tradingVol;
    private volatile int totalSales;
    private Currency tradingCurr;
    private boolean isOnMarket;

    public List<Pair<LocalTime, Float>> getPricesList() {
            return pricesList;
        }

    private final List <Pair<LocalTime, Float>> pricesList;

    public Currency getTradingCurr() {
        return tradingCurr;
    }

    public boolean isOnMarket() {
        return isOnMarket;
    }

    public void setTradingCurr(Currency tradingCurr) {
        this.tradingCurr = tradingCurr;
    }

    public Company(String name) {
        super(name);
        this.dateIPO = LocalTime.now().withNano(0);
        this.shareValueIPO = randomFloat(100,10000000);
        this.openingPrice = randomFloat(2,150);
        this.currPrice = this.openingPrice;
        this.minPrice = this.currPrice;
        this.maxPrice = this.currPrice;
        this.profit = randomFloat(5000,100000);
        this.revenue = randomFloat(5000,100000);
        this.capital = randomFloat(100000,20000000);
        this.tradingVol = 0;
        this.totalSales = 0;
        this.tradingCurr = null;
        this.isOnMarket = false;
        this.pricesList = new ArrayList<>();
        this.pricesList.add(new Pair<>(this.dateIPO, this.currPrice));
        if(world.getNumberOfStockMarkets() > 0){
            int id = App.randomInt(0,App.world.getNumberOfStockMarkets());
            world.getStockMarkets().get(id).addCompany(this);
            this.isOnMarket = true;
            this.tradingCurr = world.getStockMarkets().get(id).getTradingCurr();
        }

        for(MarketIndex mi: world.getMarketIndexes()){
            if(this.capital >= mi.getThreshold()){
                mi.addCompany(this);
            }
        }
    }

    public LocalTime getDateIPO() {
        return dateIPO;
    }

    public float getShareValueIPO() {
        return shareValueIPO;
    }

    public float getOpeningPrice() {
        return openingPrice;
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

    public float getProfit() {
        return profit;
    }

    public float getRevenue() {
        return revenue;
    }

    public float getCapital() {
        return capital;
    }

    public int getTradingVol() {
        return tradingVol;
    }

    public int getTotalSales() {
        return totalSales;
    }

    /**
     * Setter method setting current price, but moreover, updating the maximum and minimum price and adding the price to the list
     */
    public synchronized void setCurrPrice(float currPrice) {
        this.currPrice = currPrice;
        this.pricesList.add(new Pair<>(LocalTime.now().withNano(0), this.currPrice));
        this.maxPrice = Math.max(this.maxPrice, this.currPrice);
        this.minPrice = Math.min(this.minPrice, this.currPrice);
    }

    public void setTradingVol(int tradingVol) {
        this.tradingVol = tradingVol;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * Special kind of toString listing all important properties of this object
     */
    public String toStringProperties(){
        return "NAME:   " + this.getName() + "\n\n" +
                "AMOUNT:   " + this.getAmount() + "\n\n" +
                "IPO date:\n" + this.getDateIPO() + "\n\n"+
                "IPO share value:\n" + this.getShareValueIPO() + "\n\n"+
                "Opening price:\n" + this.getOpeningPrice() + "\n\n"+
                "Current price:\n" + this.getCurrPrice() + "\n\n"+
                "Minimum price:\n" + this.getMinPrice() + "\n\n"+
                "Maximum price:\n" + this.getMaxPrice() + "\n\n"+
                "Profit:\n" + this.getProfit() + "\n\n" +
                "Revenue:\n" + this.getRevenue() + "\n\n"+
                "Capital:\n" + this.getCapital() + "\n\n"+
                "Trading volume:\n" + this.getTradingVol() + "\n\n"+
                "Total sales:\n" + this.getTotalSales() + "\n\n";
    }

    /**
     * Main function which performs changing company's profit and revenue, activated when the thread is started
     */
    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true){
            this.revenue += randomFloat(0,2500);
            this.profit += this.revenue * randomFloat(-1, 2);
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public String toString(){
        return this.getName();
    }

    /**
     * Function that makes a buy out - it buys out some % of its own shares
     */
    public void makeBuyOut(){
        System.out.println("Making a buy out of " + this.getName() + " from " + this.getAmount() + " to " + this.getAmount() * (world.getBuyOutPercent()/100));
        this.setAmount(this.getAmount() * (world.getBuyOutPercent()/100));
    }
}
