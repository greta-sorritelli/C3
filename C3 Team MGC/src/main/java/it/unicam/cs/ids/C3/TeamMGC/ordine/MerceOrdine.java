package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire il comportamento di
 * una singola Merce di un {@link Ordine}. Permette di accedere e modificare le informazioni
 * associate alla merce.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public interface MerceOrdine extends Comparable<MerceOrdine> {

    /**
     * Ritorna la Descrizione della {@link MerceOrdine}.
     *
     * @return la descrizione della merce
     */
    String getDescrizione();

    /**
     * Imposta la Descrizione alla {@link MerceOrdine}.
     *
     * @param descrizione Nuova Descrizione
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    void setDescrizione(String descrizione) throws SQLException;

    /**
     * Ritorna un arraylist con i dettagli della {@link MerceOrdine}.
     *
     * @return ArrayList dei dettagli della merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<String> getDettagli() throws SQLException;

    /**
     * Ritorna il Codice Identificativo della {@link MerceOrdine}.
     *
     * @return l'ID della merce
     */
    int getID();

    /**
     * Ritorna il Codice Identificativo del {@link Corriere} che trasporta la {@link MerceOrdine}.
     *
     * @return l'ID del corriere
     */
    int getIDCorriere();

    /**
     * Associa il {@link Corriere} alla {@link MerceOrdine}.
     *
     * @param IDCorriere Codice Identificativo del Corriere
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    void setIDCorriere(int IDCorriere) throws SQLException;

    /**
     * Ritorna il Codice Identificativo dell' {@link Ordine} della {@link MerceOrdine}.
     *
     * @return l'ID dell' Ordine
     */
    int getIDOrdine();

    /**
     * Associa l' {@link Ordine} alla {@link MerceOrdine}.
     *
     * @param IDOrdine Codice Identificativo dell' Ordine
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    void setIDOrdine(int IDOrdine) throws SQLException;

    /**
     * Ritorna il Prezzo della {@link MerceOrdine}.
     *
     * @return il prezzo della merce
     */
    double getPrezzo();

    /**
     * Imposta il Prezzo alla {@link MerceOrdine}.
     *
     * @param prezzo Nuovo Prezzo
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    void setPrezzo(double prezzo) throws SQLException;

    /**
     * Ritorna la Quantità della {@link MerceOrdine}.
     *
     * @return la quantità della merce
     */
    int getQuantita();

    /**
     * Imposta la Quantità alla {@link MerceOrdine}.
     *
     * @param quantita Nuova Quantità
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    void setQuantita(int quantita) throws SQLException;

    /**
     * Ritorna lo {@link StatoOrdine Stato} della {@link MerceOrdine}.
     *
     * @return lo stato della merce
     */
    StatoOrdine getStato();

    /**
     * Imposta lo {@link StatoOrdine Stato} alla {@link MerceOrdine}.
     *
     * @param stato Stato della Merce
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    void setStato(StatoOrdine stato) throws SQLException;

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void update() throws SQLException;
}