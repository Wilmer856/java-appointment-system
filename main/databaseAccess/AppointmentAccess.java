package main.databaseAccess;

import main.databaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Appointments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class that allows you to make APPOINTMENT related SQL queries and statements to the database.
 * @author Wilmer Guzman
 * */
public class AppointmentAccess {

    /**
     * @return a list of all appointments
     * */
    public static ObservableList<Appointments> getAllAppointments(){

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, User_ID, Customer_ID, Title, Description, Location, Contact_ID, Type, Start, End FROM appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

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
     * Insert into database table.
     * Calls an INSERT SQL statement to CREATE New Appointment data.
     * @param customerId customerId of appointment
     * @param title title of appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param contactId contactId of appointment
     * @param type type of appointment
     * @param start start of appointment
     * @param end end of appointment
     * @param creator creator of appointment
     * @param userId userId of appointment
     * */
    public static void insert(int customerId, String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, String creator, int userId) throws SQLException {


        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        int id = getAllAppointments().size() + 1;

        ps.setInt(1, id);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setTimestamp(6, Timestamp.valueOf(start));
        ps.setTimestamp(7, Timestamp.valueOf(end));
        ps.setTimestamp(8,  Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(9, creator);
        ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(11, creator);
        ps.setInt(12, customerId);
        ps.setInt(13, userId);
        ps.setInt(14, contactId);


        ps.executeUpdate();
    }

    /**
     * Updates database table.
     * Calls an UPDATE SQL statement to UPDATE existing Appointment data.
     * @param customerId customerId of appointment
     * @param appointmentId appointmentId of appointment
     * @param title title of appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param contactId contactId of appointment
     * @param type type of appointment
     * @param start start of appointment
     * @param end end of appointment
     * @param editor editor of appointment
     * @param userId userId of appointment
     * */
    public static void update(int customerId, int appointmentId, String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, String editor, int userId) throws SQLException {

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, editor);
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, appointmentId);

        ps.executeUpdate();
    }

    /**
     * Deletes table data.
     * Calls a DELETE SQL statement to DELETE Appointment data.
     * @param id ID of appointment to delete
     * */
    public static void delete(int id) throws SQLException {

        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();
    }

    /**
     * Order Appointment ID's to consecutive numerical order
     * */
    public static void updateIdOrder() throws SQLException {

        int newId = 0;
        for(Appointments appointments : getAllAppointments()){
            newId++;
            String sql = "UPDATE appointments SET Appointment_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, newId);
            ps.setInt(2, appointments.getId());

            ps.executeUpdate();
        }
    }

    /**
     * @return a list of all appointments in the CURRENT MONTH
     * */
    public static ObservableList<Appointments> getCurrentMonthAppointments(){

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(CURDATE()) AND YEAR(Start) = YEAR(CURDATE())";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

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
     * @return a list of all appointments in the CURRENT WEEK
     * */
    public static ObservableList<Appointments> getCurrentWeekAppointments(){

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(CURDATE()) AND YEAR(Start) = YEAR(CURDATE())";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

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
     * @return a list of all appointments ordered by Customers
     * */
    public static ObservableList<Appointments> getCustomerOrderedAppointments(){

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments ORDER BY Customer_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

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
     * @return a list of all appointments in a SPECIFIC DATE
     * @param selectedDate date of appointment
     * */
    public static ObservableList<Appointments> getSelectedDateAppointments(LocalDate selectedDate){

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE DATE(Start) = DATE(?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, String.valueOf(selectedDate));
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
