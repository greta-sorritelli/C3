package it.unicam.cs.ids.C3.TeamMGC.corriere;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

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
            if (corriere.getID() == rs.getInt("ID"))
                return corriere;
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
        ResultSet rs = executeQuery("SELECT ID FROM sys.corrieri WHERE (stato = 'true' );");
        if (rs.next())
            do {
                Corriere tmp = new Corriere(rs.getInt("ID"));
                corrieriDisponibili.add(tmp);
            } while (rs.next());

        else {
            //todo eccezione
            throw new IllegalArgumentException("Corrieri disponibili non presenti.");
        }
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
        if (corrieriDisponibili.isEmpty())
            //todo eccezione
            throw new IllegalArgumentException("Corrieri disponibili non presenti.");
        for (Corriere corriere : corrieriDisponibili)
            dettagli.add(corriere.getDettagli());
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
        return dettagli;
    }

    public boolean getDisponibilita(int IDCorriere) throws SQLException {
        return getItem(IDCorriere).getDisponibilita();
    }

    /**
     * Ritorna il {@link Corriere} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Corriere
     * @return Il Corriere desiderato
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public Corriere getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.corrieri where ID='" + ID + "' ;");
        if (rs.next())
            return addCorriere(rs);
        else
            throw new IllegalArgumentException("ID non valido.");
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
        return new ArrayList<>(corrieri);
    }

    /**
     * Crea e inserisce un nuovo {@link Corriere} nella lista.
     *
     * @param nome          Nome del corriere da inserire
     * @param cognome       Cognome del corriere da inserire
     * @param capienza      Capienza del corriere da inserire
     * @return              ArrayList<String> dei dettagli del corriere creato
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> inserisciDati(String nome, String cognome, int capienza) throws SQLException {
        Corriere corriere = new Corriere(nome, cognome, true, capienza);
        addCorriereToList(corriere);
        return corriere.getDettagli();
    }

    /**
     * Seleziona il {@link Corriere} desiderato.
     *
     * @param ID            Codice Identificativo del Corriere
     * @return              Le informazioni del Corriere
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> selezionaCorriere(int ID) throws SQLException {
        return getItem(ID).getDettagli();
    }

    public void setCapienza(int IDCorriere, int capienza) throws SQLException {
        getItem(IDCorriere).setCapienza(capienza);
    }

    public void setDisponibilita(int IDCorriere, boolean disponibilita) throws SQLException {
        getItem(IDCorriere).setDisponibilita(disponibilita);
    }

    /**
     * todo
     *
     * @param IDCorriere
     * @param negozi
     */
    public void mandaAlert(int IDCorriere, ArrayList<Negozio> negozi) {
        //todo manda alert al corriere con i negozi in cui prelevare merce
    }

    /**
     * todo
     *
     * @param IDCorriere
     * @param residenza
     */
    public void mandaAlert(int IDCorriere, String residenza) {
        //todo manda alert quando il commesso seleziona un corriere per andare alla residenza
    }
}