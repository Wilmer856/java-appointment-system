package main.controller;

import main.databaseAccess.CountryAccess;
import main.databaseAccess.CustomerAccess;
import main.databaseAccess.FirstLevelDivAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.model.Country;
import main.model.Customer;
import main.model.FirstLevelDivisions;

import java.io.IOException;
import java.time.ZoneId;

/**
 * Class that allows you to modify Customers and provides functionality to the 'Modify Customer' screen.
 * @author Wilmer Guzman
 * */
public class ModifyCustomer {

    Stage stage;
    Parent scene;

    @FXML
    private ComboBox<Country> countryDropDown;

    @FXML
    private Label currentUserLbl;

    @FXML
    private TextField customerAddressTxt;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField customerPhoneTxt;

    @FXML
    private TextField postalCodeTxt;

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
     * Filters state dropdown.
     * Listens for button click event that changes the list of states according to the country that is currently selected utilizing lambda #2 (forEach) to filter the list.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onCountryDropDownClick(ActionEvent event) {
        Country country = countryDropDown.getValue();
        ObservableList<FirstLevelDivisions> states = FirstLevelDivAccess.getAllDivisions();
        ObservableList<FirstLevelDivisions> statesFiltered = FXCollections.observableArrayList();

        states.forEach(state -> {
            if(state.getCountry_ID() == country.getCountryID()){
                statesFiltered.add(state);
            }
        });

        stateDropDown.valueProperty().set(null);
        stateDropDown.setItems(statesFiltered);
    }

    /**
     * Saves edited information of existing customer.
     * Listens for button click event that saves the details provided, updates them in the database, and takes you back to the 'Overview' screen.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onSaveBtnClick(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        try{
            int id = Integer.parseInt(customerIdTxt.getText());
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

            String postalCode = postalCodeTxt.getText();
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

            CustomerAccess.update(id, name, address, postalCode, phone, "admin", "admin", divId);
            sceneChanger(event);

            Alert success = new Alert(Alert.AlertType.INFORMATION, "Customer (ID: " + id + ")" + " successfully modified.");
            success.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Receives Customer information.
     * Customer selected from the 'Overview' screen will provide its details into all the fields and the name of the currently signed-in user into its proper label.
     * @param customer Customer instance
     * @param username username of current user
     * */
    public void sendDetails(Customer customer, String username){
        currentUserLbl.setText(username);
        FirstLevelDivisions fld = null;
        int country = 0;
        customerIdTxt.setText(String.valueOf(customer.getId()));
        customerNameTxt.setText(customer.getName());
        customerAddressTxt.setText(customer.getAddress());
        postalCodeTxt.setText(customer.getPostalCode());
        customerPhoneTxt.setText(customer.getPhone());

        for(FirstLevelDivisions d : FirstLevelDivAccess.getAllDivisions()){
            if(customer.getDivisionId() == d.getDivisionID()){
                fld = d;
                country = fld.getCountry_ID();
            }
        }

        countryDropDown.getSelectionModel().select(country - 1);
        stateDropDown.getSelectionModel().select(fld);

        Country c = countryDropDown.getValue();
        ObservableList<FirstLevelDivisions> states = FirstLevelDivAccess.getAllDivisions();
        ObservableList<FirstLevelDivisions> statesFiltered = FXCollections.observableArrayList();

        states.forEach(state -> {
            if(state.getCountry_ID() == c.getCountryID()){
                statesFiltered.add(state);
            }
        });

        stateDropDown.setItems(statesFiltered);
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
     * Initializes the dropdown boxes with required information and the labels.
     * */
    public void initialize(){

        ZoneId zoneLocation = ZoneId.systemDefault();
        zoneIdLbl.setText("Zone ID: " + zoneLocation);

        countryDropDown.setItems(CountryAccess.getAllCountries());
        countryDropDown.setVisibleRowCount(5);
        stateDropDown.setVisibleRowCount(5);
    }

}