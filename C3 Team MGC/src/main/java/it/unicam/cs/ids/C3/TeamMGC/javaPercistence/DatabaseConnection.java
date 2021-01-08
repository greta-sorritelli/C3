package it.unicam.cs.ids.C3.TeamMGC.javaPercistence;

import java.sql.*;
import java.util.TimeZone;

/**
 * How to connect to a mySQL Database.
 *
 * @author Jon Bonso
 */
public class DatabaseConnection {
    public static Connection connectToDB() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sys?serverTimezone=" + TimeZone.getDefault().getID();
        //todo
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, "root", "root");
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        return connectToDB().createStatement().executeQuery(query);
    }

    public static void updateData(String query) throws SQLException {
        connectToDB().createStatement().executeUpdate(query);
    }
}