package com.project.databaseproject;

import javafx.stage.Stage;

public class LoginController {
    private Database database;
    private Stage primaryStage;

    public void set(Database database, Stage primaryStage) {
        this.database = database;
        this.primaryStage = primaryStage;
    }
}
