package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

/**
 * Classe per la gestione di ogni {@link PuntoPrelievo}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreMagazzini implements Gestore<PuntoPrelievo> {

    ArrayList<PuntoPrelievo> magazzini = new ArrayList<>();

    //todo
//    public Magazziniere getMagazziniere(PuntoPrelievo magazzino) {
//		return magazzino.getMagazziniere();
//	}

    /**
     * Controlla se il {@link PuntoPrelievo} che si vuole creare e' gia' presente nella lista dei magazzini. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return Il Punto di prelievo
     * @throws SQLException eccezione causata da una query SQL
     */
    private PuntoPrelievo addMagazzino(ResultSet rs) throws SQLException {
        for (PuntoPrelievo magazzino : magazzini)
            if (magazzino.getID() == rs.getInt("ID"))
                return magazzino;
        PuntoPrelievo toReturn = new PuntoPrelievo(rs.getInt("ID"));
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
     * Ritorna la lista dei dettagli dei {@link PuntoPrelievo Magazzini} presenti nel DB.
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
        for (PuntoPrelievo magazzino : magazzini)
            dettagli.add(magazzino.getDettagli());
        return dettagli;
    }

    /**
     * Ritorna il {@link PuntoPrelievo Magazzino} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Punto di prelievo
     *
     * @return Il Punto di prelievo desiderato
     */
    @Override
    public PuntoPrelievo getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.punti_prelievo where ID='" + ID + "' ;");
        if (rs.next())
            return addMagazzino(rs);
        else
            throw new IllegalArgumentException("ID non valido.");
    }

    /**
     * Ritorna la lista dei {@link PuntoPrelievo Magazzini} presenti nel DB.
     *
     * @return ArrayList<PuntoPrelievo> dei Magazzini.
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<PuntoPrelievo> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.punti_prelievo;");
        while (rs.next())
            addMagazzino(rs);
        return new ArrayList<>(magazzini);
    }

    /**
     * todo
     *
     * @param magazzino
     */
    public void mandaAlert(PuntoPrelievo magazzino) {
        //todo alert al magazziniere
    }

    /**
     * Ricerca un {@link PuntoPrelievo Magazzino} tramite il suo indirizzo.
     *
     * @param indirizzo Indirizzo del Magazzino da ricercare
     *
     * @return il Punto di Prelievo collegato all' indirizzo
     * @throws SQLException Errore causato da una query SQL
     */
    public PuntoPrelievo ricercaMagazzino(String indirizzo) throws SQLException {
        ResultSet rs = executeQuery("SELECT ID FROM sys.punti_prelievo where indirizzo='" + indirizzo + "' ;");
        if (rs.next())
            return getItem(rs.getInt("ID"));
        else
            throw new IllegalArgumentException("Magazzino non trovato.");
    }

    /**
     * Ritorna i dettagli del {@link PuntoPrelievo}.
     *
     * @param ID  Codice identificativo del punto di prelievo
     *
     * @return    ArrayList<String> dei dettagli
     */
    public ArrayList<String> sceltaPuntoPrelievo(int ID) throws SQLException {
        return getItem(ID).getDettagli();
    }

}