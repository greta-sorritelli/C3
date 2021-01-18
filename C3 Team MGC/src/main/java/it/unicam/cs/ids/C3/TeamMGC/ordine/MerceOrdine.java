package it.unicam.cs.ids.C3.TeamMGC.ordine;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MerceOrdine {

    String getDescrizione();

    void setDescrizione(String descrizione) throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    int getID();

    int getIDCorriere();

    void setIDCorriere(int IDCorriere) throws SQLException;

    int getIDOrdine();

    double getPrezzo();

    void setPrezzo(double prezzo) throws SQLException;

    int getQuantita();

    void setQuantita(int quantita) throws SQLException;

    StatoOrdine getStato();

    void setStato(StatoOrdine stato) throws SQLException;

    void update() throws SQLException;
}
