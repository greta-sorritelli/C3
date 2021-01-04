package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

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
     * @throws SQLException
     */
    private PuntoPrelievo addMagazzino(ResultSet rs) throws SQLException {
        for (PuntoPrelievo magazzino : magazzini)
            if (magazzino.getID() == rs.getInt("ID"))
                return magazzino;
        PuntoPrelievo toReturn = new PuntoPrelievo(rs.getInt("ID"), rs.getString("indirizzo"),
                rs.getString("nome"));
        addMagazzinoToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link PuntoPrelievo} alla lista di magazzini.
     *
     * @param magazzino Punto di prelievo da aggiungere
     * @return {@code true} se il Punto di prelievo viene inserito correttamente, {@code false} altrimenti
     */
    private boolean addMagazzinoToList(PuntoPrelievo magazzino) {
        if (!magazzini.contains(magazzino))
            return magazzini.add(magazzino);
        else
            return false;
    }


    /**
     * @return ArrayList<ArrayList < String>> dei dettagli dei magazzini disponibili.
     */
    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() {
        try {
            ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * FROM sys.punti_prelievo;");
            while (rs.next())
                addMagazzino(rs);
            for (PuntoPrelievo magazzino : magazzini)
                dettagli.add(magazzino.getDettagli());
            return dettagli;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * @return l'elenco dei Magazzini Disponibili
     */
    @Override
    public ArrayList<PuntoPrelievo> getItems() {
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.punti_prelievo;");
            while (rs.next())
                addMagazzino(rs);
            return new ArrayList<>(magazzini);
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Ritorna il {@link PuntoPrelievo} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Punto di prelievo
     * @return Il Punto di prelievo desiderato
     */
    @Override
    public PuntoPrelievo getItem(int ID) {
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.punti_prelievo where ID='" + ID + "' ;");
            if (rs.next())
                return addMagazzino(rs);
            else
                //todo eccezione
                throw new IllegalArgumentException();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void mandaAlert(PuntoPrelievo magazzino) {
        //todo
    }

    /**
     * @param ID
     * @return
     */
    public PuntoPrelievo sceltaPuntoPrelievo(int ID) {

        return getItem(ID);
    }

    public void selezionaPuntoPrelievo(int ID) {
        //todo
    }

    public PuntoPrelievo ricercaMagazzinoVicino(String indirizzo) {
        //todo
        return null;
    }

}