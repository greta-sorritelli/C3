package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreInventario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la gestione di ogni {@link SimplePuntoPrelievo}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreMagazzini implements Gestore<SimplePuntoPrelievo> {

    private static GestoreMagazzini gestoreMagazzini;
    ArrayList<SimplePuntoPrelievo> magazzini = new ArrayList<>();

    private GestoreMagazzini() {
    }

    public static GestoreMagazzini getInstance() {
        if (gestoreMagazzini == null)
            gestoreMagazzini = new GestoreMagazzini();
        return gestoreMagazzini;
    }

    /**
     * Controlla se il {@link SimplePuntoPrelievo} che si vuole creare e' gia' presente nella lista dei magazzini. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return Il Punto di prelievo
     * @throws SQLException eccezione causata da una query SQL
     */
    private SimplePuntoPrelievo addMagazzino(ResultSet rs) throws SQLException {
        for (SimplePuntoPrelievo magazzino : magazzini)
            if (magazzino.getID() == rs.getInt("ID"))
                return magazzino;
        SimplePuntoPrelievo toReturn = new SimplePuntoPrelievo(rs.getInt("ID"));
        addMagazzinoToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link SimplePuntoPrelievo} alla lista di magazzini.
     *
     * @param magazzino Punto di prelievo da aggiungere
     */
    private void addMagazzinoToList(SimplePuntoPrelievo magazzino) {
        if (!magazzini.contains(magazzino))
            magazzini.add(magazzino);
    }


    /**
     * Ritorna la lista dei dettagli dei {@link SimplePuntoPrelievo Magazzini} presenti nel DB.
     *
     * @return ArrayList<ArrayList < String>> dei dettagli dei Magazzini.
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.punti_prelievo;");
        while (rs.next())
            addMagazzino(rs);
        for (SimplePuntoPrelievo magazzino : magazzini)
            dettagli.add(magazzino.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    /**
     * Ritorna il {@link SimplePuntoPrelievo Magazzino} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Punto di prelievo
     *
     * @return Il Punto di prelievo desiderato
     */
    @Override
    public SimplePuntoPrelievo getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT ID FROM sys.punti_prelievo where ID='" + ID + "' ;");
        if (rs.next()) {
            SimplePuntoPrelievo toReturn = addMagazzino(rs);
            disconnectToDB(rs);
            return toReturn;
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Ritorna la lista dei {@link SimplePuntoPrelievo Magazzini} presenti nel DB.
     *
     * @return ArrayList<PuntoPrelievo> dei Magazzini.
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<SimplePuntoPrelievo> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.punti_prelievo;");
        while (rs.next())
            addMagazzino(rs);
        disconnectToDB(rs);
        return new ArrayList<>(magazzini);
    }

    /**
     * Manda un alert al punto di prelievo per avvisare il magazziniere che serve un corriere per
     * ritirare le merci presso un negozio.
     *  @param IDPuntoPrelievo ID del punto prelievo in cui è presente in magazziniere
     * @param negozio         Negozio in cui deve andare il corriere
     */
    public void mandaAlert(int IDPuntoPrelievo, GestoreInventario negozio) throws SQLException {
        updateData("INSERT INTO sys.alert_magazzinieri (IDPuntoPrelievo, messaggio) VALUES ('" + IDPuntoPrelievo +
                "', 'Mandare un corriere al negozio: " + negozio.getNome() + ", indirizzo: " + negozio.getIndirizzo() + ", per " +
                "prelevare la merce.');");
    }

    /**
     * Ricerca i {@link SimplePuntoPrelievo Punti prelievo} più vicini.
     *
     * @throws SQLException Errore causato da una query SQL
     * @return              ArrayList<ArrayList<String>>
     */
    public ArrayList<SimplePuntoPrelievo> ricercaMagazziniVicini() throws SQLException {
        return getItems();
    }

    /**
     * Ritorna i dettagli del {@link SimplePuntoPrelievo}.
     *
     * @param ID Codice identificativo del punto di prelievo
     *
     * @return ArrayList<String> dei dettagli
     */
    public ArrayList<String> sceltaPuntoPrelievo(int ID) throws SQLException {
        return getItem(ID).getDettagli();
    }

}