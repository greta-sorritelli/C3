package it.unicam.cs.ids.C3.TeamMGC.cliente;

import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GestoreClientiTest {

    @Test
    void verificaEsistenzaCodiceTest() throws SQLException {
        GestoreClienti gestoreClienti = new GestoreClienti();
        ArrayList<String> dettagli = gestoreClienti.inserisciDati("Mario","Rossi");
        Ordine ordine = new Ordine(Integer.parseInt(dettagli.get(0)), "Mario", "Rossi");
        String codice = gestoreClienti.verificaEsistenzaCodice(Integer.parseInt(dettagli.get(0)), ordine.getID());
        Cliente cliente = gestoreClienti.getItem(Integer.parseInt(dettagli.get(0)));
        assertNotNull(cliente.getCodiceRitiro());
        assertEquals(codice, cliente.getCodiceRitiro());
        assertTrue(gestoreClienti.verificaCodice(cliente.getID(), codice));
    }
}