package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.manager.Gestore;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la gestione di ogni {@link Negozio}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreNegozi implements Gestore<Negozio> {

    private static GestoreNegozi gestoreNegozi;
    ArrayList<Negozio> negozi = new ArrayList<>();

    private GestoreNegozi() {
    }

    /**
     * Metodo per ottenere l' istanza singleton del {@link GestoreNegozi}
     *
     * @return l'unica istanza presente o una nuova se non è già esistente
     */
    public static GestoreNegozi getInstance() {
        if (gestoreNegozi == null)
            gestoreNegozi = new GestoreNegozi();
        return gestoreNegozi;
    }

    /**
     * Controlla se il {@link Negozio} che si vuole creare e' gia' presente nella lista dei negozi.
     * Se non e' presente viene creato e aggiunto alla lista.
     *
     * @return Il negozio
     *
     * @throws SQLException Errore causato da una query SQL
     */
    private Negozio addNegozio(ResultSet rs) throws SQLException {
        for (Negozio negozio : negozi)
            if (negozio.getID() == rs.getInt("ID"))
                return negozio;
        Negozio tmp = new SimpleNegozio(rs.getInt("ID"));
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
     * @return ArrayList di ArrayList dei dettagli dei Negozi.
     *
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
     * Ritorna la lista dei dettagli dei {@link Negozio Negozi} con una certa categoria presenti nel DB.
     *
     * @param categoria Categoria del negozio.
     *
     * @return ArrayList di ArrayList dei dettagli dei Negozi.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<ArrayList<String>> getDettagliItems(CategoriaNegozio categoria) throws SQLException {
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.negozi WHERE categoria= '" + categoria + "';");
        while (rs.next())
            addNegozio(rs);
        ArrayList<Negozio> tmp = negozi.stream().filter(negozio -> negozio.getCategoria().equals(categoria)).collect(Collectors.toCollection(ArrayList::new));
        for (Negozio negozio : tmp)
            dettagli.add(negozio.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    /**
     * Ritorna la lista dei dettagli dei {@link Negozio Negozi} con una certa categoria e con delle promozioni presenti nel DB.
     *
     * @param categoria Categoria del negozio.
     *
     * @return ArrayList di ArrayList dei dettagli dei Negozi.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<ArrayList<String>> getDettagliItemsWithPromozioni(CategoriaNegozio categoria) throws SQLException {
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT ID FROM sys.negozi inner join promozioni on ID = IDNegozio Where categoria = '" + categoria + "' ;");
        while (rs.next())
            addNegozio(rs);
        ArrayList<Negozio> tmp = negozi.stream().filter(negozio -> negozio.getCategoria().equals(categoria)).collect(Collectors.toCollection(ArrayList::new));
        for (Negozio negozio : tmp)
            if (!negozio.getDettagliPromozioni().isEmpty())
                dettagli.add(negozio.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    /**
     * Ritorna la lista dei dettagli dei {@link Negozio} che hanno ordini pagati da portare
     * ad un punto di prelievo.
     *
     * @param IDPuntoPrelievo ID del punto di prelievo
     *
     * @return ArrayList di ArrayList dei dettagli dei Negozi con ordini da ritirare.
     *
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
     * Ritorna il {@link Negozio} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Negozio
     *
     * @return Il Negozio desiderato
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public Negozio getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT ID FROM sys.negozi where ID='" + ID + "' ;");
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
     * Ritorna la lista dei {@link Negozio Negozio} presenti nel DB.
     *
     * @return ArrayList dei Negozi.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<Negozio> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.negozi;");
        while (rs.next())
            addNegozio(rs);
        disconnectToDB(rs);
        return new ArrayList<>(negozi);
    }

    /**
     * Crea e inserisce un nuovo {@link Negozio} nella lista.
     *
     * @param nome           Nome del negozio
     * @param categoria      Categoria del negozio
     * @param indirizzo      Indirizzo del negozio
     * @param telefono       Recapito del negozio
     * @param orarioApertura Orario di apertura del negozio
     * @param orarioChiusura Orario di chiusura del negozio
     *
     * @return ArrayList dei dettagli del Negozio.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> inserisciNegozio(String nome, CategoriaNegozio categoria, String indirizzo, String telefono, String orarioApertura, String orarioChiusura) throws SQLException {
        Negozio simpleNegozio = new SimpleNegozio(nome, categoria, orarioApertura, orarioChiusura, indirizzo, telefono);
        addNegozioToList(simpleNegozio);
        return simpleNegozio.getDettagli();
    }

    /**
     * Rimuove il {@link Negozio} dalla lista di negozi.
     *
     * @param IDNegozio ID del Negozio da rimuovere.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void removeNegozio(int IDNegozio) throws SQLException {
        Negozio simpleNegozio = getItem(IDNegozio);
        negozi.remove(simpleNegozio);
        simpleNegozio.delete();
    }

}
