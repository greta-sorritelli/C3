package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.manager.Gestore;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Negozio extends Gestore<Merce> {

    void delete() throws SQLException;

    void eliminaPromozione(int IDMerce) throws SQLException;

    CategoriaNegozio getCategoria();

    void setCategoria(CategoriaNegozio categoria) throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    ArrayList<ArrayList<String>> getDettagliPromozioni() throws SQLException;

    int getID();

    String getIndirizzo();

    ArrayList<Merce> getInventario() throws SQLException;

    int getMerceVenduta() throws SQLException;

    String getNome();

    String getOrarioApertura();

    String getOrarioChiusura();

    Double getPrezzoMedio() throws SQLException;

    ArrayList<String> getPromozione(int IDMerce) throws SQLException;

    String getTelefono();

    ArrayList<String> inserisciNuovaMerce(double prezzo, String descrizione, int quantita) throws SQLException;

    void lanciaPromozione(int IDMerce, double nuovoPrezzo, String messaggio) throws SQLException;

    void removeMerce(int IDMerce) throws SQLException;

    void setNuoviDatiPromozione(int IDMerce, double prezzo, String messaggio) throws SQLException;

    void setQuantitaMerce(int IDMerce, int quantita) throws SQLException;

    void update() throws SQLException;
}
