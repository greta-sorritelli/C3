package it.unicam.cs.ids.C3.TeamMGC.corriere;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Corriere {

    String getCognome();

    boolean getDisponibilita();

    int getID();

    String getNome();

    void setDisponibilita(boolean disponibilita) throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    void update() throws SQLException;
}
