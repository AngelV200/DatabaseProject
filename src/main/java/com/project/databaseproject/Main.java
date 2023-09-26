package com.project.databaseproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(RegisterController.class.getResource("Register.fxml"));
        Parent user = loader.load();

        RegisterController registerController = loader.getController();
        registerController.set(new Database(), primaryStage);

        Scene scene = new Scene(user);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Structure is src -> DatabaseDesign and inside here is Controllers package, then FXML package, then Project Package where main is at