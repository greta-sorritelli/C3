package it.unicam.cs.ids.C3.TeamMGC.ordine;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Ordine {

    void addMerce(MerceOrdine merce) throws SQLException;

    void addResidenza(String indirizzo) throws SQLException;

    void aggiungiMerce(MerceOrdine merce, int quantita) throws SQLException;

    String getCognomeCliente();

    ArrayList<String> getDettagli() throws SQLException;

    int getID();

    int getIDCliente();

    int getIDNegozio();

    ArrayList<MerceOrdine> getMerci() throws SQLException;

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
