package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.CategoriaNegozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleNegozio;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class SimpleOrdineTest {
    static Cliente simpleCliente;
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
        negozio = new SimpleNegozio("Negozio di Bici", CategoriaNegozio.SPORT, "09:00-16:00", "", "Via dei Test", "12345");
    }

    @Test
    void addResidenza() throws SQLException {
        Ordine ordine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        assertEquals("", ordine.getResidenza());
        ordine.addResidenza("Piazza degli Assert");
        assertEquals("Piazza degli Assert", ordine.getResidenza());
    }

    @Test
    void aggiungiMerce() throws SQLException {
        Ordine ordine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        MerceOrdine merce = new SimpleMerceOrdine(12, "matita", StatoOrdine.IN_DEPOSITO, ordine.getID());
        ordine.aggiungiMerce(merce, 15);
        assertEquals(15, merce.getQuantita());
        assertTrue(ordine.getMerci().contains(merce));
    }

    @Test
    void creazioneOrdine() throws SQLException {
        Ordine ordine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        assertEquals(simpleCliente.getID(), ordine.getIDCliente());
        assertEquals(simpleCliente.getNome(), ordine.getNomeCliente());
        assertEquals(simpleCliente.getCognome(), ordine.getCognomeCliente());
        assertEquals(negozio.getID(), ordine.getIDNegozio());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new SimpleOrdine(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void compareTo() throws SQLException {
        Ordine ordine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        Ordine ordineCopia = new SimpleOrdine(ordine.getID());
        Ordine ordine2 = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());

        assertEquals(0, ordine.compareTo(ordineCopia));
        assertEquals(-1, ordine.compareTo(ordine2));
        assertEquals(1, ordine2.compareTo(ordine));

        assertThrows(NullPointerException.class, () -> ordine.compareTo(null));
    }

    @Test
    void getDettagli() throws SQLException {
        Ordine ordine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        MerceOrdine merce = new SimpleMerceOrdine(12, "matita", StatoOrdine.PAGATO, ordine.getID());
        PuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 1");
        ordine.aggiungiMerce(merce, 2);
        ordine.setStato(StatoOrdine.PAGATO);
        ordine.setPuntoPrelievo(p.getID());

        ArrayList<String> ordineLista = new ArrayList<>();
        ordineLista.add(String.valueOf(ordine.getID()));
        ordineLista.add(String.valueOf(simpleCliente.getID()));
        ordineLista.add("Matteo");
        ordineLista.add("Rondini");
        ordineLista.add("24.0");
        ordineLista.add(StatoOrdine.PAGATO.toString());
        ordineLista.add(String.valueOf(p.getID()));
        ordineLista.add(String.valueOf(negozio.getID()));
        ordineLista.add(ordine.getMerci().toString());
        assertEquals(ordineLista, ordine.getDettagli());
    }

    @Test
    void setPuntoPrelievo() throws SQLException {
        Ordine ordine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        PuntoPrelievo p = new SimplePuntoPrelievo("Piazza Roma", "MAG 5");
        assertEquals(-1, ordine.getPuntoPrelievo());
        ordine.setPuntoPrelievo(p.getID());
        assertEquals(p.getID(), ordine.getPuntoPrelievo());

        ResultSet rs = executeQuery("SELECT IDPuntoPrelievo FROM sys.ordini where ID = " + ordine.getID() + ";");
        if (rs.next())
            assertEquals(p.getID(), rs.getInt("IDPuntoPrelievo"));
    }

    @Test
    void setStato() throws SQLException {
        Ordine ordine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        assertEquals(StatoOrdine.DA_PAGARE, ordine.getStato());
        ordine.setStato(StatoOrdine.RITIRATO);
        assertEquals(ordine.getStato(), StatoOrdine.RITIRATO);

        ResultSet rs = executeQuery("SELECT stato FROM sys.ordini where ID = " + ordine.getID() + ";");
        if (rs.next())
            assertEquals(StatoOrdine.RITIRATO, StatoOrdine.valueOf(rs.getString("stato")));
    }

    @Test
    void testEquals_toString_hashCode() throws SQLException {
        Ordine ordine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        ordine.setStato(StatoOrdine.PAGATO);
        Ordine ordineCopia = new SimpleOrdine(ordine.getID());
        Ordine ordine2 = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        assertEquals(ordine, ordineCopia);
        assertNotEquals(ordine, ordine2);
        assertEquals(ordine.hashCode(),ordineCopia.hashCode());
        assertEquals("ID=" + ordine.getID() + ", IDCliente=1, nomeCliente='Matteo', cognomeCliente='Rondini', totalePrezzo=0.0, stato=PAGATO, IDPuntoPrelievo=-1, residenza='', IDNegozio=1, merci=[]",ordine.toString());
        assertEquals("ID=" + ordine2.getID() + ", IDCliente=1, nomeCliente='Matteo', cognomeCliente='Rondini', totalePrezzo=0.0, stato=DA_PAGARE, IDPuntoPrelievo=-1, residenza='', IDNegozio=1, merci=[]",ordine2.toString());
    }

    @Test
    void update() throws SQLException {
        Ordine ordine = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio.getID());
        MerceOrdine merce = new SimpleMerceOrdine(12, "matita", StatoOrdine.PAGATO, ordine.getID());
        ordine.aggiungiMerce(merce, 15);
        PuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 1");
        ordine.setPuntoPrelievo(p.getID());

        assertEquals("Matteo", ordine.getNomeCliente());
        assertEquals("Rondini", ordine.getCognomeCliente());
        assertEquals(180, ordine.getTotalePrezzo());
        assertEquals(StatoOrdine.DA_PAGARE, ordine.getStato());
        assertEquals(p.getID(), ordine.getPuntoPrelievo());

        ordine.aggiungiMerce(merce, 10);
        ordine.setStato(StatoOrdine.PAGATO);
        PuntoPrelievo p1 = new SimplePuntoPrelievo("Via degli Activity Diagram", "SD 2");

        updateData("UPDATE sys.ordini SET nomeCliente = 'Clarissa' where ID = '" + ordine.getID() + "';");
        updateData("UPDATE sys.ordini SET cognomeCliente = 'Albanese' where ID = '" + ordine.getID() + "';");
        updateData("UPDATE sys.ordini SET totalePrezzo = '300' where ID = '" + ordine.getID() + "';");
        updateData("UPDATE sys.ordini SET stato = 'PAGATO' where ID = '" + ordine.getID() + "';");
        updateData("UPDATE sys.ordini SET IDPuntoPrelievo = " + p1.getID() + " where ID = '" + ordine.getID() + "';");

        ordine.update();
        assertEquals("Clarissa", ordine.getNomeCliente());
        assertEquals("Albanese", ordine.getCognomeCliente());
        assertEquals(300, ordine.getTotalePrezzo());
        assertEquals(StatoOrdine.PAGATO, ordine.getStato());
        assertEquals(p1.getID(), ordine.getPuntoPrelievo());
    }

}
