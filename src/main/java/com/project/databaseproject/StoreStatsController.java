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
    ListView<String> one, two;
    @FXML
    TextField threeTF, c1, c2;


    @FXML
    public void initialize() {

    }


    public void set(Database database, Stage primaryStage, String username) {
        this.database = database;
        this.primaryStage = primaryStage;
        this.username = username;
        oneFunction();
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
