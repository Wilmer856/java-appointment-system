package main.databaseAccess;

import main.databaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that allows you to make STATE related SQL queries to the database.
 * @author Wilmer Guzman
 * */
public class FirstLevelDivAccess {

    /**
     * @return a list of all Divisions
     * */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() {

        ObservableList<FirstLevelDivisions> divisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                FirstLevelDivisions newDivision = new FirstLevelDivisions(divId, division, countryId);
                divisionList.add(newDivision);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return divisionList;
    }
}
