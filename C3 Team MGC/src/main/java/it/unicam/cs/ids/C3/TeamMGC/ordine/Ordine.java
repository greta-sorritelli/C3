package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire il comportamento di
 * un singolo Ordine. Permette di accedere e modificare le informazioni associate all' ordine.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public interface Ordine extends Comparable<Ordine> {
    /**
     * Aggiunge la {@link MerceOrdine} all'{@link Ordine}.
     *
     * @param merce Merce da aggiungere
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void addMerce(MerceOrdine merce) throws SQLException;

    /**
     * Aggiunge l'{@code indirizzo} della residenza all'{@link Ordine}.
     *
     * @param indirizzo Indirizzo residenza del cliente
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void addResidenza(String indirizzo) throws SQLException;

    /**
     * Aggiunge la {@link SimpleMerceOrdine} all'{@link Ordine} del cliente.
     *
     * @param merce    Merce da aggiungere
     * @param quantita Quantità della merce da aggiungere
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void aggiungiMerce(MerceOrdine merce, int quantita) throws SQLException;

    /**
     * Ritorna il Cognome del {@link Cliente} collegato all' {@link Ordine}.
     *
     * @return il cognome del Cliente
     */
    String getCognomeCliente();

    /**
     * Ritorna un arraylist con i dettagli dell' {@link Ordine}.
     *
     * @return ArrayList dei dettagli dell' ordine
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<String> getDettagli() throws SQLException;

    /**
     * Ritorna il Codice Identificativo dell' {@link Ordine}.
     *
     * @return l'ID dell' ordine
     */
    int getID();

    /**
     * Ritorna il Codice Identificativo del {@link Cliente} collegato all' {@link Ordine}.
     *
     * @return l'ID del Cliente
     */
    int getIDCliente();

    /**
     * Ritorna il Codice Identificativo del {@link Negozio} collegato all' {@link Ordine}.
     *
     * @return l'ID del Negozio
     */
    int getIDNegozio();

    /**
     * Ritorna la lista delle {@link MerceOrdine Merci} dell' {@link Ordine}.
     *
     * @return la lista delle merci
     */
    ArrayList<MerceOrdine> getMerci();

    /**
     * Ritorna il Nome del {@link Cliente} collegato all' {@link Ordine}.
     *
     * @return il Nome del Cliente
     */
    String getNomeCliente();

    /**
     * Ritorna l'ID del {@link PuntoPrelievo} collegato all' {@link Ordine}.
     *
     * @return l'ID del Punto di Prelievo
     */
    int getPuntoPrelievo();

    /**
     * Imposta un {@link PuntoPrelievo} all' {@link Ordine}.
     *
     * @param IDPuntoPrelievo ID del Punto di Prelievo da impostare
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void setPuntoPrelievo(int IDPuntoPrelievo) throws SQLException;

    /**
     * Ritorna la Residenza collegata all' {@link Ordine}.
     *
     * @return la residenza dell' Ordine
     */
    String getResidenza();

    /**
     * Imposta una Residenza all' {@link Ordine}.
     *
     * @param residenza Residenza da impostare
     */
    void setResidenza(String residenza);

    /**
     * Ritorna lo {@link StatoOrdine Stato} dell' {@link Ordine}.
     *
     * @return lo Stato dell' ordine
     */
    StatoOrdine getStato();

    /**
     * Imposta lo {@link StatoOrdine Stato} all' {@link Ordine}.
     *
     * @param statoOrdine Stato da impostare
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void setStato(StatoOrdine statoOrdine) throws SQLException;

    /**
     * Ritorna il Prezzo Totale dell' {@link Ordine}.
     *
     * @return il prezzo dell' ordine
     */
    double getTotalePrezzo();

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void update() throws SQLException;
}
