package main.model;

/**
 * Class that creates a Users object.
 * @author Wilmer Guzman
 * */
public class Users {

    private String username;
    private String password;
    private int id;

    public Users(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    /**
     * @return the username
     * */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     * */
    public String getPassword() {
        return password;
    }

    /**
     * @return the id
     * */
    public int getId() {
        return id;
    }

}
