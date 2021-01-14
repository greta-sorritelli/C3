package it.unicam.cs.ids.C3.TeamMGC.cliente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Cliente {

     ArrayList<String> getDettagli() throws SQLException;

     void update() throws SQLException;



}
