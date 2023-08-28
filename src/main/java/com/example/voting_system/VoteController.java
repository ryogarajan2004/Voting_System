package com.example.voting_system;

import com.mysql.cj.util.Base64Decoder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VoteController implements Initializable {
    static String user ;
    String pass;
    Connection con;
    Statement stmt;
    @FXML
    private Button vote;
    @FXML
            private Button exit;

    Parent root;
    Stage stage;
    Scene scene;


    private final ArrayList<String> list = new ArrayList<>();
    @FXML
    private Label username;

    @FXML
    ChoiceBox<String> candidate;


    void loadpassword(String password1) {
        System.out.println("Inside loadpassword");
        System.out.println(password1);
        user = password1;
        //this.username.setText(password1);
        System.out.println("Password loaded");
        loadCandidate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCandidate();
        for (String temp : list) {
            candidate.getItems().add(temp);

        }
        System.out.println("Loaded successfully");
    }

    void loadCandidate() {
        try {
            String conString = "jdbc:mysql://127.0.0.1:3306/Voting";
            Connection con = DriverManager.getConnection(conString, "yoga2405", "Rajanr*2405");
            Statement stmt = con.createStatement();


            String query = "select * from candidate";
            ResultSet res = stmt.executeQuery(query);
            System.out.println("Query Executed");
            while (res.next()) {
                list.add(res.getString(1));
                System.out.println(res.getString(1));

            }
            System.out.println(list.toString());

            System.out.println(list.toString());
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //System.out.println(clist.toString());
    }


    public void addVote(ActionEvent event) {
        String name = candidate.getValue();
        try {
            if (!checkvote()) {
                Connection con;
                Statement stmt;
                String conString = "jdbc:mysql://127.0.0.1:3306/Voting";
                con = DriverManager.getConnection(conString, "yoga2405", "Rajanr*2405");
                stmt = con.createStatement();
                String query = String.format("UPDATE candidate set vote=vote+1 where cname='%s'", name);
                stmt.execute(query);
                query = String.format("UPDATE users set isvote=1 where username='%s'", user);
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


            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }


    }

    boolean checkvote() {
        ResultSet res = null;
        boolean status = false;

        try {

            String conString = "jdbc:mysql://127.0.0.1:3306/Voting";
            Connection con = DriverManager.getConnection(conString, "yoga2405", "Rajanr*2405");
            Statement stmt = con.createStatement();
            String query = String.format("select isvote from users where username='%s'", user);
            res = stmt.executeQuery(query);
            res.next();
            status = (Integer.parseInt(res.getString("isvote")) == 1);
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }

        System.out.println(status);
        return status;
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
