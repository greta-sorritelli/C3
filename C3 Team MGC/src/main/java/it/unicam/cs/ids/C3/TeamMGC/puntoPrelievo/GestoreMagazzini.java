package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.manager.Gestore;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la gestione di ogni {@link PuntoPrelievo}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreMagazzini implements Gestore<PuntoPrelievo> {

    private static GestoreMagazzini gestoreMagazzini;
    ArrayList<PuntoPrelievo> magazzini = new ArrayList<>();

    private GestoreMagazzini() {
    }

    /**
     * Metodo per ottenere l' istanza singleton del {@link GestoreMagazzini}
     *
     * @return l'unica istanza presente o una nuova se non è già esistente
     */
    public static GestoreMagazzini getInstance() {
        if (gestoreMagazzini == null)
            gestoreMagazzini = new GestoreMagazzini();
        return gestoreMagazzini;
    }

    /**
     * Controlla se il {@link PuntoPrelievo} che si vuole creare e' gia' presente nella lista dei magazzini.
     * Se non e' presente viene creato e aggiunto alla lista.
     *
     * @return Il Punto di prelievo
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    private PuntoPrelievo addMagazzino(ResultSet rs) throws SQLException {
        for (PuntoPrelievo magazzino : magazzini)
            if (magazzino.getID() == rs.getInt("ID"))
                return magazzino;
        PuntoPrelievo toReturn = new SimplePuntoPrelievo(rs.getInt("ID"));
        addMagazzinoToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link PuntoPrelievo} alla lista di magazzini.
     *
     * @param magazzino Punto di prelievo da aggiungere
     */
    private void addMagazzinoToList(PuntoPrelievo magazzino) {
        if (!magazzini.contains(magazzino))
            magazzini.add(magazzino);
    }


    /**
     * Ritorna la lista dei dettagli dei {@link PuntoPrelievo Punti di Prelievo} presenti nel DB.
     *
     * @return ArrayList di ArrayList dei dettagli dei Magazzini.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.punti_prelievo;");
        while (rs.next())
            addMagazzino(rs);
        for (PuntoPrelievo magazzino : magazzini)
            dettagli.add(magazzino.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    /**
     * Ritorna il {@link PuntoPrelievo} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Punto di prelievo
     *
     * @return Il Punto di prelievo desiderato
     */
    @Override
    public PuntoPrelievo getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT ID FROM sys.punti_prelievo where ID='" + ID + "' ;");
        if (rs.next()) {
            PuntoPrelievo toReturn = addMagazzino(rs);
            disconnectToDB(rs);
            return toReturn;
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Ritorna la lista dei {@link PuntoPrelievo Punti di Prelievo} presenti nel DB.
     *
     * @return ArrayList dei Punti di Prelievo.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<PuntoPrelievo> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.punti_prelievo;");
        while (rs.next())
            addMagazzino(rs);
        disconnectToDB(rs);
        return new ArrayList<>(magazzini);
    }

    /**
     * Manda un alert al {@link PuntoPrelievo} per avvisare il magazziniere che serve un corriere per
     * ritirare le merci presso un negozio.
     *
     * @param IDPuntoPrelievo ID del punto prelievo in cui è presente in magazziniere
     * @param negozio         Negozio in cui deve andare il corriere
     */
    public void mandaAlert(int IDPuntoPrelievo, Negozio negozio) throws SQLException {
        updateData("INSERT INTO sys.alert_magazzinieri (IDPuntoPrelievo, messaggio) VALUES ('" + IDPuntoPrelievo +
                "', 'Mandare un corriere al negozio: " + negozio.getNome() + ", indirizzo: " + negozio.getIndirizzo() + ", per " +
                "prelevare la merce.');");
    }

    /**
     * Ricerca i {@link PuntoPrelievo Punti di prelievo} più vicini.
     *
     * @return ArrayList dei punti di prelievo
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<PuntoPrelievo> ricercaMagazziniVicini() throws SQLException {
        return getItems();
    }

}