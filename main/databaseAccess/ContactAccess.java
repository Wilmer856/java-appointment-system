package main.databaseAccess;

import main.databaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that allows you to make CONTACTS related SQL queries to the database.
 * @author Wilmer Guzman
 * */
public class ContactAccess {

    /**
     * @return a list of all contacts
     * */
    public static ObservableList<Contact> getAllContacts(){

        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact contact = new Contact(id, name, email);
                contactList.add(contact);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactList;
    }

}
