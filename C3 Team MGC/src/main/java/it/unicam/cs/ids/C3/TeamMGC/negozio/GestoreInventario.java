package it.unicam.cs.ids.C3.TeamMGC.negozio;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GestoreInventario {

    String getCategoria();

    int getID();

    String getIndirizzo();

    ArrayList<Merce> getInventario();

    String getNome();

    String getOrarioApertura();

    String getOrarioChiusura();

    String getTelefono();

    void setQuantita(int IDMerce, int quantita) throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    Merce getMerce(int ID) throws SQLException;

    ArrayList<ArrayList<String>> getDettagliMerce() throws SQLException;

    ArrayList<Merce> getMerceDisponibile() throws SQLException;

    ArrayList<String> inserisciNuovaMerce(double prezzo, String descrizione, int quantita) throws SQLException;

    void removeMerce(int IDMerce) throws SQLException;

    ArrayList<String> selezionaMerce(int IDMerce) throws SQLException;

    void update() throws SQLException;
}
