package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.disconnectToDB;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

/**
 * Classe per la gestione di ogni {@link Negozio}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreNegozi implements Gestore<Negozio> {

    ArrayList<Negozio> negozi = new ArrayList<>();

    /**
     * Controlla se il {@link Negozio} che si vuole creare e' gia' presente nella lista dei negozi. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return Il negozio
     * @throws SQLException Errore causato da una query SQL
     */
    //todo controllare costruttore negozio
    private Negozio addNegozio(ResultSet rs) throws SQLException {
        for (Negozio negozio : negozi)
            if (negozio.getID() == rs.getInt("ID"))
                return negozio;
        Negozio tmp = new Negozio(rs.getInt("ID"));
        addNegozioToList(tmp);
        return tmp;
    }

    /**
     * Aggiunge un {@link Negozio} alla lista dei negozi.
     *
     * @param negozio Negozio da aggiungere
     */
    private void addNegozioToList(Negozio negozio) {
        if (!negozi.contains(negozio))
            negozi.add(negozio);
    }

    /**
     * Ritorna la lista dei dettagli dei {@link Negozio Negozi} presenti nel DB.
     *
     * @return ArrayList<ArrayList < String>> dei dettagli dei Negozi.
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.negozi;");
        while (rs.next())
            addNegozio(rs);
        for (Negozio negozio : negozi)
            dettagli.add(negozio.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    /**
     * Ritorna la lista dei dettagli dei {@link Negozio Negozi} on ordini da ritirare presenti nel DB.
     *
     * @return ArrayList<ArrayList < String>> dei dettagli dei Negozi on ordini da ritirare.
     * @throws SQLException Errore causato da una query SQL
     */
    //todo controllare problema Franco e Clarissa
    //todo finire test
    public ArrayList<ArrayList<String>> getDettagliItemsConOrdini() throws SQLException {
        ResultSet rs = executeQuery("SELECT IDNegozio FROM sys.ordini where stato = '" + StatoOrdine.PAGATO + "' ;");
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        while (rs.next())
            toReturn.add(getItem(rs.getInt("IDNegozio")).getDettagli());
        disconnectToDB(rs);
        return toReturn;
    }

    /**
     * Ritorna il {@link Negozio} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Negozio
     *
     * @return Il Negozio desiderato
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public Negozio getItem(int ID) throws SQLException {
        //todo
        ResultSet rs = executeQuery("SELECT * FROM sys.negozi where ID='" + ID + "' ;");
        if (rs.next()) {
            Negozio negozio = addNegozio(rs);
            disconnectToDB(rs);
            return negozio;
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Ritorna la lista dei {@link Negozio Negozi} presenti nel DB.
     *
     * @return ArrayList<Negozi> dei Negozi.
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    //todo controllare test
    public ArrayList<Negozio> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.negozi;");
        while (rs.next())
            addNegozio(rs);
        disconnectToDB(rs);
        return new ArrayList<>(negozi);
    }

    /**
     * todo
     *
     * @param ID
     *
     * @return
     * @throws SQLException eccezione causata da una query SQL
     */
    //todo test e vedere se ritorna dettagli
    public Negozio selezionaNegozio(int ID) throws SQLException {
        return getItem(ID);
    }
}
