package main.Main;

import main.databaseConnection.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//Javadocs folder is located in the root directory of this application.

/** This class creates, starts, and launches the program.
 THE APPLICATION: An appointment scheduling application with a GUI.
 Lambda #1: Located in ReportsAccess.java on LINE 29
 Lambda #2: Located in AddAppointment.java on LINE 200
 Lambda #3: Located in AddCustomer.java on LINE 133
 @author Wilmer Guzman
 */
public class MainApplication extends Application {

    /**Displays the starting UI.
     Method that creates and loads the initial FXML file by providing a stage container for the Scene.
     @param stage Stage container for the scene
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/main/view/loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 700);
        stage.setScene(scene);
        stage.show();
    }

    /** Launches the program and opens connection to the database.*/
    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}