package com.project.databaseproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Database database;
    private Stage primaryStage;
    @FXML
    private TextField usernameField, passwordField;

    public void set(Database database, Stage primaryStage) {
        this.database = database;
        this.primaryStage = primaryStage;
    }


    private void alertBox(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Read");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    @FXML
    private void sC(ActionEvent e) throws IOException {
        String u = usernameField.getText();
        String p = passwordField.getText();

        if (u.trim().isEmpty() || p.trim().isEmpty()) {
            alertBox("Please Fill Out All Fields");
            return;
        }

        int key = database.login(u, p);
        if (key == 1) {
            alertBox("user name does not exist");
            return;
        }
        else if (key == 2) {
            alertBox("password is wrong");
            return;
        }

        alertBox("login success");
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("Store.fxml"));
        Parent user = loader.load();

        StoreController storeController = loader.getController();
        storeController.set(new Database(), primaryStage, usernameField.getText());

        Scene scene = new Scene(user);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
