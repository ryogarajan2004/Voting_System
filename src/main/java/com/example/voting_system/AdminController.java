package com.example.voting_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AdminController {
    @FXML
    private TextField Username;
    @FXML
    private TextField Password;
    @FXML
    private Button Login;
    @FXML
    private Label alert;

    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    private Button Exit;

    public void adminLogin(ActionEvent event) {
        String user = Username.getText();
        String pass = Password.getText();
        boolean status = false;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Voting", "yoga2405", "Rajanr*2405");
            Statement stmt = connection.createStatement();
            String query;
            query = String.format("select COUNT(*) AS password_match from users where username='%s' and pass=PASSWORD('%s') and isadmin='1'", user, pass);
            ResultSet set = stmt.executeQuery(query);

            // System.out.println(set.getInt(0));

            //System.out.println(set.first());
            while (set.next()) {
                System.out.println(set.getInt(1));
                if (set.getInt(1) == 1) {
                    status = true;

                    System.out.println("Login as admin");

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (status) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ControlPanel.fxml"));

                root = loader.load();

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        } else {
            alert.setText("You are not a admin");
            System.out.println("You are not a admin");
        }
    }

    public void Logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
