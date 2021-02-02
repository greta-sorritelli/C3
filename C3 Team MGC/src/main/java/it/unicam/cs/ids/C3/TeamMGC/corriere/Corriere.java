package it.unicam.cs.ids.C3.TeamMGC.corriere;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire il comportamento di
 * un singolo Corriere. Permette di accedere e modificare le informazioni associate al corriere.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public interface Corriere extends Comparable<Corriere> {
    /**
     * Ritorna il Cognome del {@link Corriere}.
     *
     * @return il Cognome
     */
    String getCognome();

    /**
     * Imposta il nuovo Cognome del {@link Corriere}.
     *
     * @param cognome Nuovo cognome
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    void setCognome(String cognome) throws SQLException;

    /**
     * Ritorna un arraylist con i dettagli del {@link Corriere}.
     *
     * @return ArrayList dei dettagli
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    ArrayList<String> getDettagli() throws SQLException;

    /**
     * Ritorna la Disponibilità del {@link Corriere}.
     *
     * @return lo stato del corriere
     */
    boolean getDisponibilita();

    /**
     * Imposta la nuova Disponibilità del {@link Corriere}.
     *
     * @param disponibilita Nuovo valore della disponibilità
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    void setDisponibilita(boolean disponibilita) throws SQLException;

    /**
     * Ritorna il Codice Identificativo del {@link Corriere}.
     *
     * @return il Codice Identificativo
     */
    int getID();

    /**
     * Ritorna il Nome del {@link Corriere}.
     *
     * @return il Nome
     */
    String getNome();

    /**
     * Imposta il nuovo Nome del {@link Corriere}.
     *
     * @param nome Nuovo nome
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    void setNome(String nome) throws SQLException;

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void update() throws SQLException;
}
