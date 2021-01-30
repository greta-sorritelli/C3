package it.unicam.cs.ids.C3.TeamMGC.ordine;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire il comportamento di
 * una singola Merce di un {@link Ordine}. Permette di accedere e modificare le informazioni
 * associate alla merce.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public interface MerceOrdine {

    String getDescrizione();

    void setDescrizione(String descrizione) throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    int getID();

    int getIDCorriere();

    void setIDCorriere(int IDCorriere) throws SQLException;

    int getIDOrdine();

    void setIDOrdine(int IDOrdine) throws SQLException;

    double getPrezzo();

    void setPrezzo(double prezzo) throws SQLException;

    int getQuantita();

    void setQuantita(int quantita) throws SQLException;

    StatoOrdine getStato();

    void setStato(StatoOrdine stato) throws SQLException;

    void update() throws SQLException;
}
