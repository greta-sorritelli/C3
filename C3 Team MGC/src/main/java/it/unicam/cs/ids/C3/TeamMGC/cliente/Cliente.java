package it.unicam.cs.ids.C3.TeamMGC.cliente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Cliente {

     String getCodiceRitiro();

     String getCognome();

     String getDataCreazioneCodice();

     int getID();

     String getNome();

     String setCodiceRitiro(String codiceRitiro) throws SQLException;

     ArrayList<String> getDettagli() throws SQLException;

     void update() throws SQLException;



}
