package com.project.databaseproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    private TextField usernameField, passwordField, firstNameField, lastNameField, emailField;


    @FXML
    private void register() {
        if (usernameField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty() || firstNameField.getText().trim().isEmpty() || lastNameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
            alertBox("Please Fill Out All Fields");
            return;
        }

        alertBox("Successfully Registered!");
    }


    private void alertBox(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Read");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
}
