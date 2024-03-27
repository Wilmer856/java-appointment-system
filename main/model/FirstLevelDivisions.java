package main.model;

/**
 * Class that creates a FirstLevelDivisions object.
 * @author Wilmer Guzman
 * */
public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;
    private int country_ID;

    public FirstLevelDivisions(int divisionID, String divisionName, int country_ID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.country_ID = country_ID;
    }

    /**
     * @return the divisionID
     * */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @return the divisionName
     * */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @return country_ID
     * */
    public int getCountry_ID() {
        return country_ID;
    }

    /**
     * @return the format to display the division
     * */
    @Override
    public String toString(){
        return divisionID + " - " + divisionName;
    }
}
