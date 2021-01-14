package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class SimpleOrdineTest {
    static SimpleCliente simpleCliente;
    static Negozio negozio;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.ordini;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        updateData("delete from sys.merci;");
        updateData("alter table merci AUTO_INCREMENT = 1;");
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
        updateData("delete from sys.negozi;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        simpleCliente = new SimpleCliente("Matteo", "Rondini");
        negozio = new Negozio("Negozio di Bici", "Sport", "09:00-16:00", "", "Via dei Test", "12345");
    }

    @Test
    void addResidenza() throws SQLException {
        SimpleOrdine simpleOrdine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        assertEquals("", simpleOrdine.getResidenza());
        simpleOrdine.addResidenza("Piazza degli Assert");
        assertEquals("Piazza degli Assert", simpleOrdine.getResidenza());
    }

    @Test
    void aggiungiMerce() throws SQLException {
        SimpleOrdine simpleOrdine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        SimpleMerceOrdine merce = new SimpleMerceOrdine(12, "matita", StatoOrdine.IN_DEPOSITO, simpleOrdine.getID());
        simpleOrdine.aggiungiMerce(merce, 15);
        assertEquals(15, merce.getQuantita());
        assertTrue(simpleOrdine.getMerci().contains(merce));
    }

    @Test
    void creazioneOrdine() throws SQLException {
        SimpleOrdine simpleOrdine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        assertEquals(simpleCliente.getID(), simpleOrdine.getIDCliente());
        assertEquals(simpleCliente.getNome(), simpleOrdine.getNomeCliente());
        assertEquals(simpleCliente.getCognome(), simpleOrdine.getCognomeCliente());
        assertEquals(negozio.getID(), simpleOrdine.getIDNegozio());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new SimpleOrdine(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getDettagli() throws SQLException {
        SimpleOrdine simpleOrdine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        SimpleMerceOrdine merce = new SimpleMerceOrdine(12, "matita", StatoOrdine.PAGATO, simpleOrdine.getID());
        SimplePuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 1");
        simpleOrdine.aggiungiMerce(merce, 2);
        simpleOrdine.setStato(StatoOrdine.PAGATO);
        simpleOrdine.setPuntoPrelievo(p.getID());

        ArrayList<String> ordineLista = new ArrayList<>();
        ordineLista.add(String.valueOf(simpleOrdine.getID()));
        ordineLista.add(String.valueOf(simpleCliente.getID()));
        ordineLista.add("Matteo");
        ordineLista.add("Rondini");
        ordineLista.add("24.0");
        ordineLista.add(StatoOrdine.PAGATO.toString());
        ordineLista.add(String.valueOf(p.getID()));
        ordineLista.add(simpleOrdine.getMerci().toString());
        assertEquals(ordineLista, simpleOrdine.getDettagli());
    }

    @Test
    void setPuntoPrelievo() throws SQLException {
        SimpleOrdine simpleOrdine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        SimplePuntoPrelievo p = new SimplePuntoPrelievo("Piazza Roma", "MAG 5");
        assertEquals(-1, simpleOrdine.getPuntoPrelievo());
        simpleOrdine.setPuntoPrelievo(p.getID());
        assertEquals(p.getID(), simpleOrdine.getPuntoPrelievo());
    }

    @Test
    void setStato() throws SQLException {
        SimpleOrdine simpleOrdine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        assertEquals(StatoOrdine.DA_PAGARE, simpleOrdine.getStato());
        simpleOrdine.setStato(StatoOrdine.RITIRATO);
        assertEquals(simpleOrdine.getStato(), StatoOrdine.RITIRATO);
    }

    @Test
    void testEquals() throws SQLException {
        SimpleOrdine simpleOrdine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        simpleOrdine.setStato(StatoOrdine.PAGATO);
        SimpleOrdine simpleOrdineCopia = new SimpleOrdine(simpleOrdine.getID());
        SimpleOrdine simpleOrdine2 = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        assertEquals(simpleOrdine, simpleOrdineCopia);
        assertNotEquals(simpleOrdine, simpleOrdine2);
    }

    @Test
    void update() throws SQLException {
        SimpleOrdine simpleOrdine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        SimpleMerceOrdine merce = new SimpleMerceOrdine(12, "matita", StatoOrdine.PAGATO, simpleOrdine.getID());
        simpleOrdine.aggiungiMerce(merce, 15);
        SimplePuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 1");
        simpleOrdine.setPuntoPrelievo(p.getID());

        assertEquals("Matteo", simpleOrdine.getNomeCliente());
        assertEquals("Rondini", simpleOrdine.getCognomeCliente());
        assertEquals(180, simpleOrdine.getTotalePrezzo());
        assertEquals(StatoOrdine.DA_PAGARE, simpleOrdine.getStato());
        assertEquals(p.getID(), simpleOrdine.getPuntoPrelievo());

        simpleOrdine.aggiungiMerce(merce,10);
        simpleOrdine.setStato(StatoOrdine.PAGATO);
        SimplePuntoPrelievo p1 = new SimplePuntoPrelievo("Via degli Activity Diagram", "SD 2");

        updateData("UPDATE sys.ordini SET nomeCliente = 'Clarissa' where ID = '" + simpleOrdine.getID() + "';");
        updateData("UPDATE sys.ordini SET cognomeCliente = 'Albanese' where ID = '" + simpleOrdine.getID() + "';");
        updateData("UPDATE sys.ordini SET totalePrezzo = '300' where ID = '" + simpleOrdine.getID() + "';");
        updateData("UPDATE sys.ordini SET stato = 'PAGATO' where ID = '" + simpleOrdine.getID() + "';");
        updateData("UPDATE sys.ordini SET IDPuntoPrelievo = " + p1.getID() + " where ID = '" + simpleOrdine.getID() + "';");

        simpleOrdine.update();
        assertEquals("Clarissa", simpleOrdine.getNomeCliente());
        assertEquals("Albanese", simpleOrdine.getCognomeCliente());
        assertEquals(300, simpleOrdine.getTotalePrezzo());
        assertEquals(StatoOrdine.PAGATO, simpleOrdine.getStato());
        assertEquals(p1.getID(), simpleOrdine.getPuntoPrelievo());

    }

}
