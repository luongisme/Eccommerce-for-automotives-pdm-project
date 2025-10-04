package com.example.pdm_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(fxml.load(), 960, 640);
        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
        stage.setTitle("AutoParts Pro — Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}
