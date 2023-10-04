package com.project.databaseproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Database database;
    private Stage primaryStage;

    public void set(Database database, Stage primaryStage) {
        this.database = database;
        this.primaryStage = primaryStage;
    }

    @FXML
    private void switchToRegister(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("Register.fxml"));
        Parent user = loader.load();

        RegisterController registerController = loader.getController();
        registerController.set(new Database(), primaryStage);

        Scene scene = new Scene(user);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
