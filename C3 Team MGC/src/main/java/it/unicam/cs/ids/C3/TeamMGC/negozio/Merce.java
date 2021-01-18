package it.unicam.cs.ids.C3.TeamMGC.negozio;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Merce {

    String getDescrizione();

    void setDescrizione(String descrizione) throws SQLException;

    int getID();

    int getIDNegozio();

    double getPrezzo();

    void setPrezzo(double prezzo) throws SQLException;

    int getQuantita();

    void setQuantita(int quantita) throws SQLException;


    abstract void delete() throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    void update() throws SQLException;
}
