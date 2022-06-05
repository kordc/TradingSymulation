# Java trading simulator
  
  *24.01.2021 - Karol Cyganik, AI 2nd year, 148250*
  
  This is a Java trading simulator - a final project realized for Object-Oriented Programming classes at Poznan University of Technology (AI major). 
  


  **Main description**

This is a program that can simulate trading on markets. Three possible markets are available (each can be created on a user's command):
* Stock market - trading company shares
* Currency market - trading between currencies
* Commodity market - trading commodity units

As visible above, three main types of assets are available (each can be created on a user's command):
* Company shares
* Commodity units
* Currencies

To trade, we need some more stuff:
* Investors (created automatically, proportionally to the assets)
* Investment funds (created automatically, proportionally to the assets)
* Market Index (created on a user's command)
   
**How does it work**

On a application start one investor, investment fund, market index, market and asset of each type are created. Then, the trading begins. 
During the trading, you can (and even should, to make it more interesting) create random assets, markets, and optionally market indexes. 

To create something you have to go to the top menu bar, click on actions, and select the name you want to create. 

**Special functions**
* Bear-bull ratio - using a slider you can set the behavior of the investors (more info https://www.investopedia.com/terms/b/bullbearratio.asp)
* Transaction probability - using a slider you can set the probability of doing some transaction by the investor, each one will decide every 2 seconds
* Making a buy-out - you can select the % of the company's total shares amount to make the operation, then, you can select the company from the rollable list, at the end, you just have to click on the button

**Listing items, their properties, and plots**

For each type of storage item, you can view the list of them. To do it go to the "List of items" section and select the group you want to see.

On the list you can select the item, then, in the "Properties" section you will see all the properties of the object. Moreover, if the selected item is a company or a commodity, the plot will be shown.

You can plot more than one company at a time, just click on another item once one plot is already visible. To clear the plot just click the "Clear" button at the bottom of the line chart section. 

**Notes**
Investors and Investment funds can be also created manually, but it's not recommended


**Downloading and running the Stocl.jar file**
To run the \*.jar file you should have the file in the same folder, as *scr* folder. 
Then, running it using console requires belows command:
>java --module-path "%JAVAFC_LIB_PATH%"  --add-modules javafx.controls,javafx.fxml -jar Stock.jar App

For example:
>java --module-path "C:\Program Files\Java\javafx-sdk-17.0.0.1\lib"  --add-modules javafx.controls,javafx.fxml -jar Stock.jar App
