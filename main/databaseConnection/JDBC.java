package main.databaseConnection;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class that provides the details needed to connect to the database.
 * @author Wilmer Guzman 
 * */

public abstract class JDBC {

    private static Dotenv dotenv = Dotenv.load();
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = dotenv.get("DATABASE_NAME");
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = dotenv.get("DATABASE_USERNAME"); // Username
    private static final String password = dotenv.get("DATABASE_PASSWORD"); // Password
    public static Connection connection;  // Connection Interface

    /** Opens the connection to the database.*/
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /** Closes the connection to the database.*/
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**@return the connection*/
    public static Connection getConnection(){
        return connection;
    }
}