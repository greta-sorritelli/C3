package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.manager.Gestore;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire il comportamento di
 * un singolo Negozio. Permette di accedere e modificare le informazioni associate al negozio, gestire l' inventario e
 * gestire le promozioni collegate alle {@link Merce Merci}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public interface Negozio extends Gestore<Merce> {
    /**
     * Elimina il {@link Negozio} dal DB e aggiorna i dati dell' oggetto.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void delete() throws SQLException;

    /**
     * Elimina la Promozione collegata alla {@link Merce}.
     *
     * @param IDMerce Codice Identificativo della Merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void eliminaPromozione(int IDMerce) throws SQLException;

    /**
     * Ritorna la Categoria del {@link Negozio}.
     *
     * @return la categoria del Negozio
     */
    CategoriaNegozio getCategoria();

    void setCategoria(CategoriaNegozio categoria) throws SQLException;

    /**
     * Ritorna la lista dei dettagli di tutta la {@link Merce} all' interno del {@link Negozio}.
     *
     * @return ArrayList di ArrayList dei dettagli della merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<String> getDettagli() throws SQLException;

    /**
     * Ritorna la lista dei dettagli di tutte le {@code Promozione} collegate alla {@link Merce} del {@link Negozio}.
     *
     * @return ArrayList di ArrayList dei dettagli delle Promozioni
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<ArrayList<String>> getDettagliPromozioni() throws SQLException;

    /**
     * Ritorna il Codice Identificativo del {@link Negozio}.
     *
     * @return il Codice Identificativo del Negozio
     */
    int getID();

    /**
     * Ritorna l' Indirizzo del {@link Negozio}.
     *
     * @return l' Indirizzo del Negozio
     */
    String getIndirizzo();

    /**
     * Ritorna la lista della {@link Merce} presente nell' inventario del {@link Negozio}.
     *
     * @return la lista della Merce nell' inventario
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<Merce> getInventario() throws SQLException;

    /**
     * Calcola la percentuale della merce venduta rispetto all' inventario del {@link Negozio}.
     *
     * @return La percentuale della merce venduta.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    int getMerceVenduta() throws SQLException;

    /**
     * Ritorna il Nome del {@link Negozio}.
     *
     * @return il Nome del Negozio
     */
    String getNome();

    /**
     * Ritorna l'Orario d'Apertura del {@link Negozio}.
     *
     * @return l'Orario d'Apertura del Negozio
     */
    String getOrarioApertura();

    /**
     * Ritorna l'Orario di Chiusura del {@link Negozio}.
     *
     * @return l'Orario di Chiusura del Negozio
     */
    String getOrarioChiusura();

    /**
     * Calcola il prezzo medio della merce del {@link Negozio}.
     *
     * @return Prezzo medio.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    Double getPrezzoMedio() throws SQLException;

    /**
     * Ritorna la Promozione  collegata alla {@link Merce}.
     *
     * @param IDMerce Codice Identificativo della Merce
     *
     * @return la Promozione collegata alla Merce
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<String> getPromozione(int IDMerce) throws SQLException;

    /**
     * Ritorna il telefono del {@link Negozio}.
     *
     * @return il telefono del Negozio
     */
    String getTelefono();

    /**
     * Crea e inserisce una nuova {@link Merce} all' interno dell' inventario.
     *
     * @param prezzo      Prezzo della merce da inserire
     * @param descrizione Descrizione della merce da inserire
     * @param quantita    Quantita della merce da inserire
     *
     * @return ArrayList dei dettagli della merce creata
     *
     * @throws SQLException Errore causato da una query SQL
     */
    ArrayList<String> inserisciNuovaMerce(double prezzo, String descrizione, int quantita) throws SQLException;

    /**
     * Crea una nuova {@code Promozione} e la collega alla {@link Merce}.
     *
     * @param IDMerce     Codice Identificativo della Merce
     * @param nuovoPrezzo Nuovo Prezzo della Merce
     * @param messaggio   Messaggio della Promozione
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void lanciaPromozione(int IDMerce, double nuovoPrezzo, String messaggio) throws SQLException;

    /**
     * Rimuove la {@link Merce} dall' inventario.
     *
     * @param IDMerce ID della Merce da rimuovere.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void removeMerce(int IDMerce) throws SQLException;

    /**
     * Imposta i nuovi dati della {@code Promozione}.
     *
     * @param IDMerce   Codice Identificato della Merce con la Promozione da modificare
     * @param prezzo    Prezzo da impostare
     * @param messaggio Messaggio da impostare
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void setNuoviDatiPromozione(int IDMerce, double prezzo, String messaggio) throws SQLException;

    /**
     * Imposta la nuova Quantità della {@link Merce}.
     *
     * @param IDMerce  Codice Identificativo della Merce
     * @param quantita Nuova Quantità da impostare
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void setQuantitaMerce(int IDMerce, int quantita) throws SQLException;

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    void update() throws SQLException;
}
