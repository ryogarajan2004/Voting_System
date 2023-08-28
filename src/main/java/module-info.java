module com.example.voting_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    requires mysql.connector.j;

    opens com.example.voting_system to javafx.fxml;
    exports com.example.voting_system;
}