package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.manager.Gestore;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Negozio extends Gestore<Merce> {

    CategoriaNegozio getCategoria();

    void setCategoria(CategoriaNegozio categoria) throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    int getID();

    String getIndirizzo();

    ArrayList<Merce> getInventario() throws SQLException;

    int getMerceVenduta() throws SQLException;

    String getNome();

    String getOrarioApertura();

    String getOrarioChiusura();

    Double getPrezzoMedio() throws SQLException;

    String getTelefono();

    ArrayList<String> inserisciNuovaMerce(double prezzo, String descrizione, int quantita) throws SQLException;

    void removeMerce(int IDMerce) throws SQLException;

    void setQuantitaMerce(int IDMerce, int quantita) throws SQLException;

    void update() throws SQLException;
}
