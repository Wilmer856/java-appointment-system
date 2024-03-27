package main.model;

/**
 * Class that creates a Country object.
 * @author Wilmer Guzman
 * */
public class Country {
    private int countryID;
    private String countryName;


    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * @return the countryID
     * */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @return the countryName
     * */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @return the format to display the country
     * */
    @Override
    public String toString(){
        return countryID + " - " + countryName;
    }
}
