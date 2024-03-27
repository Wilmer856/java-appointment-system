package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 * Class that creates an Appointment object.
 * @author Wilmer Guzman
 * */
public class Appointments {

    private int id;
    private int userId;
    private int customerId;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;

    public Appointments(int id, int userId, int customerId, String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.userId = userId;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.start = start;
        this.end = end;
    }
    /**
     * @return the appointment id
     * */
    public int getId() {
        return id;
    }

    /**
     * @return the userId
     * */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the customerId
     * */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @return the title
     * */
    public String getTitle() {
        return title;
    }

    /**
     * @return the description
     * */
    public String getDescription() {
        return description;
    }

    /**
     * @return the location
     * */
    public String getLocation() {
        return location;
    }

    /**
     * @return the contactId
     * */
    public int getContactId() {
        return contactId;
    }

    /**
     * @return the type
     * */
    public String getType() {
        return type;
    }

    /**
     * @return the start
     * */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @return the end
     * */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @return list of types of appointments
     * */
    public static ObservableList<String> getAppTypeList(){
        ObservableList<String> types = FXCollections.observableArrayList();
        types.addAll("New Appointment", "Coffee Break", "Planning Session", "De-Briefing");

        return types;
    }
}
