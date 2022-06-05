package com.stock;

import static com.stock.App.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A class which performs reading from files to name every objects
 */
public class Read {
    public Read(){}

    private void readOneFile(String location, List<String> list){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(location));
            String line = reader.readLine();
            while (line != null) {
                list.add(line);
                line = reader.readLine();
            }
            Collections.shuffle(list);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A function which performs reading from files to name every objects
     */
    public void readFiles() {
        this.readOneFile("src/main/resources/company_names.txt", company_names);
        this.readOneFile("src/main/resources/currencies.txt", currencies_names);
        this.readOneFile("src/main/resources/countries.txt", countries_names);
        this.readOneFile("src/main/resources/commodities.txt", commodities_names);
        this.readOneFile("src/main/resources/units.txt", commodities_units);
        this.readOneFile("src/main/resources/cities.txt", cities);
        this.readOneFile("src/main/resources/indexes.txt", indexes_names);
        this.readOneFile("src/main/resources/names.txt", names);
        this.readOneFile("src/main/resources/funds.txt", funds_names);
    }
}
