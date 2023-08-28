package com.example.voting_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CandidateManager implements Initializable {

    @FXML
    private Button delete;
    @FXML
    private Button create;
    @FXML
    private Button Exit;
    Parent root;
    Stage stage;
    Scene scene;


    private final ArrayList<String> list = new ArrayList<>();
    @FXML
    private TextField username;

    @FXML
    ChoiceBox<String> candidate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCandidate();
        for (String temp : list) {
            candidate.getItems().add(temp);

        }
        System.out.println("Loaded successfully");
    }

    public void delete(ActionEvent event) {
        try {
            String conString = "jdbc:mysql://127.0.0.1:3306/Voting";
            Connection con = DriverManager.getConnection(conString, "yoga2405", "Rajanr*2405");
            Statement stmt = con.createStatement();


            String query = String.format("DELETE FROM candidate where cname='%s'", candidate.getValue());
            stmt.execute(query);

            System.out.println("Query Executed");

            con.close();
            try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("Candidate.fxml"));
                root = loader.load();

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                System.out.println(e);
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //System.out.println(clist.toString());
    }

    public void createCandidate(ActionEvent event) {


        System.out.println("Inside create candidate");
        try {
            String conString = "jdbc:mysql://127.0.0.1:3306/Voting";
            Connection con = DriverManager.getConnection(conString, "yoga2405", "Rajanr*2405");
            Statement stmt = con.createStatement();

            String query = String.format("select COUNT(cname)  from candidate where cname='%s'", username.getText());
            ResultSet res = stmt.executeQuery(query);
            System.out.println("Query Executed");
            res.next();
            if (res.getInt(1) == 1) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Candidate.fxml"));
                    root = loader.load();

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println(e);
                    System.out.println(e.getMessage());
                }
            } else {
                query = String.format("INSERT INTO  candidate(cname,vote) VALUES('%s',0)", username.getText());
                stmt.execute(query);
                updateCandidate();
                con.close();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Candidate.fxml"));
                    root = loader.load();

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    return;
                } catch (IOException e) {
                    System.out.println(e);
                    System.out.println(e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        }

        //System.out.println(clist.toString());
    }

    void updateCandidate() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Candidate.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("File not found");
        }

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
            System.out.println(list);

            System.out.println(list);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //System.out.println(clist.toString());
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
