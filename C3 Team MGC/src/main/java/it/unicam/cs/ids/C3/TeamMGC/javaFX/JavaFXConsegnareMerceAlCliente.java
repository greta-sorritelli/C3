package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXConsegnareMerceAlCliente {

    private final GestoreOrdini gestoreOrdini;

    private final GestoreClienti gestoreClienti;

    public JavaFXConsegnareMerceAlCliente(GestoreOrdini gestoreOrdini, GestoreClienti gestoreClienti) {
        this.gestoreOrdini = gestoreOrdini;
        this.gestoreClienti = gestoreClienti;
    }

    public boolean verificaCodice(int IDCliente, String codiceRitiro) throws SQLException {
        //todo
      return gestoreClienti.verificaCodice(IDCliente,codiceRitiro);
    }

    public ArrayList<String> getDettagliOrdine(int IDCliente) {
        //todo
        return null;
    }

    public ArrayList<String> getDettagliMerceMagazzino(int IDOrdine) {
        //todo
        return null;
    }

    public void setStatoMerce(int IDMerce, StatoOrdine statoOrdine){
        //todo stato ritirato
    }

    public ArrayList<String> getDettagliMerceTotale(int IDOrdine) {
        //todo
        return null;
    }


}
