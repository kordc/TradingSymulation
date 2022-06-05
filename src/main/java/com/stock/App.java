package com.stock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Main class used as a start project
 * Creates the most important containers, class World and initialize the GUI
 */
public class App extends Application {
    static World world = new World();
    static List<String> company_names = new ArrayList<>();
    static List<String> currencies_names = new ArrayList<>();
    static List<String> countries_names = new ArrayList<>();
    static List<String> commodities_names = new ArrayList<>();
    static List<String> commodities_units = new ArrayList<>();
    static List<String> cities = new ArrayList<>();
    static List<String> indexes_names = new ArrayList<>();
    static List<String> names = new ArrayList<>();
    static List<String> funds_names = new ArrayList<>();

    /**
     * Simple implementation of the random function for integers from the given range
     * @param min lower bound of the given range
     * @param max upper bound of the given range
     * @return Random Integer
     */
    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    /**
     * Simple implementation of the random function for floats from the given bound
     * @param min lower bound of the given range
     * @param max upper bound of the given range
     * @return Random Float
     */
    public static float randomFloat(float min, float max) {
        Random random = new Random();
        return random.nextFloat(max - min) + min;
    }

    /**
     * Start function initializing all important properties of GUI implementation in FXML
     * @param stage Stage object from FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Trading simulation");
        stage.getIcons().add(new Image("/twarz.png"));
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
    }

    /**
     * Main function with invocation of a reader from files and GUI application run
     */
    public static void main(String[] args) throws IOException {
        Read r = new Read();
        r.readFiles();
        launch();
    }
}