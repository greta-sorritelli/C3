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
     * @return la Connessione al DB
     *
     * @throws SQLException eccezione causata dalla query
     */
    public static Connection connectToDB() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sys?serverTimezone=" + TimeZone.getDefault().getID();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, "root", "root");
    }

    /**
     * Esecuzione di una query SQL sul database
     *
     * @param query query SQL
     *
     * @return risultato della query
     *
     * @throws SQLException eccezione causata dalla query
     */
    public static ResultSet executeQuery(String query) throws SQLException {
        return connectToDB().createStatement().executeQuery(query);
    }

    /**
     * Esecuzione di una query per aggiornare i dati del database
     *
     * @param query query SQL
     *
     * @throws SQLException eccezione causata dalla query
     */
    public static void updateData(String query) throws SQLException {
        Connection connection = connectToDB();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        disconnectToDB(connection, statement);
    }

    private static void disconnectToDB(Connection connection, Statement statement) throws SQLException {
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }

    /**
     * Termina la connessione con il database
     *
     * @param rs ResultSet in uso
     *
     * @throws SQLException Errore con il DB
     */
    public static void disconnectToDB(ResultSet rs) throws SQLException {
        Statement statement = rs.getStatement();
        Connection connection = statement.getConnection();
        rs.close();
        statement.close();
        if (connection != null)
            connection.close();
    }
}