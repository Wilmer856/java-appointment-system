package main.controller;

import main.databaseAccess.AppointmentAccess;
import main.databaseAccess.CustomerAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.model.Appointments;
import main.model.Customer;
import main.model.Users;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Class that allows you to view customers and appointments and provides functionality to the 'Overview' screen.
 * @author Wilmer Guzman
 * */
public class Overview {

    Stage stage;
    Parent scene;
    ResourceBundle language = ResourceBundle.getBundle("/main/language/lang", Locale.getDefault());

    @FXML
    private TableColumn<Appointments, Integer> appContactIdCol;

    @FXML
    private TableColumn<Appointments, Integer> appCustomerIdCol;

    @FXML
    private TableColumn<Appointments, String> appDescCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appEndCol;

    @FXML
    private TableColumn<Appointments, Integer> appIdCol;

    @FXML
    private TableColumn<Appointments, Integer> appUserIdCol;

    @FXML
    private TableColumn<Appointments, String> appLocationCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> appStartCol;

    @FXML
    private TableColumn<Appointments, String> appTitleCol;

    @FXML
    private TableColumn<Appointments, String> appTypeCol;

    @FXML
    private ToggleGroup appointRdGroup;

    @FXML
    private RadioButton viewAllRdBtn;

    @FXML
    private RadioButton viewByCustomerRdBtn;

    @FXML
    private RadioButton viewByMonthRdBtn;

    @FXML
    private RadioButton viewByWeekRdBtn;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private TableView<Appointments> appointmentsTable;

    @FXML
    private Label currentUserLbl;

    @FXML
    private TableColumn<Customer, String> customerAddCol;

    @FXML
    private TableColumn<Customer, Integer> customerDivIdCol;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerPostCol;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private Label zoneIdLbl;

    /**
     * Change to 'Add Appointment' screen.
     * Checks to see if Customer is selected from the customer table and sends the data to the 'Add Appointment' screen.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onAddAppointmentClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/view/addAppointment.fxml"));
        loader.load();
        AddAppointment appointment = loader.getController();
        if(customerTable.getSelectionModel().getSelectedItem() == null){
            return;
        }

        appointment.sendDetails(customerTable.getSelectionModel().getSelectedItem(), currentUserLbl.getText());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Change to 'Add Customer' screen.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onAddCustomerClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/view/addCustomer.fxml"));
        loader.load();
        AddCustomer ad = loader.getController();

        ad.sendDetails(currentUserLbl.getText());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Deletes Customer.
     * Checks to see if Customer is selected and if they have a scheduled appointment before deleting.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onDeleteCustomerClick(ActionEvent event) throws SQLException {

        if(customerTable.getSelectionModel().getSelectedItem() == null){
            return;
        }

        int id = customerTable.getSelectionModel().getSelectedItem().getId();
        for(Appointments a : AppointmentAccess.getAllAppointments()){
            if(id == a.getCustomerId()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Error removing customer. Please make sure the customer does not have any appointments and then try again.");
                alert.show();
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the customer. Select OK to continue.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            CustomerAccess.delete(customerTable.getSelectionModel().getSelectedItem().getId());
            CustomerAccess.updateIdOrder();
            customerTable.setItems(CustomerAccess.getAllCustomers());

            Alert success = new Alert(Alert.AlertType.INFORMATION, "Customer successfully removed. (ID: " + id + ")");
            success.show();
        }


    }

    /**
     * Change to 'Modify Customer' screen.
     * Checks to see if Customer is selected from the customer table and sends the data to the 'Modify Customer' screen.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onUpdateCustomerClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/view/modifyCustomer.fxml"));
        loader.load();

        ModifyCustomer modCustomer = loader.getController();
        if(customerTable.getSelectionModel().getSelectedItem() == null){
            return;
        }
        modCustomer.sendDetails(customerTable.getSelectionModel().getSelectedItem(), currentUserLbl.getText());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Change to 'Modify Appointment' screen.
     * Checks to see if Appointment is selected from the Appointment table and sends the data to the 'Modify Appointment' screen.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onUpdateAppClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/view/modifyAppointment.fxml"));
        loader.load();
        ModifyAppointment appointment = loader.getController();
        if(appointmentsTable.getSelectionModel().getSelectedItem() == null){
            return;
        }

        appointment.sendDetails(appointmentsTable.getSelectionModel().getSelectedItem(), currentUserLbl.getText());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes Appointment.
     * Checks to see if Appointment is selected and deletes it.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onDeleteAppClick(ActionEvent event) throws SQLException {

        if(appointmentsTable.getSelectionModel().getSelectedItem() == null){
            return;
        }
        Appointments a = appointmentsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove and cancel the appointment. Select OK to continue.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            AppointmentAccess.delete(a.getId());
            AppointmentAccess.updateIdOrder();
            appointmentsTable.setItems(AppointmentAccess.getAllAppointments());

            Alert success = new Alert(Alert.AlertType.INFORMATION, "Appointment successfully canceled. (Appointment ID: " + a.getId() + ". Appointment Type: " + a.getType() + ")");
            success.show();
        }
    }

    /**
     * Change to 'Reports' screen.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onReportsBtnClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/view/reports.fxml"));
        loader.load();
        Reports reports = loader.getController();

        reports.sendDetails(currentUserLbl.getText());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Displays All Appointments in the Appointments Table
     * @param event The event the program is listening for.
     * */
    @FXML
    void viewAllRdBtnClick(ActionEvent event) {
        viewAllRdBtn.setSelected(true);
        appointmentDatePicker.setValue(null);
        appointmentsTable.setItems(AppointmentAccess.getAllAppointments());
    }

    /**
     * Displays All Appointments ordered by the Customers in the Appointments Table
     * @param event The event the program is listening for.
     * */
    @FXML
    void viewCustomerRdBtnClick(ActionEvent event) {
        viewByCustomerRdBtn.setSelected(true);
        appointmentDatePicker.setValue(null);
        appointmentsTable.setItems(AppointmentAccess.getCustomerOrderedAppointments());
    }

    /**
     * Displays All Appointments for the CURRENT MONTH in the Appointments Table
     * @param event The event the program is listening for.
     * */
    @FXML
    void viewMonthRdBtnClick(ActionEvent event) {
        viewByMonthRdBtn.setSelected(true);
        appointmentDatePicker.setValue(null);
        appointmentsTable.setItems(AppointmentAccess.getCurrentMonthAppointments());
    }

    /**
     * Displays All Appointments for the CURRENT WEEK in the Appointments Table
     * @param event The event the program is listening for.
     * */
    @FXML
    void viewWeekRdBtnClick(ActionEvent event) {
        viewByWeekRdBtn.setSelected(true);
        appointmentDatePicker.setValue(null);
        appointmentsTable.setItems(AppointmentAccess.getCurrentWeekAppointments());
    }

    /**
     * Displays All Appointments of a SPECIFIC DATE in the Appointments Table
     * @param event The event the program is listening for.
     * */
    @FXML
    void onSelectDateClick(ActionEvent event) {
        if(appointmentDatePicker.getValue() == null){
            appointmentsTable.setItems(AppointmentAccess.getAllAppointments());
            return;
        }
        appointRdGroup.selectToggle(null);
        LocalDate selectedDate = appointmentDatePicker.getValue();
        appointmentsTable.setItems(AppointmentAccess.getSelectedDateAppointments(selectedDate));
    }

    /**
     * Logs out of the application.
     * @param event The event the program is listening for.
     * */
    @FXML
    void onLogOutBtnCLick(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to sign out?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            sceneChanger(event);
        }
    }

    /**
     * Initializes the tables with required information and the labels.
     * */
    public void initialize(){

        ZoneId zoneLocation = ZoneId.systemDefault();
        zoneIdLbl.setText("Zone ID: " + zoneLocation);

        customerTable.setItems(CustomerAccess.getAllCustomers());

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        appointmentsTable.setItems(AppointmentAccess.getAllAppointments());

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
    }

    /**
     * Takes you back to the 'Login' screen.
     * @param event The event the program is listening for.
     * */
    private void sceneChanger(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/main/view/loginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Checks if there's an appointment within 15 minutes of logging in and translates the message to English or French.
     * @param user user that signed in
     * */
    public void sendDetails(Users user){

        currentUserLbl.setText("Current User: " + user.getUsername());
        int appCount = 0;
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime upcomingAppTime = currentTime.plusMinutes(15);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(language.getString("warning"));

        if(Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")){
            if(AppointmentAccess.getAllAppointments() != null) {
                for (Appointments appointment : AppointmentAccess.getAllAppointments()) {
                    if (appointment.getStart().isEqual(currentTime) || (appointment.getStart().isAfter(currentTime)) && (appointment.getStart().isBefore(upcomingAppTime) || appointment.getStart().isEqual(upcomingAppTime))) {
                        appCount+=1;
                        alert.setContentText(language.getString("reminder") + " (" + appointment.getId() + ") " + language.getString("at") + " " +appointment.getStart() + " " + language.getString("comingUp"));
                        alert.show();
                    }
                }
            }
            if(appCount == 0){
                alert.setContentText(language.getString("noAppointments"));
                alert.show();
            }
        }
    }

    /**
     * Receives the username of logged-in user and displays it on the appropriate label
     * @param username username to display.
     * */
    public void sendDetails(String username){

        currentUserLbl.setText(username);
    }

}