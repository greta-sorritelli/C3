package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class OrdineTest {

    static Ordine ordineTest;
    static Cliente cliente1;

    @Test
    void addResidenza() throws SQLException {
        cliente1 = new Cliente("Matteo", "Rondini");
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine = new Ordine(1, 1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, p.getID());
        Ordine ordine1 = new Ordine(1, "Matteo", "Rondini");
        ordine.addResidenza("Via Giuseppe Garibaldi");
        ordine1.addResidenza("Via Giuseppe Rossi");
        assertEquals(ordine.getResidenza(), "Via Giuseppe Garibaldi");
        assertEquals(-1, ordine1.getPuntoPrelievo());

    }

    @Test
    void aggiungiMerce() throws SQLException {
        cliente1 = new Cliente("Matteo", "Rondini");
        Ordine ordine = new Ordine(1, "Matteo", "Rondini");
        MerceOrdine merce = new MerceOrdine(12, "matita", StatoOrdine.IN_DEPOSITO, 1);
        ordine.aggiungiMerce(merce, 15);
        assertEquals(merce.getQuantita(), 15);
        assertTrue(ordine.getMerci().contains(merce));
        assertEquals(merce.getIDOrdine(), 1);

    }

    @BeforeEach
    void clearDB() throws SQLException {
        updateData("delete from sys.ordini;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        updateData("delete from sys.merci;");
        updateData("alter table merci AUTO_INCREMENT = 1;");
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
    }

    @Test
    void creazioneOrdine() throws SQLException {
        cliente1 = new Cliente("Matteo", "Rondini");
        Ordine ordine = new Ordine(1, "Matteo", "Rondini");
        assertEquals(1, ordine.getID());
    }

    @Test
    void getDettagli() throws SQLException {
        cliente1 = new Cliente("Matteo", "Rondini");
        ordineTest = new Ordine(1, "Matteo", "Rondini");
        MerceOrdine merce = new MerceOrdine(12, "matita", StatoOrdine.PAGATO, 1);
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine = new Ordine(cliente1.getID(), "Matteo", "Rondini");
        ordine.aggiungiMerce(merce, 2);
        ordine.setStato(StatoOrdine.PAGATO);
        ordine.setPuntoPrelievo(p.getID());

        ArrayList<String> ordine1 = new ArrayList<>();
        ordine1.add(String.valueOf(ordine.getID()));
        ordine1.add(String.valueOf(ordine.getIDCliente()));
        ordine1.add("Matteo");
        ordine1.add("Rondini");
        ordine1.add("24.0");
        ordine1.add(StatoOrdine.PAGATO.toString());
        ordine1.add(String.valueOf(p.getID()));
        ordine1.add(ordine.getMerci().toString());
        assertEquals(ordine1, ordine.getDettagli());
    }

    @Test
    void setPuntoPrelievo() throws SQLException {
        cliente1 = new Cliente("Matteo", "Rondini");
        Ordine ordine = new Ordine(1, 1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, -1);
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine1 = new Ordine(1, "Matteo", "Rondini");
        ordine.setPuntoPrelievo(p.getID());
        PuntoPrelievo p1 = new PuntoPrelievo("via op", "B3");
        ordine1.setPuntoPrelievo(p1.getID());
        assertEquals(p.getID(), ordine.getPuntoPrelievo());
    }

    @Test
    void setStato() throws SQLException {
        cliente1 = new Cliente("Matteo", "Rondini");
        Ordine ordine = new Ordine(1, 1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, -1);
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine1 = new Ordine(1, "Matteo", "Rondini");
        ordine.setStato(StatoOrdine.RITIRATO);
        assertEquals(ordine.getStato(), StatoOrdine.RITIRATO);
        assertNotEquals(ordine.getStato(), StatoOrdine.AFFIDATO_AL_CORRIERE);
        ordine1.setStato(StatoOrdine.AFFIDATO_AL_CORRIERE);
        assertEquals(ordine1.getStato(), StatoOrdine.AFFIDATO_AL_CORRIERE);

    }

}
