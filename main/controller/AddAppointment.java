package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.databaseAccess.*;
import main.model.*;

import java.io.IOException;
import java.time.*;

/**
 * Class that allows you to add appointments and provides functionality to the 'Add Appointment' screen.
 * LAMBDA #2: LOCATED ON LINE 200 in onCountryDropDownClick().
 * @author Wilmer Guzman
 * */
public class AddAppointment {

    Stage stage;
    Parent scene;

    @FXML
    private ComboBox<Contact> contactDropdown;

    @FXML
    private ComboBox<Country> countryDropdown;

    @FXML
    private Label currentUserLbl;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private ComboBox<String> endHourDropdown;

    @FXML
    private ComboBox<String> endMinuteDropdown;

    @FXML
    private ComboBox<String> endSecondsDropdown;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<String> startHourDropdown;

    @FXML
    private ComboBox<String> startMinuteDropdown;

    @FXML
    private ComboBox<String> startSecondsDropdown;

    @FXML
    private ComboBox<FirstLevelDivisions> stateDropdown;

    @FXML
    private TextField titleTxt;

    @FXML
    private ComboBox<String> typeDropdown;

    @FXML
    private TextField userIdTxt;

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
     * Saves an appointment.
     * Listens for button click event that saves the details provided, stores them in the database, and takes you back to the 'Overview' screen.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onSaveBtnClick(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        try {
            int customerId = Integer.parseInt(customerIdTxt.getText());
            int userId = Integer.parseInt(userIdTxt.getText());
            int contactId = contactDropdown.getValue().getId();
            int appId = AppointmentAccess.getAllAppointments().size() + 1;
            String creator = "";

            for(Users user : UserAccess.getAllUsers()){
                if(user.getId() == userId){
                    creator = user.getUsername();
                }
            }

            String title = titleTxt.getText();
            if (title.isBlank()) {
                alert.setContentText("Title is blank!");
                alert.show();
                return;
            }

            String description = descriptionTxt.getText();
            if (description.isBlank()) {
                alert.setContentText("Description is blank!");
                alert.show();
                return;
            }

            if(stateDropdown.getValue() == null){
                alert.setContentText("You must choose a state and country!");
                alert.show();
                return;
            }
            FirstLevelDivisions fld = stateDropdown.getValue();
            Country country = countryDropdown.getValue();
            String location = fld.getDivisionName() + ", " + country.getCountryName();
            String type = typeDropdown.getValue();

            LocalTime startTime = LocalTime.of(Integer.parseInt(startHourDropdown.getValue()), Integer.parseInt(startMinuteDropdown.getValue()), Integer.parseInt(startSecondsDropdown.getValue()));
            LocalTime endTime = LocalTime.of(Integer.parseInt(endHourDropdown.getValue()), Integer.parseInt(endMinuteDropdown.getValue()), Integer.parseInt(endSecondsDropdown.getValue()));

            LocalDateTime startDateTime = LocalDateTime.of(startDatePicker.getValue(), startTime);
            LocalDateTime endDateTime = LocalDateTime.of(startDatePicker.getValue(), endTime);

            ZonedDateTime currentTimeZoneStart = startDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime easternTimeZoneStart = currentTimeZoneStart.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime easternToCurrentStart = easternTimeZoneStart.toLocalDateTime();
            LocalDateTime businessStart = LocalDateTime.of(startDatePicker.getValue(), LocalTime.of(8, 0));

            ZonedDateTime currentTimeZoneEnd = endDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime easterTimeZoneEnd = currentTimeZoneEnd.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime easternToCurrentEnd = easterTimeZoneEnd.toLocalDateTime();
            LocalDateTime businessEnd = LocalDateTime.of(startDatePicker.getValue(), LocalTime.of(22, 0));


            if(easternToCurrentStart.isAfter(easternToCurrentEnd)) {
                alert.setContentText("The start of the appointment must be before the end time!");
                alert.show();
                return;
            } else if(easternToCurrentEnd.isBefore(easternToCurrentStart)) {
                alert.setContentText("The end of the appointment must be after the start time!");
                alert.show();
                return;
            } else if(easternToCurrentStart.isEqual(easternToCurrentEnd)) {
                alert.setContentText("The start of the appointment cannot be the same as the end time!");
                alert.show();
                return;
            } else if(easternToCurrentStart.isBefore(businessStart)) {
                alert.setContentText("Appointment time is before office hours! Please choose a time during office hours (08:00 - 22:00 EST).");
                alert.show();
                return;
            } else if(easternToCurrentEnd.isAfter(businessEnd)) {
                alert.setContentText("Appointment time is after office hours! Please choose a time during office hours (08:00 - 22:00 EST).");
                alert.show();
                return;
            } else if(timeOverlap(customerId, appId, startDateTime, endDateTime)) {
                return;
            }

            AppointmentAccess.insert(customerId, title, description, location,contactId, type, startDateTime, endDateTime, creator, userId);
            sceneChanger(event);

            Alert success = new Alert(Alert.AlertType.INFORMATION, "Appointment successfully added.");
            success.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Filters state dropdown.
     * Listens for button click event that changes the list of states according to the country that is currently selected utilizing lambda #2 (forEach) to filter the list.
     * LAMBDA #2: Filters a list using the forEach method in just a few lines.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onCountryDropDownClick(ActionEvent event) {
        Country country = countryDropdown.getValue();
        ObservableList<FirstLevelDivisions> states = FirstLevelDivAccess.getAllDivisions();
        ObservableList<FirstLevelDivisions> statesFiltered = FXCollections.observableArrayList();

        //lambda #2
        states.forEach(state -> {
            if(state.getCountry_ID() == country.getCountryID()){
                statesFiltered.add(state);
            }
        });
        stateDropdown.setItems(statesFiltered);
    }


    /**
     * Initializes the dropdown boxes with required information and the labels.
     * */
    public void initialize(){

        ZoneId zoneLocation = ZoneId.systemDefault();
        zoneIdLbl.setText("Zone ID: " + zoneLocation);

        contactDropdown.setItems(ContactAccess.getAllContacts());
        contactDropdown.getSelectionModel().selectFirst();
        contactDropdown.setVisibleRowCount(5);

        countryDropdown.setItems(CountryAccess.getAllCountries());
        countryDropdown.getSelectionModel().selectFirst();
        countryDropdown.setVisibleRowCount(5);

        typeDropdown.setItems(Appointments.getAppTypeList());
        typeDropdown.getSelectionModel().selectFirst();
        typeDropdown.setVisibleRowCount(5);

        Country c = countryDropdown.getValue();
        ObservableList<FirstLevelDivisions> states = FirstLevelDivAccess.getAllDivisions();
        ObservableList<FirstLevelDivisions> statesFiltered = FXCollections.observableArrayList();

        states.forEach(state -> {
            if(state.getCountry_ID() == c.getCountryID()){
                statesFiltered.add(state);
            }
        });

        stateDropdown.setItems(statesFiltered);
        stateDropdown.setVisibleRowCount(5);

        startDatePicker.setValue(LocalDate.now());
        populateTimeDropdowns();

    }

    /**
     * Receives customer information.
     * Customer selected from the 'Overview' screen will provide its customerID into the text field and the name of the currently signed-in user into its proper label.
     * @param customer customer instance
     * @param username username of current user
     * */
    public void sendDetails(Customer customer, String username){
        customerIdTxt.setText(String.valueOf(customer.getId()));
        currentUserLbl.setText(username);

        obtainUserId();

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
     * @return id of current user
     * */
    private int obtainUserId() {
        int id = 0;
        for (Users user : UserAccess.getAllUsers()) {
            if (currentUserLbl.getText().contains(user.getUsername())) {
                id = user.getId();
                userIdTxt.setText(String.valueOf(id));
                break;
            }
        }
        return id;
    }

    /**
     * Populate time dropdowns.
     * Provides the time dropdowns with default data and a list of time to choose from.
     * */
    private void populateTimeDropdowns(){

        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();
        ObservableList<String> seconds = FXCollections.observableArrayList();

        for(int h = 0; h <= LocalTime.MAX.getHour(); h++){
            if(h < 10){
                hours.add("0" + h);
            } else {
                hours.add(String.valueOf(h));
            }
        }
        for(int m = 0; m <= LocalTime.MAX.getMinute(); m++){
            if(m < 10){
                minutes.add("0" + m);
            } else {
                minutes.add(String.valueOf(m));
            }
        }
        for(int s = 0; s <= LocalTime.MAX.getSecond(); s++){
            if(s < 10){
                seconds.add("0" + s);
            } else {
                seconds.add(String.valueOf(s));
            }
        }

        startHourDropdown.setItems(hours);
        startMinuteDropdown.setItems(minutes);
        startSecondsDropdown.setItems(seconds);
        endHourDropdown.setItems(hours);
        endMinuteDropdown.setItems(minutes);
        endSecondsDropdown.setItems(seconds);

        startHourDropdown.setVisibleRowCount(5);
        startMinuteDropdown.setVisibleRowCount(5);
        startSecondsDropdown.setVisibleRowCount(5);
        endHourDropdown.setVisibleRowCount(5);
        endMinuteDropdown.setVisibleRowCount(5);
        endSecondsDropdown.setVisibleRowCount(5);

        startHourDropdown.getSelectionModel().select(8);
        startMinuteDropdown.getSelectionModel().selectFirst();
        startSecondsDropdown.getSelectionModel().selectFirst();
        endHourDropdown.getSelectionModel().select(9);
        endMinuteDropdown.getSelectionModel().selectFirst();
        endSecondsDropdown.getSelectionModel().selectFirst();
    }

    /**
     * Check for time overlaps.
     * Alerts the user if the current user has an appointment that overlaps with an existing appointment.
     * @param customerId the id of the customer
     * @param appointmentId the appointment id
     * @param start the start of the appointment
     * @param end the end of the appointment
     * @return true or false
     * */
    private boolean timeOverlap(int customerId, int appointmentId, LocalDateTime start, LocalDateTime end) throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        for (Appointments app : AppointmentAccess.getAllAppointments()) {

            if (customerId != app.getCustomerId()) {
                continue;
            }
            if (appointmentId == app.getId()) {
                continue;
            }
            else if (app.getStart().isEqual(start) || app.getStart().isEqual(end) || app.getEnd().isEqual(start) || app.getEnd().isEqual(end)){
                alert.setContentText("Appointment start and end times are the same as an existing appointment!");
                alert.show();
                return true;
            }
            else if (start.isAfter(app.getStart()) && (start.isBefore(app.getEnd()))){
                alert.setContentText("Start time overlaps with an existing appointment!");
                alert.show();
                return true;
            }
            else if (end.isAfter(app.getStart()) && end.isBefore(app.getEnd())){
                alert.setContentText("End time overlaps with an existing appointment!");
                alert.show();
                return true;
            }
        }
        return false;
    }


}