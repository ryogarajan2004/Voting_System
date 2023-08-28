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
import java.sql.*;
import java.util.Objects;

public class Signup {

    @FXML
    private TextField username;
    @FXML
    private TextField name;
    @FXML
    private PasswordField pass1;
    @FXML
    private PasswordField pass2;
    @FXML
    private TextField mobile;
    @FXML
    private TextField age;
    @FXML
    private Button signup;
    @FXML
    private Label alert;
    @FXML
    private Button Exit;

    Parent root;
    Stage stage;
    Scene scene;

    public void signup(ActionEvent event) {
        String user = username.getText();
        ResultSet set;
        Statement stmt;
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/Voting", "yoga2405", "Rajanr*2405");
            stmt = con.createStatement();

            String query = String.format("select COUNT(*) AS password_match from users where username='%s'", user);
            set = stmt.executeQuery(query);
            set.next();

            if (set.getInt(1) == 1) {
                alert.setText("User already exist");
                return;
            } else {
                if (!Objects.equals(pass1.getText(), pass2.getText())) {
                    alert.setText("Password does not match");

                }else {
                query = String.format("INSERT INTO users(username,pass,fname,mobile,age,isvote,isadmin) VALUES('%s',PASSWORD('%s'),'%s','%s','%s',0,0)", user, pass1.getText(), name.getText(), mobile.getText(), age.getText());
                stmt.execute(query);
                alert.setText("New user created");
                System.out.println("User Inserted");

                return;
            }}
        } catch (SQLException e) {
            System.out.println(e.toString());
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
