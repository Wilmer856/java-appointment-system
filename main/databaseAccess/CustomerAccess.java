package main.databaseAccess;

import main.databaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class that allows you to make APPOINTMENT related SQL queries and statements to the database.
 * @author Wilmer Guzman
 * */
public class CustomerAccess {

    /**
     * @return a list of all Customers
     * */
    public static ObservableList<Customer> getAllCustomers(){

        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divId = rs.getInt("Division_ID");

                Customer customer = new Customer(id, name, address, postal, phone, divId);
                customerList.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    /**
     * Insert into database table.
     * Calls an INSERT SQL statement to CREATE New Customer data.
     * @param name Name of customer
     * @param address Address of customer
     * @param postalCode Postal code of customer
     * @param phone phone number of customer
     * @param creator creator of customer
     * @param lastUpdatedBy last update of customer
     * @param divId divisionId of customer
     * */
    public static void insert(String name, String address, String postalCode, String phone, String creator, String lastUpdatedBy, int divId) throws SQLException {


        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        int id = getAllCustomers().size() + 1;

        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(7, creator);
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(9, lastUpdatedBy);
        ps.setInt(10, divId);

        ps.executeUpdate();
    }

    /**
     * Updates database table.
     * Calls an UPDATE SQL statement to UPDATE New Customer data.
     * @param id ID of customer
     * @param name Name of customer
     * @param address Address of customer
     * @param postalCode Postal code of customer
     * @param phone phone number of customer
     * @param creator creator of customer
     * @param lastUpdatedBy last update of customer
     * @param divId divisionId of customer
     * */
    public static void update(int id, String name, String address, String postalCode, String phone, String creator, String lastUpdatedBy, int divId) throws SQLException {

        String sql = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(7, creator);
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(9, lastUpdatedBy);
        ps.setInt(10, divId);
        ps.setInt(11, id);

        ps.executeUpdate();
    }

    /**
     * Order Customer ID's to consecutive numerical order
     * */
    public static void updateIdOrder() throws SQLException {

        int newId = 0;
        for(Customer customer : getAllCustomers()){
            newId++;
            String sql = "UPDATE customers SET Customer_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, newId);
            ps.setInt(2, customer.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Deletes table data.
     * Calls a DELETE SQL statement to DELETE Customer data.
     * @param id ID of Customer to delete
     * */
    public static void delete(int id) throws SQLException {

        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();
    }
}
