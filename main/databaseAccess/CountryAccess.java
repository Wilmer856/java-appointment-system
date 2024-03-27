package main.databaseAccess;

import main.databaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that allows you to make COUNTRY related SQL queries to the database.
 * @author Wilmer Guzman
 * */
public class CountryAccess {

    /**
     * @return a list of all Countries
     * */
    public static ObservableList<Country> getAllCountries(){

        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Country_ID, Country FROM countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                Country country = new Country(id, name);
                countryList.add(country);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countryList;
    }
}
