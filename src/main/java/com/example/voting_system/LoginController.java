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

public class LoginController {
    @FXML
    private TextField Username;
    @FXML
    private PasswordField Pass;
    @FXML
    private Button Signup;
    @FXML
    private Button Admin;
    @FXML
    private Label alert;

    private  Button vote;
    private Parent root;
    private Stage stage;
    private Scene scene;


    public void passCheck() {
        Password password = new Password(Username.getText(), Pass.getText());
        if (password.passcheck(password)) {
            System.out.println("Password correct");
        } else {

            alert.setText("Login failed.  Try again or sigup");


        }

    }

    public void openinside(ActionEvent event) throws IOException, SQLException {
        Password password = new Password(Username.getText(), Pass.getText());
        if (password.passcheck(password)) {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Voting", "yoga2405", "Rajanr*2405");
            Statement stmt = con.createStatement();

            String query = String.format("select isvote,age from users where username='%s'",Username.getText());
            ResultSet set = stmt.executeQuery(query);
            set.next();
            if(set.getInt(1)==1)
            {
                alert.setText("You are already voted");
                return;
            }
            else if(set.getInt(2)<18)
            {
                alert.setText("You are not eligible for voting");
                return;
            }
            try {
                System.out.println("Correct Password");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Voter.fxml"));

                root = loader.load();

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();




            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println(e.getMessage());
                System.out.println(e.getClass());
            }
            VoteController controller = new VoteController();
            openVoter(password.Username);

        } else {
            alert.setText("Login failed.  Try Again or signup");
        }
    }
    public void openVoter(String username)
    {
        VoteController.user=username;


    }

    public void openAdmin(ActionEvent event) {
        //Password password = new Password(Username.getText(), Pass.getText());
        //if (password.passcheck(password)) {
        try {
            System.out.println("Correct Password");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));

            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


            System.out.println("Constructor called");
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            System.out.println(e.getClass());
        }

    }

    public void openSignup(ActionEvent event) {
        //Password password = new Password(Username.getText(), Pass.getText());
        //if (password.passcheck(password)) {
        try {
            System.out.println("Correct Password");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));

            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


            System.out.println("Constructor called");
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            System.out.println(e.getClass());
        }
    }
}
