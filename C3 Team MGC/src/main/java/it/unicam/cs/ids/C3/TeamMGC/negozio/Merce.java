package it.unicam.cs.ids.C3.TeamMGC.negozio;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Merce {

    void delete() throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    void update() throws SQLException;
}
