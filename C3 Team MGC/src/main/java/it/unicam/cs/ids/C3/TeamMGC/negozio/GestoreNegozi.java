package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;
import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.disconnectToDB;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

/**
 * Classe per la gestione di ogni {@link GestoreInventario Negozio}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreNegozi implements Gestore<GestoreInventario> {

    private static GestoreNegozi gestoreNegozi;
    ArrayList<GestoreInventario> negozi = new ArrayList<>();

    private GestoreNegozi() {
    }

    public static GestoreNegozi getInstance() {
        if (gestoreNegozi == null)
            gestoreNegozi = new GestoreNegozi();
        return gestoreNegozi;
    }

    /**
     * Controlla se il {@link GestoreInventario Negozio} che si vuole creare e' gia' presente nella lista dei negozi. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return Il negozio
     * @throws SQLException Errore causato da una query SQL
     */
    private GestoreInventario addNegozio(ResultSet rs) throws SQLException {
        for (GestoreInventario negozio : negozi)
            if (negozio.getID() == rs.getInt("ID"))
                return negozio;
        GestoreInventario tmp = new Negozio(rs.getInt("ID"));
        addNegozioToList(tmp);
        return tmp;
    }

    /**
     * Aggiunge un {@link GestoreInventario Negozio} alla lista dei negozi.
     *
     * @param negozio Negozio da aggiungere
     */
    private void addNegozioToList(GestoreInventario negozio) {
        if (!negozi.contains(negozio))
            negozi.add(negozio);
    }

    /**
     * Ritorna la lista dei dettagli dei {@link GestoreInventario Negozio} presenti nel DB.
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
        for (GestoreInventario negozio : negozi)
            dettagli.add(negozio.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    /**
     * Ritorna la lista dei dettagli dei {@link GestoreInventario Negozio} che hanno ordini pagati da portare
     * ad un punto di prelievo.
     *
     * @param IDPuntoPrelievo ID del punto di prelievo
     *
     * @return ArrayList<ArrayList<String>> dei dettagli dei Negozi con ordini da ritirare.
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<ArrayList<String>> getDettagliItemsConOrdini(int IDPuntoPrelievo) throws SQLException {
        ResultSet rs = executeQuery("SELECT DISTINCT IDNegozio FROM sys.ordini where stato = '" + StatoOrdine.PAGATO +
                "' and IDPuntoPrelievo = '" + IDPuntoPrelievo + "' ;");
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        while (rs.next())
            toReturn.add(getItem(rs.getInt("IDNegozio")).getDettagli());
        disconnectToDB(rs);
        return toReturn;
    }

    /**
     * Ritorna il {@link GestoreInventario Negozio} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Negozio
     *
     * @return Il Negozio desiderato
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public GestoreInventario getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT ID FROM sys.negozi where ID='" + ID + "' ;");
        if (rs.next()) {
            GestoreInventario negozio = addNegozio(rs);
            disconnectToDB(rs);
            return negozio;
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Ritorna la lista dei {@link GestoreInventario Negozio} presenti nel DB.
     *
     * @return ArrayList<Negozi> dei Negozi.
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<GestoreInventario> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.negozi;");
        while (rs.next())
            addNegozio(rs);
        disconnectToDB(rs);
        return new ArrayList<>(negozi);
    }

//    /**
//     * todo
//     *
//     * @param ID
//     *
//     * @return
//     * @throws SQLException eccezione causata da una query SQL
//     */
//    //todo test e vedere se ritorna dettagli
//    public Negozio selezionaNegozio(int ID) throws SQLException {
//        return getItem(ID);
//    }
}
