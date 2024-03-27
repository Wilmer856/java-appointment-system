module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;


    opens main.Main to javafx.fxml;
    exports main.Main;
    exports main.controller;
    exports main.model;
    exports main.databaseConnection;
    exports main.databaseAccess;
    opens main.controller to javafx.fxml;
}