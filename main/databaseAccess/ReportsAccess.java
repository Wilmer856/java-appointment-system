package main.databaseAccess;

import main.databaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Appointments;
import main.model.Contact;
import main.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Class that allows you to make Filtered APPOINTMENT related SQL queries to the database to create REPORTS of specific data.
 * LAMBDA #1: Located on LINE 29 in getAppointmentsByType().
 * @author Wilmer Guzman
 * */
public class ReportsAccess {

    /**
     * LAMBDA #1: Significantly reduces the lines of code to filter a list of appointments by type without using SQL.
     * @param appType the type to filter by
     * @return a list of appointments filtered by Appointment type
     * */
    public static ObservableList<Appointments> getAppointmentsByType(String appType){
        //Lambda #1
        return AppointmentAccess.getAllAppointments().filtered(a -> a.getType().equals(appType));
    }

    /**
     * @param month the month to filter by
     * @return a list of appointments of a specific month
     * */
    public static ObservableList<Appointments> getAppointmentsByMonth(String month){

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, User_ID, Customer_ID, Title, Description, Location, Contact_ID, Type, Start, End FROM appointments WHERE MONTHNAME(Start) = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, month);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("Appointment_ID");
                int userId = rs.getInt("User_ID");
                int customerId = rs.getInt("Customer_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

                Appointments appointment = new Appointments(id, userId, customerId, title, description, location, contactId, type, start, end);
                appointmentList.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * @param contact the contact to filter by
     * @return a list of appointments of a specific contact
     * */
    public static ObservableList<Appointments> getAppointmentsByContact(Contact contact){

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, User_ID, Customer_ID, Title, Description, Location, Contact_ID, Type, Start, End FROM appointments WHERE Contact_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contact.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("Appointment_ID");
                int userId = rs.getInt("User_ID");
                int customerId = rs.getInt("Customer_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

                Appointments appointment = new Appointments(id, userId, customerId, title, description, location, contactId, type, start, end);
                appointmentList.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * @param country the country to filter by
     * @return a list of appointments of a specific country
     * */
    public static ObservableList<Appointments> getAppointmentsByCountry(Country country){

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, User_ID, Customer_ID, Title, Description, Location, Contact_ID, Type, Start, End FROM appointments WHERE Location LIKE ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, "%" + country.getCountryName());

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("Appointment_ID");
                int userId = rs.getInt("User_ID");
                int customerId = rs.getInt("Customer_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

                Appointments appointment = new Appointments(id, userId, customerId, title, description, location, contactId, type, start, end);
                appointmentList.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentList;
    }


}
