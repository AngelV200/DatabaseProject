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

public class RegisterController {
    private Database database;
    private Stage primaryStage;
    @FXML
    private TextField usernameField, passwordField, firstNameField, lastNameField, emailField;

    public void set(Database database, Stage primaryStage) {
        this.database = database;
        this.primaryStage = primaryStage;
    }

    @FXML
    private void register() {
        String u = usernameField.getText();
        String p = passwordField.getText();
        String fN = firstNameField.getText();
        String lN = lastNameField.getText();
        String e = emailField.getText();

        if (u.trim().isEmpty() || p.trim().isEmpty() || fN.trim().isEmpty() || lN.trim().isEmpty() || e.trim().isEmpty()) {
            alertBox("Please Fill Out All Fields");
            return;
        }

        int caseNum = database.insertUserInfo(u, p, fN, lN, e);

        if (caseNum == 0)
            alertBox("Successfully Registered!");
        else if (caseNum == 1)
            alertBox(("Username Already Exists. Please Choose Another One."));
        else if (caseNum == 2)
            alertBox(("Email Already Exists. Please Choose Another One."));
        else
            alertBox("Data In The Table Was Not Inserted Successfully.");

        // These are the cases based on the updated queries.
    }


    private void alertBox(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Read");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    @FXML
    private void switchToLogin(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("Login.fxml"));
        Parent user = loader.load();

        LoginController loginController = loader.getController();
        loginController.set(new Database(), primaryStage);

        Scene scene = new Scene(user);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
