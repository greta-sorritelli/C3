package it.unicam.cs.ids.C3.TeamMGC.corriere;

import it.unicam.cs.ids.C3.TeamMGC.manager.Gestore;
import it.unicam.cs.ids.C3.TeamMGC.manager.GestoreLogin;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la gestione di ogni {@link Corriere}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreCorrieri extends GestoreLogin implements Gestore<Corriere> {

    private static GestoreCorrieri gestoreCorrieri;
    ArrayList<Corriere> corrieri = new ArrayList<>();

    private GestoreCorrieri() {
    }

    /**
     * Metodo per ottenere l' istanza singleton del {@link GestoreCorrieri}.
     *
     * @return l'unica istanza presente o una nuova se non è già esistente
     */
    public static GestoreCorrieri getInstance() {
        if (gestoreCorrieri == null)
            gestoreCorrieri = new GestoreCorrieri();
        return gestoreCorrieri;
    }

    /**
     * Controlla se il {@link Corriere} che si vuole creare e' gia' presente nella lista dei corrieri.
     * Se non e' presente viene creato e aggiunto alla lista.
     *
     * @return Il corriere
     *
     * @throws SQLException Errore causato da una query SQL
     */
    private Corriere addCorriere(ResultSet rs) throws SQLException {
        for (Corriere simpleCorriere : corrieri)
            if (simpleCorriere.getID() == rs.getInt("ID")) {
                return simpleCorriere;
            }
        Corriere toReturn = new SimpleCorriere(rs.getInt("ID"));
        addCorriereToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link Corriere} alla lista di corrieri.
     *
     * @param corriere Corriere da aggiungere
     */
    private void addCorriereToList(Corriere corriere) {
        if (!corrieri.contains(corriere))
            corrieri.add(corriere);
    }

    /**
     * Ritorna la lista dei {@link Corriere Corrieri} {@code disponibili} presenti nel DB.
     *
     * @return ArrayList dei Corrieri disponibili.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<Corriere> getCorrieriDisponibili() throws SQLException {
        ArrayList<Corriere> corrieriDisponibili = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT ID FROM sys.corrieri WHERE (stato = 'true');");
        if (rs.next())
            do {
                Corriere tmp = new SimpleCorriere(rs.getInt("ID"));
                corrieriDisponibili.add(tmp);
            } while (rs.next());

        else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("Corrieri disponibili non presenti.");
        }
        disconnectToDB(rs);
        return corrieriDisponibili;
    }

    /**
     * Ritorna la lista dei dettagli dei {@link Corriere Corrieri} {@code disponibili} presenti nel DB.
     *
     * @return ArrayList di ArrayList dei dettagli dei Corrieri disponibili.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<ArrayList<String>> getDettagliCorrieriDisponibili() throws SQLException {
        ArrayList<Corriere> corrieriDisponibili = new ArrayList<>();
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT ID FROM sys.corrieri WHERE (stato = 'true' );");
        while (rs.next()) {
            Corriere tmp = new SimpleCorriere(rs.getInt("ID"));
            corrieriDisponibili.add(tmp);
        }
        if (corrieriDisponibili.isEmpty()) {
            disconnectToDB(rs);
            throw new IllegalArgumentException("Corrieri disponibili non presenti.");
        }
        for (Corriere simpleCorriere : corrieriDisponibili)
            dettagli.add(simpleCorriere.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    /**
     * Ritorna la lista dei dettagli dei {@link Corriere Corrieri} presenti nel DB.
     *
     * @return ArrayList di ArrayList dei dettagli dei Corrieri.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.corrieri;");
        while (rs.next())
            addCorriere(rs);
        for (Corriere corriere : corrieri)
            dettagli.add(corriere.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    //todo commento
    @Override
    public void reset() {
        corrieri.clear();
    }

    /**
     * Ritorna la Disponibilità del {@link Corriere} collegato all' {@code ID}.
     *
     * @param IDCorriere Codice Identificativo del Corriere
     *
     * @return la disponibilità del corriere
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public boolean getDisponibilita(int IDCorriere) throws SQLException {
        return getItem(IDCorriere).getDisponibilita();
    }

    /**
     * Ritorna il {@link Corriere} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Corriere
     *
     * @return Il Corriere desiderato
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public Corriere getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT ID FROM sys.corrieri where ID='" + ID + "' ;");
        if (rs.next()) {
            return addCorriere(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Ritorna la lista dei {@link Corriere Corrieri} presenti nel DB.
     *
     * @return ArrayList dei Corrieri.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<Corriere> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.corrieri;");
        while (rs.next())
            addCorriere(rs);
        disconnectToDB(rs);
        return new ArrayList<>(corrieri);
    }

    /**
     * Crea e inserisce un nuovo {@link Corriere} nella lista.
     *
     * @param nome    Nome del corriere da inserire
     * @param cognome Cognome del corriere da inserire
     *
     * @return ArrayList dei dettagli del corriere creato
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> inserisciDati(String nome, String cognome, String password) throws SQLException {
        Corriere simpleCorriere = new SimpleCorriere(nome, cognome, true);
        updateData("UPDATE sys.corrieri SET password = '" + password + "' WHERE (ID =" + simpleCorriere.getID() + ");");
        addCorriereToList(simpleCorriere);
        return simpleCorriere.getDettagli();
    }

    /**
     * Manda un alert al {@link Corriere} per andare ai negozi dove deve prelevare la {@link MerceOrdine}.
     *
     * @param IDCorriere ID del corriere
     * @param negozi     Negozi dove il corriere preleva la merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void mandaAlert(int IDCorriere, ArrayList<Negozio> negozi) throws SQLException {
        for (Negozio negozio : negozi)
            updateData("INSERT INTO sys.alert_corrieri (IDCorriere, messaggio) VALUES ('" + IDCorriere +
                    "', 'Andare al Negozio: " + negozio.getNome() + ", indirizzo: " + negozio.getIndirizzo()
                    + ", per ritirare le merci dei clienti.');");
    }

    /**
     * Manda un alert al {@link Corriere} per andare al negozio dove deve prelevare la {@link MerceOrdine} per poi
     * consegnarla in una residenza.
     *
     * @param IDCorriere ID del corriere
     * @param negozio    Negozio dove il corriere preleva la merce
     * @param residenza  Residenza dove il corriere consegna le merci
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void mandaAlert(int IDCorriere, Negozio negozio, String residenza) throws SQLException {
        updateData("INSERT INTO sys.alert_corrieri (IDCorriere, messaggio) VALUES ('" + IDCorriere +
                "', 'Andare al Negozio: " + negozio.getNome() + ", indirizzo: " + negozio.getIndirizzo()
                + ", per ritirare le merci dei cliente alla residenza: " + residenza + ".');");
    }

    public void setDisponibilita(int IDCorriere, boolean disponibilita) throws SQLException {
        getItem(IDCorriere).setDisponibilita(disponibilita);
    }

}