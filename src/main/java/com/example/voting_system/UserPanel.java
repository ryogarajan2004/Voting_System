package com.example.voting_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class UserPanel {
    @FXML
    private TextField searchfield;
    @FXML
    private Button search;

    @FXML
    private TextField namefield;
    @FXML
    private TextField mobile;
    @FXML
    private TextField age;
    @FXML
    private ToggleButton admin;
    @FXML
    private Button changepassword;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private Button Exit;

    Parent root;
    Scene scene;
    Stage stage;

    public void change(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));

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

    public void searchuser(ActionEvent event) {
        String username = searchfield.getText();
        ResultSet set;
        if (username != null) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Voting", "yoga2405", "Rajanr*2405");
                Statement stmt = con.createStatement();

                String query = String.format("select fname, mobile,age,isadmin from users where username='%s'", username);

                set = stmt.executeQuery(query);
                set.next();
                System.out.println(1 + set.getString(1));
                System.out.println(2 + set.getString(2));
                System.out.println(3 + set.getString(3));
                System.out.println(4 + set.getString(4));

                namefield.setText(set.getString(1));

                age.setText(set.getString(2));
                mobile.setText(set.getString(3).toString());
                admin.setSelected((set.getInt(4) == 1));
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

    }

    public void delete(ActionEvent event) {
        String username = namefield.getText();
        ResultSet set;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Voting", "yoga2405", "Rajanr*2405");
            Statement stmt = con.createStatement();
            if (admin.isSelected())
                ;
            else
                ;
            String query = String.format("DELETE from users  where username='%s'", searchfield.getText());

            stmt.execute(query);
            System.out.println("User Deleted");

            con.close();
            searchfield.clear();
            namefield.clear();
            age.clear();
            mobile.clear();

        } catch (SQLException e) {
            System.out.println(e);
        }


    }

    public void update(ActionEvent event) {
        String username = searchfield.getText();
        ResultSet set;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Voting", "yoga2405", "Rajanr*2405");
            Statement stmt = con.createStatement();
            int isadmin;
            if (admin.isSelected())
                isadmin = 1;
            else
                isadmin = 0;
            String query = String.format("UPDATE users SET fname='%s', mobile='%s',age='%s',isadmin=%d  where username='%s'", namefield.getText(), mobile.getText(), age.getText(), isadmin, username);
            System.out.println(query);
            stmt.execute(query);
            System.out.println("User details updated");
            con.close();
            searchfield.clear();
            namefield.clear();
            age.clear();
            mobile.clear();
        } catch (SQLException e) {
            System.out.println(e);
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
