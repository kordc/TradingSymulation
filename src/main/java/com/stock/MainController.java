package com.stock;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.stock.App.*;


/**
 * A controller class providing controllers to the App GUI
 * @see App
 */
public class MainController {
    @FXML
    private SplitMenuButton menu;

    @FXML
    private Slider transactionsSlider;
    @FXML
    private Slider animalsSlider;
    @FXML
    private Slider buyOutSlider;

    @FXML
    private Label transactionsLabel;
    @FXML
    private Label animalsLabel;
    @FXML
    private Label buyOutLabel;

    @FXML
    private ChoiceBox <Company> buyOutChoice;

    @FXML
    private LineChart<String, Number> lineChart;


    /**
     * Initializing the controller, creating first objects and sliders properties
     */
    @FXML
    public void initialize() {
        this.appOut.setEditable(false);
        this.propertyText.setEditable(false);
        this.createCommodityMarket();
        this.createCurrencyMarket();
        this.createStockMarket();
        this.createCurrency();
        this.createCommodity();
        this.createCompany();
        this.createMarketIndex();
        this.createFund();
        this.createInvestor();

        this.transactionsSlider.valueProperty().addListener((observableValue, number, t1) -> {
            world.setTransactionProbability((int)transactionsSlider.getValue());
            transactionsLabel.setText(Integer.toString(world.getTransactionProbability()));
        });
        this.animalsSlider.valueProperty().addListener((observableValue, number, t1) -> {
            world.setBullBearRatio((int)animalsSlider.getValue());
            animalsLabel.setText(Integer.toString(world.getBullBearRatio()));
        });

        this.buyOutSlider.valueProperty().addListener((observableValue, number, t1) -> {
            world.setBuyOutPercent((int)buyOutSlider.getValue());
            buyOutLabel.setText(Integer.toString((int) world.getBuyOutPercent()));
        });
        this.buyOutChoice.setOnAction(this::makeBuyOutController);
    }

    private Company toBuyOut;

    private void makeBuyOutController(ActionEvent actionEvent) {
        this.toBuyOut = buyOutChoice.getValue();
    }

    @FXML
    protected void makeBuyOut(){
        this.toBuyOut.makeBuyOut();
    }

    @FXML
    protected void displayingTypeAssets() {
        menu.setText("Assets");
        this.showAssets();
    }

    @FXML
    protected void displayingTypeMarkets() {
        menu.setText("Markets");
        this.showMarkets();
    }

    @FXML
    protected void displayingTypeIndexes() {
        menu.setText("Indexes");
        this.showIndexes();
    }

    @FXML
    protected void displayingTypeInvestors() {
        menu.setText("Investors");
        this.showInvestors();
    }

    @FXML
    protected void displayingTypeFunds() {
        menu.setText("Funds");
        this.showFunds();
    }

    @FXML
    private TextArea appOut;

    @FXML
    protected void createStockMarket() {
        StockMarket m = new StockMarket("StockMarket"+(world.getNumberOfStockMarkets()+1), randomFloat(0.01f, 0.5f),
                countries_names.get(randomInt(1,196)), cities.get(world.getNumberOfStockMarkets()), cities.get(world.getNumberOfStockMarkets())+randomInt(1,100));
        world.setNumberOfStockMarkets(world.getNumberOfStockMarkets()+1);
        appOut.appendText("New stock market: "+ m.getName()+"\n");
        world.addStockMarkets(m);
        this.displayingTypeMarkets();
    }

    @FXML
    protected void createCommodityMarket() {
        CommodityMarket m = new CommodityMarket("CommodityMarket"+(world.getNumberOfCommodityMarkets()+1), randomFloat(0.01f, 0.5f),
                countries_names.get(randomInt(1,196)), cities.get(world.getNumberOfCommodityMarkets()), cities.get(world.getNumberOfCommodityMarkets())+randomInt(1,100));
        world.setNumberOfCommodityMarkets(world.getNumberOfCommodityMarkets()+1);
        appOut.appendText("New commodity market: " + m.getName()+"\n");
        world.addCommodityMarkets(m);
        this.displayingTypeMarkets();
    }

    @FXML
    protected void createCurrencyMarket() {
        CurrencyMarket m = new CurrencyMarket("CurrencyMarket"+(world.getNumberOfCurrencyMarkets()+1), randomFloat(0.01f, 0.5f),
                countries_names.get(randomInt(1,196)), cities.get(world.getNumberOfCurrencyMarkets()), cities.get(world.getNumberOfCurrencyMarkets())+randomInt(1,100));
        world.setNumberOfCurrencyMarkets(world.getNumberOfCurrencyMarkets()+1);
        appOut.appendText("New currency market: "+ m.getName()+"\n");
        world.addCurrencyMarkets(m);
        this.displayingTypeMarkets();
    }

    @FXML
    protected void createCompany() {
        if(world.getNumberOfCompanies() < 1146){
            Company c = new Company(company_names.get(world.getNumberOfCompanies()));
            world.setNumberOfCompanies(world.getNumberOfCompanies()+1);
            appOut.appendText("New company: "+ c.getName()+"\n");
            world.addCompanies(c);
            if((world.getNumberOfCurrencies()+ world.getNumberOfCommodities()+ world.getNumberOfCompanies())%4 == 0 ){
                this.createInvestor();
            }
            if((world.getNumberOfCurrencies()+ world.getNumberOfCommodities()+ world.getNumberOfCompanies())%8 == 0 ){
                this.createFund();
            }
            this.displayingTypeAssets();
            (new Thread(c)).start();
            this.buyOutChoice.getItems().add(c);
        }
        else{
            appOut.appendText("The world of companies is full... New company creation denied");
        }
    }

    @FXML
    protected void createCurrency() {
        if(world.getNumberOfCurrencies() < 190){
            Currency c = new Currency(currencies_names.get(world.getNumberOfCurrencies()));
            world.setNumberOfCurrencies(world.getNumberOfCurrencies()+1);
            appOut.appendText("New currency: "+ c.getName()+"\n");
            world.addCurrencies(c);
            if((world.getNumberOfCurrencies()+ world.getNumberOfCommodities()+ world.getNumberOfCompanies())%4 == 0 ){
                this.createInvestor();
            }
            if((world.getNumberOfCurrencies()+ world.getNumberOfCommodities()+ world.getNumberOfCompanies())%8 == 0 ){
                this.createFund();
            }
            this.displayingTypeAssets();
        }
        else{
            appOut.appendText("The world of currencies is full... New currency creation denied \n");
        }
    }

    @FXML
    protected void createCommodity() {
        if(world.getNumberOfCommodities() < 50 && world.getNumberOfCurrencies() > 0){
            Commodity c = new Commodity(commodities_names.get(world.getNumberOfCommodities()));
            world.setNumberOfCommodities(world.getNumberOfCommodities()+1);
            appOut.appendText("New commodity: "+ c.getName()+"\n");
            world.addCommodities(c);
            if((world.getNumberOfCurrencies()+ world.getNumberOfCommodities()+ world.getNumberOfCompanies())%4 == 0 ){
                this.createInvestor();
            }
            if((world.getNumberOfCurrencies()+ world.getNumberOfCommodities()+ world.getNumberOfCompanies())%8 == 0 ){
                this.createFund();
            }
            this.displayingTypeAssets();
        }
        else if (world.getNumberOfCurrencies() <= 0){
            appOut.appendText("Create currency before creating a commodity! \n");
        }
        else{
            appOut.appendText("The world of commodities is full... New currency creation denied \n");
        }
    }


    @FXML
    protected void createMarketIndex() {
        if(world.getNumberOfMarketIndexes() <= 54){
            MarketIndex mi = new MarketIndex(indexes_names.get(world.getNumberOfMarketIndexes()));
            world.setNumberOfMarketIndexes(world.getNumberOfMarketIndexes()+1);
            appOut.appendText("New market index: " + mi.getName()+"\n");
            world.addMarketIndexes(mi);
            this.displayingTypeIndexes();
        }
        else{
            appOut.appendText("The world of market indexes is full... New index creation denied\n");
        }
    }

    @FXML
    protected void createInvestor() {
        if(world.getNumberOfInvestors() <= 54){
            Investor i = new Investor(names.get(world.getNumberOfInvestors()));
            world.setNumberOfInvestors(world.getNumberOfInvestors()+1);
            appOut.appendText("New investor: " + i.getInvestorName()+"\n");
            world.addInvestors(i);
            this.displayingTypeInvestors();
            i.start();
        }
        else{
            appOut.appendText("The world of investors is full... New investor creation denied\n");
        }
    }

    @FXML
    protected void createFund() {
        if(world.getNumberOfFunds() <= 54){
            InvestmentFund f = new InvestmentFund(funds_names.get(world.getNumberOfFunds()));
            world.setNumberOfFunds(world.getNumberOfFunds()+1);
            appOut.appendText("New investment fund: " + f.getFundName()+"\n");
            world.addFunds(f);
            this.displayingTypeFunds();
            (new Thread(f)).start();
        }
        else{
            appOut.appendText("The world of funds is full... New fund creation denied\n");
        }
    }

    @FXML
    private TextFlow tx;

    @FXML
    private TextArea propertyText;

    @FXML
    protected void showAssets(){
        tx.getChildren().clear();
        lineChart.getData().clear();
        String[] names = {"COMPANIES:\n", "\n\n\nCURRENCIES:\n", "\n\n\nCOMMODITIES:\n"};
        List<Hyperlink> companyLinks = new ArrayList<>();
        List<Hyperlink> currencyLinks = new ArrayList<>();
        List<Hyperlink> commodityLinks = new ArrayList<>();
        for(Company c: world.getCompanies()){
            Hyperlink tmp = new Hyperlink((c.getName()));
            tmp.setOnAction(e -> {
                propertyText.clear();
                propertyText.appendText(c.toStringProperties());
                this.showChart(c);
            });
            companyLinks.add(tmp);
        }

        for(Currency c: world.getCurrencies()){
            Hyperlink tmp = new Hyperlink((c.getName()));
            tmp.setOnAction(e -> {
                propertyText.clear();
                propertyText.appendText(c.toString());
            });
            currencyLinks.add(tmp);
        }

        for(Commodity c: world.getCommodities()){
            Hyperlink tmp = new Hyperlink((c.getName()));
            tmp.setOnAction(e -> {
                propertyText.clear();
                propertyText.appendText(c.toString());
                this.showChart(c);
            });
            commodityLinks.add(tmp);
        }


        addingText(names, companyLinks, currencyLinks, commodityLinks);
    }

    private void addingText(String[] names, List<Hyperlink> companyLinks, List<Hyperlink> currencyLinks, List<Hyperlink> commodityLinks) {
        tx.getChildren().add(new Text(names[0]));
        for(Hyperlink h: companyLinks){
            tx.getChildren().add(h);
            tx.getChildren().add(new Text("\n"));
        }
        tx.getChildren().add(new Text(names[1]));
        for(Hyperlink h: currencyLinks){
            tx.getChildren().add(h);
            tx.getChildren().add(new Text("\n"));
        }
        tx.getChildren().add(new Text(names[2]));
        for(Hyperlink h: commodityLinks){
            tx.getChildren().add(h);
            tx.getChildren().add(new Text("\n"));
        }
    }
    @FXML
    protected void showMarkets(){
        tx.getChildren().clear();
        lineChart.getData().clear();
        String[] names = {"STOCK MARKETS:\n", "\n\n\nCURRENCY MARKETS:\n", "\n\n\nCOMMODITY MARKETS:\n"};
        List<Hyperlink> stockMarketsLinks = new ArrayList<>();
        List<Hyperlink> currencyMarketsLinks = new ArrayList<>();
        List<Hyperlink> commodityMarketsLinks = new ArrayList<>();

        for(StockMarket m: world.getStockMarkets()){
            Hyperlink tmp = new Hyperlink((m.getName()));
            tmp.setOnAction(e -> {
                propertyText.clear();
                propertyText.appendText(m.toString());
            });
            stockMarketsLinks.add(tmp);
        }

        for(CurrencyMarket m: world.getCurrencyMarkets()){
            Hyperlink tmp = new Hyperlink((m.getName()));
            tmp.setOnAction(e -> {
                propertyText.clear();
                propertyText.appendText(m.toString());
            });
            currencyMarketsLinks.add(tmp);
        }

        for(CommodityMarket m: world.getCommodityMarkets()){
            Hyperlink tmp = new Hyperlink((m.getName()));
            tmp.setOnAction(e -> {
                propertyText.clear();
                propertyText.appendText(m.toString());
            });
            commodityMarketsLinks.add(tmp);
        }

        addingText(names, stockMarketsLinks, currencyMarketsLinks, commodityMarketsLinks);
    }
    @FXML
    protected void showIndexes(){
        tx.getChildren().clear();
        lineChart.getData().clear();
        List<Hyperlink> indexLinks = new ArrayList<>();
        for(MarketIndex mi: world.getMarketIndexes()){
            Hyperlink tmp = new Hyperlink((mi.getName()));
            tmp.setOnAction(e -> {
                propertyText.clear();
                propertyText.appendText(mi.toString());
            });
            indexLinks.add(tmp);
        }
        tx.getChildren().add(new Text("MARKET INDEXES:\n"));
        for(Hyperlink h: indexLinks){
            tx.getChildren().add(h);
            tx.getChildren().add(new Text("\n"));
        }
    }
    @FXML
    protected void showInvestors(){
        tx.getChildren().clear();
        lineChart.getData().clear();
        List<Hyperlink> investorsLinks = new ArrayList<>();
        for(Investor i: world.getInvestors()){
            Hyperlink tmp = new Hyperlink((i.getInvestorName()));
            tmp.setOnAction(e -> {
                propertyText.clear();
                propertyText.appendText(i.toString());
            });
            investorsLinks.add(tmp);
        }
        tx.getChildren().add(new Text("INVESTORS:\n"));
        for(Hyperlink h: investorsLinks){
            tx.getChildren().add(h);
            tx.getChildren().add(new Text("\n"));
        }
    }
    @FXML
    protected void showFunds(){
        tx.getChildren().clear();
        lineChart.getData().clear();
        List<Hyperlink> fundsLinks = new ArrayList<>();
        for(InvestmentFund i: world.getFunds()){
            Hyperlink tmp = new Hyperlink((i.getFundName()));
            tmp.setOnAction(e -> {
                propertyText.clear();
                propertyText.appendText(i.toString());
            });
            fundsLinks.add(tmp);
        }
        tx.getChildren().add(new Text("INVESTMENTS FUNDS:\n"));
        for(Hyperlink h: fundsLinks){
            tx.getChildren().add(h);
            tx.getChildren().add(new Text("\n"));
        }
    }

    private final List<Asset> onChart = new ArrayList<>();

    @FXML
    protected void showChart(Asset asset) {
        if(!onChart.contains(asset)) {
            onChart.add(asset);
            lineChart.getData().clear();
            for(Asset a: onChart){
                XYChart.Series<String, Number> data = new XYChart.Series<>();
                data.setName(a.getName());

                if (a instanceof Company) {
                    for (Pair<LocalTime, Float> p : ((Company) a).getPricesList()) {
                        data.getData().add(new XYChart.Data<>(p.getKey().toString(), p.getValue()));
                    }
                }
                if (a instanceof Commodity) {
                    for (Pair<LocalTime, Float> p : ((Commodity) a).getPricesList()) {
                        data.getData().add(new XYChart.Data<>(p.getKey().toString(), p.getValue()));
                    }
                }
                lineChart.setAnimated(false);
                lineChart.getData().add(data);
            }
        }
    }

    @FXML
    private Button clearButton;

    @FXML
    protected void clearChart(){
        lineChart.getData().clear();
        onChart.clear();
    }
}