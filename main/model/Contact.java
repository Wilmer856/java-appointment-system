package main.model;


/**
 * Class that creates a Contact object.
 * @author Wilmer Guzman
 * */
public class Contact {

    public int id;
    public String name;
    public String email;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * @return the id
     * */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     * */
    public String getName() {
        return name;
    }

    /**
     * @return the email
     * */
    public String getEmail() {
        return email;
    }

    /**
     * @return the format to display the contact
     * */
    @Override
    public String toString(){
        return id + " - " + name;
    }
}
