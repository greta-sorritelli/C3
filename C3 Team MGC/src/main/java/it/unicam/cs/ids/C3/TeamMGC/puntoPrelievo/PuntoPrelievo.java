package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PuntoPrelievo {

    void delete() throws SQLException;

    ArrayList<String> getDettagli() throws SQLException;

    int getID();

    String getIndirizzo();

    ArrayList<MerceOrdine> getMerceMagazzino(int IDOrdine) throws SQLException;

    String getNome();

    ArrayList<Ordine> getOrdini(int IDCliente) throws SQLException;

    void update() throws SQLException;

}
