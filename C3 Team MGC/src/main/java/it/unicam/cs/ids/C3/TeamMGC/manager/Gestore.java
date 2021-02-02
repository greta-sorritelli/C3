package it.unicam.cs.ids.C3.TeamMGC.manager;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire il comportamento di
 * un insieme di oggetti T.
 *
 * @param <T> Tipologia degli oggetti da gestire
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public interface Gestore<T> {

    ArrayList<ArrayList<String>> getDettagliItems() throws SQLException;

    T getItem(int ID) throws SQLException;

    ArrayList<T> getItems() throws SQLException;

    void reset();
}
