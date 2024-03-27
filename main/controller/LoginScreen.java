package main.controller;

import main.databaseAccess.UserAccess;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.model.Users;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class that allows you to sign in and provides functionality to the 'Log-in screen' screen.
 * @author Wilmer Guzman
 * */
public class LoginScreen {

    Stage stage;
    Parent scene;

    @FXML
    private TextField passWordTxt;

    @FXML
    private TextField userNameTxt;

    @FXML
    private Label zoneIdLbl;

    @FXML
    private Label currentUserLbl;

    @FXML
    private Label passWordLbl;

    @FXML
    private Label userNameLbl;

    @FXML
    private Label invalidUserLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private Button exitBtn;

    /**
     * Allows you to sign in.
     * Validates user login and logs time of attempted login to 'login_activity.txt'.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onLoginClick(ActionEvent event) throws IOException {

        LocalDateTime currentTime = LocalDateTime.now();
        ZonedDateTime currentTimeZoneTime = currentTime.atZone(ZoneId.systemDefault());
        ZonedDateTime timeToUtc = currentTimeZoneTime.withZoneSameInstant(ZoneId.of("Etc/UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String timestamp = timeToUtc.format(formatter);

        String fileName = "login_activity.txt", item;
        File file = new File(fileName);

        FileWriter writer = new FileWriter(file, true);
        PrintWriter moveToFile = new PrintWriter(writer);



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/view/overview.fxml"));
        loader.load();

        Overview overview = loader.getController();

        ObservableList<Users> users = UserAccess.getAllUsers();
        String username = userNameTxt.getText();
        String password = passWordTxt.getText();
        ResourceBundle language = ResourceBundle.getBundle("/main/language/lang", Locale.getDefault());

        for(Users user : users){
            if((username.equals(user.getUsername())) && (password.equals(user.getPassword()))){

                item = username;
                moveToFile.println(timestamp + " " + "Username: " + item + " LOGIN SUCCESSFUL");
                moveToFile.close();

                overview.sendDetails(user);
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
                return;
            }
        }
        item = username;
        moveToFile.println(timestamp + " " + "Username: " + item + " LOGIN FAILED");
        moveToFile.close();
        invalidUserLbl.setText(language.getString("invalidLogin"));
    }

    /**
     * Closes the program.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onExitBtnClick(ActionEvent event) {
        ResourceBundle language = ResourceBundle.getBundle("/main/language/lang", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, language.getString("exit"));
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    /**
     * Initializes the labels with proper information.
     * Labels will be translated to English or French according to the Locale of the system.
     * */
    public void initialize(){
        try{
            ZoneId zoneLocation = ZoneId.systemDefault();
            zoneIdLbl.setText("Zone ID: " + zoneLocation);

            Locale location = Locale.getDefault();
            Locale.setDefault(location);

            ResourceBundle language = ResourceBundle.getBundle("/main/language/lang", Locale.getDefault());

            if(Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")){
                userNameLbl.setText(language.getString("username"));
                passWordLbl.setText(language.getString("password"));
                loginBtn.setText(language.getString("login"));
                currentUserLbl.setText(language.getString("currentUser"));
                exitBtn.setText(language.getString("close"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}