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

public class StoreController {
    @FXML
    ListView<String> itemList;
    private Database database;
    private Stage primaryStage;
    @FXML
    ComboBox box;
    @FXML
    TextArea review;
    @FXML
    TextField searchField;
    @FXML
    TextField t;
    @FXML
    TextField d;
    @FXML
    TextField c;
    @FXML
    TextField p;
    String username;
    String lineListView;



    @FXML
    public void initialize() {
        ObservableList<String> ratings = FXCollections.observableArrayList("excellent", "good", "fair", "poor");
        box.setItems(ratings);
    }

    @FXML
    private void find(KeyEvent event) { // uses search field
        String selectedCategory = searchField.getText();

        if (selectedCategory != null) {
            List<String> items = database.getAllItemsByCategory(selectedCategory);

            itemList.getItems().setAll(items); // Display the items in the itemList ListView
        }
    }

    public void set(Database database, Stage primaryStage, String username) {
        this.database = database;
        this.primaryStage = primaryStage;
        this.username = username;
    }


    @FXML
    void insert() { // inserts new item
        database.insertItem(t.getText(),d.getText(),c.getText(),Integer.parseInt(p.getText()), username);
    }


    @FXML
    void comCall() { // called from listview clicked
        String selectedText = itemList.getSelectionModel().getSelectedItem();
        if (selectedText != null) {
            lineListView = selectedText;
        }
    }

    @FXML
    private void submit(ActionEvent e) throws IOException {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String[] att = lineListView.split("-");
        //System.out.println(utilDate + "-" + sqlDate);
        database.insertReview(sqlDate, (String)box.getValue(), review.getText(), database.getItemIdByAttributes(att[0], att[1], searchField.getText(), Integer.parseInt(att[2]), username), username);
    }

    @FXML
    private void switchToStats(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("StoreStats.fxml"));
        Parent user = loader.load();

        StoreStatsController storeStatsController = loader.getController();
        storeStatsController.set(database, primaryStage, username);

        Scene scene = new Scene(user);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
