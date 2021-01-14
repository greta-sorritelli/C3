package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleMerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleOrdine;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PuntoPrelievo {

    ArrayList<String> getDettagli() throws SQLException;

    ArrayList<SimpleMerceOrdine> getMerceMagazzino(int IDOrdine) throws SQLException;

    ArrayList<SimpleOrdine> getOrdini(int IDCliente) throws SQLException;

    void update() throws SQLException;

}
