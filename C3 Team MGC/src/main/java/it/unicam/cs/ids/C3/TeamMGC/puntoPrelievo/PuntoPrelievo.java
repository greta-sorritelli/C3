package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire il comportamento di
 * un singolo Punto di Prelievo. Permette di accedere alle informazioni associate all' ordine.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public interface PuntoPrelievo extends Comparable<PuntoPrelievo>{

    /**
     * Elimina il {@link PuntoPrelievo} dal DB e aggiorna i dati dell' oggetto.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void delete() throws SQLException;

    /**
     * Ritorna un arraylist con i dettagli del {@link PuntoPrelievo}.
     *
     * @return ArrayList dei dettagli
     *
     * @throws SQLException eccezione causa da una query SQL
     */
    ArrayList<String> getDettagli() throws SQLException;

    /**
     * Ritorna il Codice Identificativo del {@link PuntoPrelievo}.
     *
     * @return l'ID del Punto di Prelievo
     */
    int getID();

    /**
     * Ritorna l'Indirizzo del {@link PuntoPrelievo}.
     *
     * @return l'indirizzo del Punto di Prelievo
     */
    String getIndirizzo();

    /**
     * Ritorna la lista di tutte le merci appartenenti a tale ordine e presenti nel {@link PuntoPrelievo}.
     *
     * @param IDOrdine ID dell' ordine
     *
     * @return la lista delle Merci
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<MerceOrdine> getMerceMagazzino(int IDOrdine) throws SQLException;

    /**
     * Ritorna il Nome del {@link PuntoPrelievo}.
     *
     * @return in nome del Punto di Prelievo
     */
    String getNome();

    /**
     * Ritorna l' insieme degli ordini effettuati dal cliente e presenti in tale {@link PuntoPrelievo}.
     *
     * @param IDCliente ID del cliente
     *
     * @return l'insieme degli Ordini
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<Ordine> getOrdini(int IDCliente) throws SQLException;

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void update() throws SQLException;
}