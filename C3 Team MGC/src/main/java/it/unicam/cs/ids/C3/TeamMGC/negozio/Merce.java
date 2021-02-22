package it.unicam.cs.ids.C3.TeamMGC.negozio;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire il comportamento di
 * una singola Merce presente nell' inventario di un {@link Negozio}.
 * Permette di accedere e modificare le informazioni associate alla merce.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public interface Merce extends Comparable<Merce> {

    /**
     * Elimina la {@link Merce} dal DB e aggiorna i dati dell' oggetto.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void delete() throws SQLException;

    /**
     * Ritorna la Descrizione della {@link Merce}.
     *
     * @return la descrizione della Merce
     */
    String getDescrizione();

    /**
     * Imposta la nuova Descrizione della {@link Merce}.
     *
     * @param descrizione Nuova descrizione
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    void setDescrizione(String descrizione) throws SQLException;

    /**
     * Ritorna la lista dei dettagli della {@link Merce } presente nel DB.
     *
     * @return ArrayList dei dettagli della merce.
     *
     * @throws SQLException Errore causato da una query SQL
     **/
    ArrayList<String> getDettagli() throws SQLException;

    /**
     * Ritorna il Codice Identificativo della {@link Merce}.
     *
     * @return il Codice Identificativo
     */
    int getID();

    /**
     * Ritorna il Codice Identificativo del {@link Negozio} della {@link Merce}.
     *
     * @return il Codice Identificativo del Negozio
     */
    int getIDNegozio();

    /**
     * Ritorna il Prezzo della {@link Merce}.
     *
     * @return il Prezzo
     */
    double getPrezzo();

    /**
     * Imposta il nuovo Prezzo della {@link Merce}.
     *
     * @param prezzo Nuovo prezzo
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    void setPrezzo(double prezzo) throws SQLException;

    /**
     * Ritorna la Quantità della {@link Merce}.
     *
     * @return la Quantità
     */
    int getQuantita();

    /**
     * Imposta la nuova Quantità della {@link Merce}.
     *
     * @param quantita Nuova quantità
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    void setQuantita(int quantita) throws SQLException;

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void update() throws SQLException;
}