package com.project.databaseproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load your FXML file
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Register.fxml"));
        Parent user = loader.load();

        RegisterController registerController = loader.getController();
        registerController.set(new Database(), primaryStage);

        // Create a ScrollPane and set its content
        ScrollPane scrollPane = new ScrollPane(user);
        scrollPane.setFitToWidth(true); // Allow horizontal scrolling
        scrollPane.setFitToHeight(true); // Allow vertical scrolling
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Show horizontal scrollbar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Show vertical scrollbar

        // Create the Scene with the ScrollPane
        Scene scene = new Scene(scrollPane);

        // Set the Scene to the primary stage
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true); // Optional: Set the stage to full screen
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Structure is src -> DatabaseDesign and inside here is Controllers package, then FXML package, then Project Package where main is at