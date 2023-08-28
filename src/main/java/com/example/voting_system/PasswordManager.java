package com.example.voting_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class PasswordManager {

    @FXML
    private TextField username;
    @FXML
    private PasswordField pass0;
    @FXML
    private PasswordField pass1;
    @FXML
    private PasswordField pass2;

    @FXML
    private Button change;
    @FXML
    private Label alert;
    @FXML
    protected Button Exit;

    Parent root;
    Stage stage;
    Scene scene;

    public void changepassword(ActionEvent event) {
        Password password = new Password(username.getText(), pass0.getText());
        if (password.passcheck(password)) {
            if (Objects.equals(pass1.getText(), pass2.getText())) {
                System.out.println("Inside Changing function");
                String conString = "jdbc:mysql://127.0.0.1:3306/Voting";
                try {
                    Connection con = DriverManager.getConnection(conString, "yoga2405", "Rajanr*2405");
                    Statement stmt = con.createStatement();
                    String query = String.format("UPDATE users SET pass=PASSWORD('%s') where username='%s'", pass1.getText(), username.getText());
                    stmt.execute(query);
                    con.close();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));

                        root = loader.load();

                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(e.getMessage());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } else if (pass1.getText() != pass2.getText()) {
                alert.setText("Password mismatch");
            }

        } else {
            alert.setText("Wrong password");

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
