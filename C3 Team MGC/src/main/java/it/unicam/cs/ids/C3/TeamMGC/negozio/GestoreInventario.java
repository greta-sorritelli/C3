package it.unicam.cs.ids.C3.TeamMGC.negozio;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GestoreInventario {

    ArrayList<String> getDettagli() throws SQLException;

    SimpleMerce getMerce(int ID) throws SQLException;

    ArrayList<ArrayList<String>> getDettagliMerce() throws SQLException;

    ArrayList<SimpleMerce> getMerceDisponibile() throws SQLException;

    ArrayList<String> inserisciNuovaMerce(double prezzo, String descrizione, int quantita) throws SQLException;

    void removeMerce(int IDMerce) throws SQLException;

    ArrayList<String> selezionaMerce(int IDMerce) throws SQLException;

    void update() throws SQLException;
}
