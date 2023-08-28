package com.example.voting_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Voting System");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("Exception found");
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
    }
}
