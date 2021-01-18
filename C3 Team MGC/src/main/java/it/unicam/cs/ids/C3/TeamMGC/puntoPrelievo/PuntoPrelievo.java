package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PuntoPrelievo {

    int getID();

    String getIndirizzo();

    String getNome();

    ArrayList<String> getDettagli() throws SQLException;

    ArrayList<MerceOrdine> getMerceMagazzino(int IDOrdine) throws SQLException;

    ArrayList<Ordine> getOrdini(int IDCliente) throws SQLException;

    void update() throws SQLException;

}
