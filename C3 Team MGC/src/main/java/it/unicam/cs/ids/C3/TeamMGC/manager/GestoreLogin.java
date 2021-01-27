package it.unicam.cs.ids.C3.TeamMGC.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

public abstract  class GestoreLogin {

    /**
     *
     * @param tipologiaUtente
     * @param ID
     * @param password
     * @return
     * @throws SQLException
     */
    //todo test e commento
    public boolean checkInfo(String tipologiaUtente, int ID, String password) throws SQLException {
        String query;
        switch (tipologiaUtente) {
            case "CLIENTE":
                query = "SELECT ID FROM sys.clienti WHERE ID = " + ID + " AND password = '" + password + "';";
                break;
            case "CORRIERE":
                query = "SELECT ID FROM sys.corrieri WHERE ID = " + ID + " AND password = '" + password + "';";
                break;
            case "MAGAZZINIERE":
                query = "SELECT ID FROM sys.magazzinieri WHERE IDPuntoPrelievo = " + ID + " AND password = '" + password + "';";
                break;
            default:
                throw new IllegalStateException("Valore non corretto: " + tipologiaUtente);
        }
        ResultSet rs = executeQuery(query);
        boolean check = rs.next();
        disconnectToDB(rs);
        return check;
    }

    /**
     *
     * @param tipologiaUtente
     * @param ID
     * @param password
     * @param IDSedeLavoro
     * @return
     * @throws SQLException
     */
    //todo test e commento
    public boolean checkInfo(String tipologiaUtente, int ID, String password, int IDSedeLavoro) throws SQLException {
        String query;
        switch (tipologiaUtente) {
            case "COMMERCIANTE":
            case "COMMESSO":
            case "ADDETTO_MAGAZZINO":
                query = "SELECT ID FROM sys.personale WHERE ID = " + ID + " AND IDNegozio = " + IDSedeLavoro + " AND password = '" + password + "';";
                break;
            default:
                throw new IllegalStateException("Valore non corretto: " + tipologiaUtente);
        }
        ResultSet rs = executeQuery(query);
        boolean check = rs.next();
        disconnectToDB(rs);
        return check;
    }

    /**
     *
     * @param nomeUtente
     * @param password
     * @return
     * @throws SQLException
     */
    //todo test e commento
    public static boolean checkInfo(String nomeUtente, String password) throws SQLException {
        ResultSet rs = executeQuery("SELECT nomeUtente FROM sys.amministratore WHERE nomeUtente = '" + nomeUtente + "' AND password = '" + password + "';");
        boolean check = rs.next();
        disconnectToDB(rs);
        return check;
    }

}
