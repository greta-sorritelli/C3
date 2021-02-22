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

    /**
     * Ritorna i dettagli degli elementi gestiti.
     *
     * @return i dettagli
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<ArrayList<String>> getDettagliItems() throws SQLException;

    /**
     * Ritorna un elemento generico T con specifico ID.
     *
     * @param ID ID di riferimento
     *
     * @return Elemento con ID univoco
     *
     * @throws SQLException Errore causato da una query SQL
     */
    T getItem(int ID) throws SQLException;

    /**
     * Ritorna gli elementi di tipo generico T.
     *
     * @return elementi del tipo gestito dal Gestore
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<T> getItems() throws SQLException;

    /**
     * Esegue un reset.
     */
    void reset();
}