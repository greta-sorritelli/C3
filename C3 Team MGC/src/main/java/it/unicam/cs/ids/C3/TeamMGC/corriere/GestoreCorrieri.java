package it.unicam.cs.ids.C3.TeamMGC.corriere;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la gestione di ogni {@link Corriere}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreCorrieri implements Gestore<Corriere> {
    ArrayList<Corriere> corrieri = new ArrayList<>();

    /**
     * Controlla se il {@link Corriere} che si vuole creare e' gia' presente nella lista dei corrieri. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return Il corriere
     * @throws SQLException Errore causato da una query SQL
     */
    private Corriere addCorriere(ResultSet rs) throws SQLException {
        for (Corriere corriere : corrieri)
            if (corriere.getID() == rs.getInt("ID")) {
                return corriere;
            }
        Corriere toReturn = new Corriere(rs.getInt("ID"));
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
     * @return ArrayList<Cliente> dei Corrieri disponibili.
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<Corriere> getCorrieriDisponibili() throws SQLException {
        ArrayList<Corriere> corrieriDisponibili = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT ID FROM sys.corrieri WHERE (stato = 'true');");
        if (rs.next())
            do {
                Corriere tmp = new Corriere(rs.getInt("ID"));
                corrieriDisponibili.add(tmp);
            } while (rs.next());

        else {
            //todo eccezione
            disconnectToDB(rs);
            throw new IllegalArgumentException("Corrieri disponibili non presenti.");
        }
        disconnectToDB(rs);
        return corrieriDisponibili;
    }

    /**
     * Ritorna la lista dei dettagli dei {@link Corriere Corrieri} {@code disponibili} presenti nel DB.
     *
     * @return ArrayList<ArrayList < String>> dei dettagli dei Corrieri disponibili.
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<ArrayList<String>> getDettagliCorrieriDisponibili() throws SQLException {
        ArrayList<Corriere> corrieriDisponibili = new ArrayList<>();
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT ID FROM sys.corrieri WHERE (stato = 'true' );");
        while (rs.next()) {
            Corriere tmp = new Corriere(rs.getInt("ID"));
            corrieriDisponibili.add(tmp);
        }
        if (corrieriDisponibili.isEmpty()) {
            //todo eccezione
            disconnectToDB(rs);
            throw new IllegalArgumentException("Corrieri disponibili non presenti.");
        }
        for (Corriere corriere : corrieriDisponibili)
            dettagli.add(corriere.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    /**
     * Ritorna la lista dei dettagli dei {@link Corriere Corrieri} presenti nel DB.
     *
     * @return ArrayList<ArrayList < String>> dei dettagli dei Corrieri.
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.corrieri;");
        while (rs.next())
            addCorriere(rs);
        for (Corriere corrieri : corrieri)
            dettagli.add(corrieri.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    public boolean getDisponibilita(int IDCorriere) throws SQLException {
        return getItem(IDCorriere).getDisponibilita();
    }

    /**
     * Ritorna il {@link Corriere} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Corriere
     *
     * @return Il Corriere desiderato
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public Corriere getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.corrieri where ID='" + ID + "' ;");
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
     * @return ArrayList<Cliente> dei Corrieri.
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
     * @return ArrayList<String> dei dettagli del corriere creato
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> inserisciDati(String nome, String cognome) throws SQLException {
        Corriere corriere = new Corriere(nome, cognome, true);
        addCorriereToList(corriere);
        return corriere.getDettagli();
    }

    /**
     * Seleziona il {@link Corriere} desiderato.
     *
     * @param ID Codice Identificativo del Corriere
     *
     * @return Le informazioni del Corriere
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> selezionaCorriere(int ID) throws SQLException {
        return getItem(ID).getDettagli();
    }

    public void setDisponibilita(int IDCorriere, boolean disponibilita) throws SQLException {
        getItem(IDCorriere).setDisponibilita(disponibilita);
    }

    /**
     * todo alert per andare ai negozi dove prelevare la merce, mandato dal magazziniere e dal commesso
     *
     * @param negozi
     */
    public void mandaAlert(int IDCorriere, ArrayList<Negozio> negozi) throws SQLException {
        for (Negozio negozio : negozi)
            updateData("INSERT INTO sys.alert_corrieri (IDCorriere, messaggio) VALUES ('" + IDCorriere +
                    "', 'Andare al Negozio: " + negozio.getNome() + ", indirizzo: " + negozio.getIndirizzo()
                    + ", per ritirare le merci dei clienti.');");
    }

    /**
     * todo alert mandato dal commesso per prelevare la merce dal negozio
     */
    public void mandaAlert(int IDCorriere, Negozio negozio, String residenza) throws SQLException {
        updateData("INSERT INTO sys.alert_corrieri (IDCorriere, messaggio) VALUES ('" + IDCorriere +
                "', 'Andare al Negozio: " + negozio.getNome() + ", indirizzo: " + negozio.getIndirizzo()
                + ", per ritirare le merci dei cliente alla residenza: " + residenza + ".');");
    }
}