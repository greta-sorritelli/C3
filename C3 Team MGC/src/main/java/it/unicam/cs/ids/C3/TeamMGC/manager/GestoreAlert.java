package it.unicam.cs.ids.C3.TeamMGC.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la gestione degli {@code Alert}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreAlert {
    private static GestoreAlert gestoreAlert;

    private GestoreAlert() {
    }

    /**
     * Metodo per ottenere l' istanza singleton del {@link GestoreAlert}
     *
     * @return l'unica istanza presente o una nuova se non è già esistente
     */
    public static GestoreAlert getInstance() {
        if (gestoreAlert == null)
            gestoreAlert = new GestoreAlert();
        return gestoreAlert;
    }

    /**
     * Ritorna i dettagli di un alert.
     *
     * @param ID              ID dell' utente a cui viene mandato l' alert
     * @param tipologiaUtente Tipologia dell' utente
     *
     * @return i dettagli dell' alert
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<ArrayList<String>> getDettagliAlert(int ID, String tipologiaUtente) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        switch (tipologiaUtente) {
            case "CLIENTE":
                getMessaggi(toReturn, "select * from alert_clienti where IDCliente ='" + ID + "';");
                break;
            case "MAGAZZINIERE":
                getMessaggi(toReturn, "select * from alert_magazzinieri where IDPuntoPrelievo ='" + ID + "';");
                break;
            case "CORRIERE":
                getMessaggi(toReturn, "select * from alert_corrieri where IDCorriere ='" + ID + "';");
                break;
        }
        return toReturn;
    }

    /**
     * Metodo per la rimozione di un alert.
     *
     * @param IDAlert         Id dell' alert da rimuovere
     * @param tipologiaUtente tipologia dell' utente a cui è stato mandato l' alert
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void deleteAlert(int IDAlert, String tipologiaUtente) throws SQLException {
        switch (tipologiaUtente) {
            case "CLIENTE":
                updateData("DELETE FROM sys.alert_clienti WHERE (ID = '" + IDAlert + "');");
                break;
            case "MAGAZZINIERE":
                updateData("DELETE FROM sys.alert_magazzinieri WHERE (ID = '" + IDAlert + "');");
                break;
            case "CORRIERE":
                updateData("DELETE FROM sys.alert_corrieri WHERE (ID = '" + IDAlert + "');");
                break;
        }
    }

    /**
     * Salva le informazioni degli alert.
     *
     * @param tmp   Arraylist in cui vengono salvate le informazioni
     * @param query query SQL con cui risalire agli alert nel DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    private void getMessaggi(ArrayList<ArrayList<String>> tmp, String query) throws SQLException {
        ResultSet rs = executeQuery(query);
        while (rs.next()) {
            ArrayList<String> toAdd = new ArrayList<>();
            toAdd.add(rs.getString("ID"));
            toAdd.add(rs.getString("messaggio"));
            tmp.add(toAdd);
        }
        disconnectToDB(rs);
    }
}