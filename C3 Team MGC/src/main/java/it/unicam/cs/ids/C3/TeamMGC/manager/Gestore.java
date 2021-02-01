package it.unicam.cs.ids.C3.TeamMGC.manager;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Gestore<T> {

    ArrayList<ArrayList<String>> getDettagliItems() throws SQLException;

    T getItem(int ID) throws SQLException;

    ArrayList<T> getItems() throws SQLException;

    void reset();
}
