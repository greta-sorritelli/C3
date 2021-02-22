package it.unicam.cs.ids.C3.TeamMGC.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe astratta per la gestione del {@code Login}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public abstract class GestoreLogin {

    /**
     * Controlla se l'ID e la password inseriti sono corretti.
     *
     * @param tipologiaUtente tipologia di utente
     * @param ID              ID dell' utente
     * @param password        password dell' utente
     *
     * @return true se le informazioni sono giuste, false altrimenti
     *
     * @throws SQLException Errore causato da una query SQL
     */
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
     * Controlla se l'ID e la password e l'ID della sede di lavoro inseriti sono corretti.
     *
     * @param tipologiaUtente tipologia dell' utente
     * @param ID              ID dell' utente
     * @param password        password dell' utente
     * @param IDSedeLavoro    ID della sede di lavoro
     *
     * @return true se le informazioni sono giuste, false altrimenti
     *
     * @throws SQLException Errore causato da una query SQL
     */
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
     * Controlla se le informazioni di accesso per l' amministratore del sistema sono giuste.
     *
     * @param nomeUtente nome utente dell' amministratore
     * @param password   password dell' amministratore
     *
     * @return true se le informazioni sono giuste, false altrimenti
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public static boolean checkInfo(String nomeUtente, String password) throws SQLException {
        ResultSet rs = executeQuery("SELECT nomeUtente FROM sys.amministratore WHERE nomeUtente = '" + nomeUtente + "' AND password = '" + password + "';");
        boolean check = rs.next();
        disconnectToDB(rs);
        return check;
    }
}