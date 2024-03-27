package main.controller;

import main.databaseAccess.CountryAccess;
import main.databaseAccess.CustomerAccess;
import main.databaseAccess.FirstLevelDivAccess;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.model.Country;
import main.model.FirstLevelDivisions;

import java.io.IOException;
import java.time.ZoneId;

/**
 * Class that allows you to add Customers and provides functionality to the 'Add Customer' screen.
 * LAMBDA #3: LOCATED ON LINE 133 in onCountryDropDownClick().
 * @author Wilmer Guzman
 * */
public class AddCustomer {

    Stage stage;
    Parent scene;

    @FXML
    private ComboBox<Country> countryDropDown;

    @FXML
    private Label currentUserLbl;

    @FXML
    private TextField customerAddressTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField customerPhoneTxt;

    @FXML
    private TextField customerPostalCodeTxt;

    @FXML
    private ComboBox<FirstLevelDivisions> stateDropDown;

    @FXML
    private Label zoneIdLbl;

    /**
     * Takes you back to the 'Overview' screen.
     * Listens for button click event that takes you back to the 'Overview' screen when clicked.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onCancelBtnClick(ActionEvent event) throws IOException {
        sceneChanger(event);
    }

    /**
     * Saves a Customer.
     * Listens for button click event that saves the details provided, stores them in the database, and takes you back to the 'Overview' screen.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onSaveBtnClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        try{
            String name = customerNameTxt.getText();
            if(name.isBlank()){
                alert.setContentText("Name is blank!");
                alert.show();
                return;
            }

            String address = customerAddressTxt.getText();
            if(address.isBlank()){
                alert.setContentText("Address is blank!");
                alert.show();
                return;
            }

            String postalCode = customerPostalCodeTxt.getText();
            if(postalCode.isBlank()){
                alert.setContentText("Postal Code is blank!");
                alert.show();
                return;
            }

            String phone = customerPhoneTxt.getText();
            if(phone.isBlank()){
                alert.setContentText("Phone number is blank!");
                alert.show();
                return;
            }

            if(countryDropDown.getValue() == null || stateDropDown.getValue() == null){
                alert.setContentText("You must choose a state and country!");
                alert.show();
                return;
            }
            int divId = stateDropDown.getValue().getDivisionID();

            CustomerAccess.insert(name, address, postalCode, phone, "admin", "admin", divId);

            sceneChanger(event);
            Alert success = new Alert(Alert.AlertType.INFORMATION, "Customer successfully added.");
            success.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Filters state dropdown.
     * Changes the list of states according to the country that is currently selected utilizing lambda #3 (filtered) to filter the list.
     * LAMBDA #3: Improved version of LAMBDA #2. Reduces the lines of code to just 1 to filter a list.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onCountryDropDownClick(ActionEvent event) {

        Country country = countryDropDown.getValue();
        ObservableList<FirstLevelDivisions> states = FirstLevelDivAccess.getAllDivisions();

        //lambda expression below easily allows for the filtering of the state dropdown to populate the states according to the country.
        ObservableList<FirstLevelDivisions> statesFiltered = states.filtered(state -> state.getCountry_ID() == country.getCountryID());

        stateDropDown.setItems(statesFiltered);
    }

    /**
     * Initializes the dropdown boxes with required information and the labels.
     * */
    public void initialize(){

        ZoneId zoneLocation = ZoneId.systemDefault();
        zoneIdLbl.setText("Zone ID: " + zoneLocation);

        countryDropDown.setItems(CountryAccess.getAllCountries());
        countryDropDown.setPromptText("Choose a country...");
        countryDropDown.setVisibleRowCount(5);
        stateDropDown.setVisibleRowCount(5);
    }

    /**
     * Takes you back to the 'Overview' screen.
     * @param event The event the program is listening for.
     * */
    private void sceneChanger(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/view/overview.fxml"));
        loader.load();
        Overview overview = loader.getController();

        overview.sendDetails(currentUserLbl.getText());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Set name of signed-in user to its proper label
     * @param username username of current user
     * */
    public void sendDetails(String username){
        currentUserLbl.setText(username);
    }

}