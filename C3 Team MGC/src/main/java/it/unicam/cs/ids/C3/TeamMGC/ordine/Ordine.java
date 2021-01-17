package it.unicam.cs.ids.C3.TeamMGC.ordine;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Ordine {

    void addMerce(SimpleMerceOrdine merce) throws SQLException;

    void addResidenza(String indirizzo) throws SQLException;

    void aggiungiMerce(SimpleMerceOrdine merce, int quantita) throws SQLException;

    String getCognomeCliente();

    ArrayList<String> getDettagli() throws SQLException;

    int getID();

    int getIDCliente();

    int getIDNegozio();

    ArrayList<SimpleMerceOrdine> getMerci() throws SQLException;

    String getNomeCliente();

    int getPuntoPrelievo();

    void setPuntoPrelievo(int IDPuntoPrelievo) throws SQLException;

    String getResidenza();

    void setResidenza(String residenza);

    StatoOrdine getStato();

    void setStato(StatoOrdine statoOrdine) throws SQLException;

    double getTotalePrezzo();

    void update() throws SQLException;
}
