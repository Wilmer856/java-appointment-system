package main.controller;

import main.databaseAccess.ContactAccess;
import main.databaseAccess.CountryAccess;
import main.databaseAccess.ReportsAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.model.Appointments;
import main.model.Contact;
import main.model.Country;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;


/**
 * Class that allows you to view filtered reports of the appointment data.
 * @author Wilmer Guzman
 * */
public class Reports {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Appointments, Integer> appContactIdContactsCol;

    @FXML
    private TableColumn<Appointments, Integer> appContactIdCountriesCol;

    @FXML
    private TableColumn<Appointments, Integer> appContactIdMonthsCol;

    @FXML
    private TableColumn<Appointments, Integer> appContactIdTypeCol;

    @FXML
    private TableColumn<Appointments, Integer> appCustomerIdContactsCol;

    @FXML
    private TableColumn<Appointments, Integer> appCustomerIdCountriesCol;

    @FXML
    private TableColumn<Appointments, Integer> appCustomerIdMonthsCol;

    @FXML
    private TableColumn<Appointments, Integer> appCustomerIdTypeCol;

    @FXML
    private TableColumn<Appointments, String> appDescContactsCol;

    @FXML
    private TableColumn<Appointments, String> appDescCountriesCol;

    @FXML
    private TableColumn<Appointments, String> appDescMonthsCol;

    @FXML
    private TableColumn<Appointments, String> appDescTypeCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appEndContactsCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appEndCountriesCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appEndMonthsCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appEndTypeCol;

    @FXML
    private TableColumn<Appointments, Integer> appIdContactsCol;

    @FXML
    private TableColumn<Appointments, Integer> appIdCountriesCol;

    @FXML
    private TableColumn<Appointments, Integer> appIdMonthsCol;

    @FXML
    private TableColumn<Appointments, Integer> appIdTypeCol;

    @FXML
    private TableColumn<Appointments, String> appLocationContactsCol;

    @FXML
    private TableColumn<Appointments, String> appLocationCountriesCol;

    @FXML
    private TableColumn<Appointments, String> appLocationMonthsCol;

    @FXML
    private TableColumn<Appointments, String> appLocationTypeCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appStartContactsCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appStartCountriesCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appStartMonthsCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appStartTypeCol;

    @FXML
    private TableColumn<Appointments, String> appTitleContactsCol;

    @FXML
    private TableColumn<Appointments, String> appTitleCountriesCol;

    @FXML
    private TableColumn<Appointments, String> appTitleMonthsCol;

    @FXML
    private TableColumn<Appointments, String> appTitleTypeCol;

    @FXML
    private TableColumn<Appointments, String> appTypeContactsCol;

    @FXML
    private TableColumn<Appointments, String> appTypeCountriesCol;

    @FXML
    private TableColumn<Appointments, String> appTypeMonthsCol;

    @FXML
    private TableColumn<Appointments, String> appTypeTypeCol;

    @FXML
    private TableView<Appointments> appointmentsContactsTable;

    @FXML
    private TableView<Appointments> appointmentsMonthsTable;

    @FXML
    private TableView<Appointments> appointmentsCountryTable;

    @FXML
    private TableView<Appointments> appointmentsTypeTable;

    @FXML
    private Label contactsCountLbl;

    @FXML
    private ComboBox<Contact> contactsDropdown;

    @FXML
    private Label countriesCountLbl;

    @FXML
    private ComboBox<Country> countryDropdown;

    @FXML
    private Label currentUserLbl;

    @FXML
    private Label monthsCountLbl;

    @FXML
    private ComboBox<String> monthsDropdown;

    @FXML
    private Label typeCountLbl;

    @FXML
    private ComboBox<String> typeDropdown;

    @FXML
    private Label zoneIdLbl;

    /**
     * Takes you back to the 'Overview' screen.
     * Listens for button click event that takes you back to the 'Overview' screen when clicked.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onBackBtnClick(ActionEvent event) throws IOException {
        sceneChanger(event);
    }

    /**
     * Filters corresponding table.
     * Changes data on Contacts' table by selecting a contact from the dropdown and filters the appointments by CONTACT.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onContactsDropdownClick(ActionEvent event) {
        Contact contact = contactsDropdown.getValue();
        int size = ReportsAccess.getAppointmentsByContact(contact).size();
        appointmentsContactsTable.setItems(ReportsAccess.getAppointmentsByContact(contact));
        contactsCountLbl.setText("Count: " + size);
    }

    /**
     * Filters corresponding table.
     * Changes data on Countries' table by selecting a country from the dropdown and filters the appointments by COUNTRY.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onCountryDropdownClick(ActionEvent event) {
        Country country = countryDropdown.getValue();
        appointmentsCountryTable.setItems(ReportsAccess.getAppointmentsByCountry(country));
        int size = ReportsAccess.getAppointmentsByCountry(country).size();
        countriesCountLbl.setText("Count: " + size);
    }

    /**
     * Filters corresponding table.
     * Changes data on Months' table by selecting a month from the dropdown and filters the appointments by MONTH.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onMonthDropdownClick(ActionEvent event) {
        String month = monthsDropdown.getValue();
        appointmentsMonthsTable.setItems(ReportsAccess.getAppointmentsByMonth(month));
        int size = ReportsAccess.getAppointmentsByMonth(month).size();
        monthsCountLbl.setText("Count: " + size);
    }

    /**
     * Filters corresponding table.
     * Changes data on Types' table by selecting a type from the dropdown and filters the appointments by TYPE.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onTypeDropdownClick(ActionEvent event) {
        String type = typeDropdown.getValue();
        int size = ReportsAccess.getAppointmentsByType(type).size();
        appointmentsTypeTable.setItems(ReportsAccess.getAppointmentsByType(type));
        typeCountLbl.setText("Count: " + size);
    }

    /**
     * Initializes the dropdown boxes with required information and their corresponding tables.
     * */
    public void initialize(){

        ZoneId zoneLocation = ZoneId.systemDefault();
        zoneIdLbl.setText("Zone ID: " + zoneLocation);

        appIdTypeCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleTypeCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appCustomerIdTypeCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appContactIdTypeCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appDescTypeCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationTypeCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appTypeTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartTypeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndTypeCol.setCellValueFactory(new PropertyValueFactory<>("end"));

        appIdMonthsCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleMonthsCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appCustomerIdMonthsCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appContactIdMonthsCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appDescMonthsCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationMonthsCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appTypeMonthsCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartMonthsCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndMonthsCol.setCellValueFactory(new PropertyValueFactory<>("end"));

        appIdContactsCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleContactsCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appCustomerIdContactsCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appContactIdContactsCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appDescContactsCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationContactsCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appTypeContactsCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartContactsCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndContactsCol.setCellValueFactory(new PropertyValueFactory<>("end"));

        appIdCountriesCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCountriesCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appCustomerIdCountriesCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appContactIdCountriesCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appDescCountriesCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationCountriesCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appTypeCountriesCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartCountriesCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndCountriesCol.setCellValueFactory(new PropertyValueFactory<>("end"));

        typeDropdown.setItems(Appointments.getAppTypeList());
        typeDropdown.setVisibleRowCount(5);
        monthsDropdown.setItems(getAllMonths());
        monthsDropdown.setVisibleRowCount(5);
        contactsDropdown.setItems(ContactAccess.getAllContacts());
        contactsDropdown.setVisibleRowCount(5);
        countryDropdown.setItems(CountryAccess.getAllCountries());
        countryDropdown.setVisibleRowCount(5);

        typeDropdown.getSelectionModel().selectFirst();
        appointmentsTypeTable.setItems(ReportsAccess.getAppointmentsByType(typeDropdown.getValue()));
        int typeSize = ReportsAccess.getAppointmentsByType(typeDropdown.getValue()).size();
        typeCountLbl.setText("Count: " + typeSize);

        monthsDropdown.getSelectionModel().selectFirst();
        appointmentsMonthsTable.setItems(ReportsAccess.getAppointmentsByMonth(monthsDropdown.getValue()));
        int monthSize = ReportsAccess.getAppointmentsByMonth(monthsDropdown.getValue()).size();
        monthsCountLbl.setText("Count: " + monthSize);

        contactsDropdown.getSelectionModel().selectFirst();
        appointmentsContactsTable.setItems(ReportsAccess.getAppointmentsByContact(contactsDropdown.getValue()));
        int contactSize = ReportsAccess.getAppointmentsByContact(contactsDropdown.getValue()).size();
        contactsCountLbl.setText("Count: " + contactSize);

        countryDropdown.getSelectionModel().selectFirst();
        appointmentsCountryTable.setItems(ReportsAccess.getAppointmentsByCountry(countryDropdown.getValue()));
        int countrySize = ReportsAccess.getAppointmentsByCountry(countryDropdown.getValue()).size();
        countriesCountLbl.setText("Count: " + countrySize);

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

    /**
     * @return a list of all months
     * */
    private ObservableList<String> getAllMonths(){
        ObservableList<String> months = FXCollections.observableArrayList();

        months.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        return months;
    }
}
