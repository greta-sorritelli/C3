package it.unicam.cs.ids.C3.TeamMGC.javaPercistence;

import java.sql.*;
import java.util.TimeZone;

/**
 * Classe per connettere il database MySQL
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class DatabaseConnection {

    /**
     * Connessione al DB
     *
     */
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

    /**
     * Esecuzione di una query SQL sul database
     * @param query query SQL
     * @return risultato della query
     * @throws SQLException eccezione causata dalla query
     */
    public static ResultSet executeQuery(String query) throws SQLException {
        return connectToDB().createStatement().executeQuery(query);
    }

    /**
     * Esecuzione di una query per aggiornare i dati del database
     * @param query query SQL
     * @throws SQLException eccezione causata dalla query
     */
    public static void updateData(String query) throws SQLException {
        connectToDB().createStatement().executeUpdate(query);
    }
}