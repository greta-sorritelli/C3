package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;

import java.sql.SQLException;

public class JavaFXRicezionePagamento {

    private final GestoreOrdini gestoreOrdini;

    public JavaFXRicezionePagamento(GestoreOrdini gestoreOrdini) {
        this.gestoreOrdini = gestoreOrdini;
    }

    //todo
    public void registraOrdine(int IDCliente, String Nome, String Cognome) throws SQLException {
        gestoreOrdini.registraOrdine(IDCliente, Nome, Cognome);
    }

    //todo
    public void registraMerce(int IDMerce, int quantita, int IDOrdine) throws SQLException {
        gestoreOrdini.registraMerce(IDMerce, quantita, IDOrdine);
    }

    //todo
    public void terminaOrdine(int IDOrdine) throws SQLException {
        gestoreOrdini.terminaOrdine(IDOrdine);
    }
}
