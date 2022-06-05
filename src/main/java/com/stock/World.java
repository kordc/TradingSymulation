package com.stock;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Company> companies;
    private final List <Currency> currencies;
    private final List <Commodity> commodities;
    private final List <StockMarket> stockMarkets;
    private final List <CurrencyMarket> currencyMarkets;
    private final List <CommodityMarket> commodityMarkets;
    private final List <MarketIndex> marketIndexes;
    private final List <Investor> investors;
    private final List <InvestmentFund> funds;
    private int numberOfCompanies = 0;
    private int numberOfCurrencies = 0;
    private int numberOfCommodities = 0;
    private int numberOfStockMarkets = 0;
    private int numberOfCurrencyMarkets = 0;
    private int numberOfCommodityMarkets = 0;
    private int numberOfMarketIndexes = 0;
    private int numberOfInvestors = 0;
    private int numberOfFunds = 0;
    private int transactionProbability = 50;
    private int bullBearRatio = 50;
    private float buyOutPercent = 50;

    public void addCompanies(Company company) {
        this.companies.add(company);
    }

    public void addCurrencies(Currency currency) {
        this.currencies.add(currency);
    }

    public void addCommodities(Commodity commodity) {
        this.commodities.add(commodity);
    }

    public void addStockMarkets(StockMarket stockMarket) {
        this.stockMarkets.add(stockMarket);
    }

    public void addCurrencyMarkets(CurrencyMarket currencyMarket) {
        this.currencyMarkets.add(currencyMarket);
    }

    public void addCommodityMarkets(CommodityMarket commodityMarket) {
        this.commodityMarkets.add(commodityMarket);
    }

    public void addMarketIndexes(MarketIndex marketIndex) {
        this.marketIndexes.add(marketIndex);
    }

    public void addInvestors(Investor investor) {
        this.investors.add(investor);
    }

    public void addFunds(InvestmentFund fund) {
        this.funds.add(fund);
    }

    public void setNumberOfCompanies(int numberOfCompanies) {
        this.numberOfCompanies = numberOfCompanies;
    }

    public void setNumberOfCurrencies(int numberOfCurrencies) {
        this.numberOfCurrencies = numberOfCurrencies;
    }

    public void setNumberOfCommodities(int numberOfCommodities) {
        this.numberOfCommodities = numberOfCommodities;
    }

    public void setNumberOfStockMarkets(int numberOfStockMarkets) {
        this.numberOfStockMarkets = numberOfStockMarkets;
    }

    public void setNumberOfCurrencyMarkets(int numberOfCurrencyMarkets) {
        this.numberOfCurrencyMarkets = numberOfCurrencyMarkets;
    }

    public void setNumberOfCommodityMarkets(int numberOfCommodityMarkets) {
        this.numberOfCommodityMarkets = numberOfCommodityMarkets;
    }

    public void setNumberOfMarketIndexes(int numberOfMarketIndexes) {
        this.numberOfMarketIndexes = numberOfMarketIndexes;
    }

    public void setNumberOfInvestors(int numberOfInvestors) {
        this.numberOfInvestors = numberOfInvestors;
    }

    public void setNumberOfFunds(int numberOfFunds) {
        this.numberOfFunds = numberOfFunds;
    }

    public void setTransactionProbability(int transactionProbability) {
        this.transactionProbability = transactionProbability;
    }

    public void setBullBearRatio(int bullBearRatio) {
        this.bullBearRatio = bullBearRatio;
    }

    public void setBuyOutPercent(float buyOutPercent) {
        this.buyOutPercent = buyOutPercent;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<Commodity> getCommodities() {
        return commodities;
    }

    public List<StockMarket> getStockMarkets() {
        return stockMarkets;
    }

    public List<CurrencyMarket> getCurrencyMarkets() {
        return currencyMarkets;
    }

    public List<CommodityMarket> getCommodityMarkets() {
        return commodityMarkets;
    }

    public List<MarketIndex> getMarketIndexes() {
        return marketIndexes;
    }

    public List<Investor> getInvestors() {
        return investors;
    }

    public List<InvestmentFund> getFunds() {
        return funds;
    }

    public int getNumberOfCompanies() {
        return numberOfCompanies;
    }

    public int getNumberOfCurrencies() {
        return numberOfCurrencies;
    }

    public int getNumberOfCommodities() {
        return numberOfCommodities;
    }

    public int getNumberOfStockMarkets() {
        return numberOfStockMarkets;
    }

    public int getNumberOfCurrencyMarkets() {
        return numberOfCurrencyMarkets;
    }

    public int getNumberOfCommodityMarkets() {
        return numberOfCommodityMarkets;
    }

    public int getNumberOfMarketIndexes() {
        return numberOfMarketIndexes;
    }

    public int getNumberOfInvestors() {
        return numberOfInvestors;
    }

    public int getNumberOfFunds() {
        return numberOfFunds;
    }

    public int getTransactionProbability() {
        return transactionProbability;
    }

    public int getBullBearRatio() {
        return bullBearRatio;
    }

    public float getBuyOutPercent() {
        return buyOutPercent;
    }

    public World() {
        this.companies = new ArrayList<>();
        this.currencies = new ArrayList<>();
        this.commodities = new ArrayList<>();
        this.stockMarkets = new ArrayList<>();
        this.currencyMarkets = new ArrayList<>();
        this.commodityMarkets = new ArrayList<>();
        this.marketIndexes = new ArrayList<>();
        this.investors = new ArrayList<>();
        this.funds = new ArrayList<>();
    }
}
