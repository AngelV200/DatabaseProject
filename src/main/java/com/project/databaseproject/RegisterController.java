package com.project.databaseproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class RegisterController {
    private Database database;
    @FXML
    private TextField usernameField, passwordField, firstNameField, lastNameField, emailField;

    public void set(Database database) {
        this.database = database;
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
}
