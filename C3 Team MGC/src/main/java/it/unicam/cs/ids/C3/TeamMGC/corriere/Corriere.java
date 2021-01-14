package it.unicam.cs.ids.C3.TeamMGC.corriere;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Corriere {

    ArrayList<String> getDettagli() throws SQLException;

    void update() throws SQLException;
}
