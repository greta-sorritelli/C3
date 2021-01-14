package it.unicam.cs.ids.C3.TeamMGC.ordine;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Ordine {

    void addMerce(SimpleMerceOrdine merce) throws SQLException;

    void addResidenza(String indirizzo) throws SQLException;

    void aggiungiMerce(SimpleMerceOrdine merce, int quantita) throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    void update() throws SQLException;
}
