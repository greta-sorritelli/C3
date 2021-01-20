package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Negozio extends Gestore<Merce> {

    String getCategoria();

    int getID();

    String getIndirizzo();

    ArrayList<Merce> getInventario();

    String getNome();

    String getOrarioApertura();

    String getOrarioChiusura();

    String getTelefono();

    void setQuantitaMerce(int IDMerce, int quantita) throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    ArrayList<String> inserisciNuovaMerce(double prezzo, String descrizione, int quantita) throws SQLException;

    void removeMerce(int IDMerce) throws SQLException;

    void update() throws SQLException;
}
