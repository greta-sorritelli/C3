package it.unicam.cs.ids.C3.TeamMGC.ordine;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MerceOrdine {

    ArrayList<String> getDettagli() throws SQLException;

    void update() throws SQLException;
}
