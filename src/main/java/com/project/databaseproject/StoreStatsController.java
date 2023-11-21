package com.project.databaseproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StoreStatsController {
    private Database database;
    private Stage primaryStage;
    String username;
    @FXML
    ListView<String> one, two, three, four, five, six, seven, eight, nine, ten;
    @FXML
    TextField c1, c2, threeTF, fourTF, u1, u2;


    @FXML
    public void initialize() {

    }


    public void set(Database database, Stage primaryStage, String username) {
        this.database = database;
        this.primaryStage = primaryStage;
        this.username = username;
        oneFunction();
        sixFunction();
        sevenFunction();
        eightFunction();
        nineFunction();
        tenFunction();
    }


    private void oneFunction() {
        List<String> items = database.getMostExpensiveItemsByCategory();

        one.getItems().setAll(items);
    }

    @FXML
    private void twoFunction() {
        List<String> items = database.getUsersWithMultipleItemsOnSameDay(c1.getText(), c2.getText());

        two.getItems().setAll(items);
    }

    @FXML
    private void threeFunction() {
        List<String> items = database.getUsersWithItemsOnlyExcellentOrGood(threeTF.getText());

        three.getItems().setAll(items);
    }

    @FXML
    private void fourFunction() {
        List<String> items = database.getUsersWithMostItemsOnDate(fourTF.getText());

        four.getItems().setAll(items);
    }

    @FXML
    private void fiveFunction() {
        List<String> items = database.getCommonFavorites(u1.getText(), u2.getText());

        five.getItems().setAll(items);
    }

    @FXML
    private void sixFunction() {
        List<String> items = database.getUsersWithNoExcellentItems();

        six.getItems().setAll(items);
    }

    @FXML
    private void sevenFunction() {
        List<String> items = database.getUsersWithoutPoorReviews();

        seven.getItems().setAll(items);
    }

    @FXML
    private void eightFunction() {
        List<String> items = database.getUsersWithOnlyPoorReviews();

        eight.getItems().setAll(items);
    }

    @FXML
    private void nineFunction() {
        List<String> items = database.getUsersWithNoPoorReviews();

        nine.getItems().setAll(items);
    }

    @FXML
    private void tenFunction() {
        List<String> items = database.getUserPairsWithExcellentReviews();

        ten.getItems().setAll(items);
    }


    @FXML
    private void switchToStore(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("Store.fxml"));
        Parent user = loader.load();

        StoreController store = loader.getController();
        store.set(database, primaryStage, username);

        Scene scene = new Scene(user);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
