package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class OrdineTest {
    static Cliente cliente;
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
        cliente = new Cliente("Matteo", "Rondini");
        negozio = new Negozio("Negozio di Bici", "Sport", "09:00-16:00", "", "Via dei Test", "12345");
    }

    @Test
    void addResidenza() throws SQLException {
        Ordine ordine = new Ordine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio.getIDNegozio());
        assertEquals("", ordine.getResidenza());
        ordine.addResidenza("Piazza degli Assert");
        assertEquals("Piazza degli Assert", ordine.getResidenza());
    }

    @Test
    void aggiungiMerce() throws SQLException {
        Ordine ordine = new Ordine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio.getIDNegozio());
        MerceOrdine merce = new MerceOrdine(12, "matita", StatoOrdine.IN_DEPOSITO, ordine.getID());
        ordine.aggiungiMerce(merce, 15);
        assertEquals(merce.getQuantita(), 15);
        assertTrue(ordine.getMerci().contains(merce));
        assertEquals(merce.getIDOrdine(), 1);
    }

    @Test
    void creazioneOrdine() throws SQLException {
        Ordine ordine = new Ordine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio.getIDNegozio());
        assertEquals(cliente.getID(), ordine.getIDCliente());
        assertEquals(cliente.getNome(), ordine.getNomeCliente());
        assertEquals(cliente.getCognome(), ordine.getCognomeCliente());
        assertEquals(negozio.getIDNegozio(), ordine.getIDNegozio());
    }

    @Test
    void getDettagli() throws SQLException {
        Ordine ordine = new Ordine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio.getIDNegozio());
        MerceOrdine merce = new MerceOrdine(12, "matita", StatoOrdine.PAGATO, ordine.getID());
        PuntoPrelievo p = new PuntoPrelievo("Via dei Casi d'Uso", "SD 1");
        ordine.aggiungiMerce(merce, 2);
        ordine.setStato(StatoOrdine.PAGATO);
        ordine.setPuntoPrelievo(p.getID());

        ArrayList<String> ordineLista = new ArrayList<>();
        ordineLista.add(String.valueOf(ordine.getID()));
        ordineLista.add(String.valueOf(cliente.getID()));
        ordineLista.add("Matteo");
        ordineLista.add("Rondini");
        ordineLista.add("24.0");
        ordineLista.add(StatoOrdine.PAGATO.toString());
        ordineLista.add(String.valueOf(p.getID()));
        ordineLista.add(ordine.getMerci().toString());
        assertEquals(ordineLista, ordine.getDettagli());
    }

    @Test
    void setPuntoPrelievo() throws SQLException {
        Ordine ordine = new Ordine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio.getIDNegozio());
        PuntoPrelievo p = new PuntoPrelievo("Piazza Roma", "MAG 5");
        assertEquals(-1, ordine.getPuntoPrelievo());
        ordine.setPuntoPrelievo(p.getID());
        assertEquals(p.getID(), ordine.getPuntoPrelievo());
    }

    @Test
    void setStato() throws SQLException {
        Ordine ordine = new Ordine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio.getIDNegozio());
        assertNull(ordine.getStato());
        ordine.setStato(StatoOrdine.RITIRATO);
        assertEquals(ordine.getStato(), StatoOrdine.RITIRATO);
    }

}
