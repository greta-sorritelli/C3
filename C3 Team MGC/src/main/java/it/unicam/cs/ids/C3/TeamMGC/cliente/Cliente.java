package it.unicam.cs.ids.C3.TeamMGC.cliente;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire il comportamento di
 * un singolo Cliente. Permette di accedere e modificare le informazioni associate al cliente.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public interface Cliente extends Comparable<Cliente> {

    /**
     * Ritorna il Codice di Ritiro collegato al {@link Cliente}.
     *
     * @return il Codice di Ritiro
     */
    String getCodiceRitiro();

    /**
     * Ritorna il Cognome del {@link Cliente}.
     *
     * @return il Cognome
     */
    String getCognome();

    /**
     * Ritorna la Data di Creazione del {@code Codice di Ritiro} collegato al {@link Cliente}.
     *
     * @return la data di creazione del Codice
     */
    String getDataCreazioneCodice();

    /**
     * Ritorna un arraylist con i dettagli del {@link Cliente}.
     *
     * @return ArrayList dei dettagli
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<String> getDettagli() throws SQLException;

    /**
     * Ritorna il Codice Identificativo del {@link Cliente}.
     *
     * @return il Codice Identificativo
     */
    int getID();

    /**
     * Ritorna il Nome del {@link Cliente}.
     *
     * @return il Nome
     */
    String getNome();

    /**
     * Crea un nuovo codice di ritiro e lo associa alla data in cui il {@link Cliente} effettua un acquisto.
     *
     * @param codiceRitiro Nuovo Codice di Ritiro
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void setCodiceRitiro(String codiceRitiro) throws SQLException;

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void update() throws SQLException;
}