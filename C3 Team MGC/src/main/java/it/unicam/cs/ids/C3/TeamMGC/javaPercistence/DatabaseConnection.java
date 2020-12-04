package it.unicam.cs.ids.C3.TeamMGC.javaPercistence;

import java.sql.*;
import java.util.TimeZone;

/**
 * How to connect to a mySQL Database.
 *
 * @author Jon Bonso
 */
public class DatabaseConnection {

    /**
     * Connect to MySQL Database
     *
     * @throws SQLException
     */
    private static void connectToDB() throws SQLException {

        // 1. Get the Connection instance using the DriverManager.getConnection() method
        //    with your MySQL Database Credentails

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Local",
                "root", "root");

        System.out.println("LOG: Connection Established!");

        // 2. Execute your SQL Query using conn.createStatement.executeQuery()
        //    and get the result as a ResultSet object.
        //    with your MySQL Database Credentails
        ResultSet rs = conn.createStatement().executeQuery("select now()");
        ResultSetMetaData rsmd = rs.getMetaData();

        System.out.println("Query Results: \n\n");

        // Show Column Names
        getColumnNames(rsmd);

        // Getting the Results
        while (rs.next()) {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(rs.getString(i) + "\t\t");
            }

            System.out.println();
        }
    }

    public static void updateData(String query) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sys?serverTimezone=" + TimeZone.getDefault().getID();
        //todo
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(url, "root", "root");
        conn.createStatement().executeUpdate(query);
    }

    /**
     * Shows the Column Names
     *
     * @param rsmd
     * @throws SQLException
     */
    private static void getColumnNames(ResultSetMetaData rsmd) throws SQLException {
        // Getting the list of COLUMN Names
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            System.out.print(rsmd.getColumnName(i) + "\t\t|");
        }

        System.out.println("");
    }
}