package main.databaseAccess;

import main.databaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that allows you to make USER related SQL queries to the database.
 * @author Wilmer Guzman
 * */
public class UserAccess {

    /**
     * @return a list of all Users
     * */
    public static ObservableList<Users> getAllUsers(){

        ObservableList<Users> userList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT User_Name, Password, User_ID FROM users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                 String username = rs.getString("User_Name");
                 String password = rs.getString("Password");
                 int userId = rs.getInt("User_ID");

                 Users user = new Users(username, password, userId);
                 userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
}
