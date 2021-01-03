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
    void creazioneOrdine() {
        cliente1 = new Cliente("Matteo", "Rondini");
        Ordine ordine = new Ordine(1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, -1);
        assertEquals(1, ordine.getID());
    }

    @Test
    void setPuntoPrelievo() {
        cliente1 = new Cliente("Matteo", "Rondini");
        Ordine ordine = new Ordine(1, 1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, -1);
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine1 = new Ordine(1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, -1);
        ordine.setPuntoPrelievo(p.getID());
        PuntoPrelievo p1 = new PuntoPrelievo("via op", "B3");
        ordine1.setPuntoPrelievo(p1.getID());
        assertEquals(p.getID(), ordine.getPuntoPrelievo());
    }

    @Test
    void setStato() {
        cliente1 = new Cliente("Matteo", "Rondini");
        Ordine ordine = new Ordine(1, 1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, -1);
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine1 = new Ordine(1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, p.getID());
        ordine.setStato(StatoOrdine.RITIRATO);
        assertEquals(ordine.getStato(), StatoOrdine.RITIRATO);
        assertNotEquals(ordine.getStato(), StatoOrdine.AFFIDATO_AL_CORRIERE);
        ordine1.setStato(StatoOrdine.AFFIDATO_AL_CORRIERE);
        assertEquals(ordine1.getStato(), StatoOrdine.AFFIDATO_AL_CORRIERE);

    }

    @Test
    void getDettagli() {
        cliente1 = new Cliente("Matteo", "Rondini");
        ordineTest = new Ordine(1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, -1);
        MerceOrdine merce = new MerceOrdine(12, "matita", StatoOrdine.IN_DEPOSITO, 1);
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine = new Ordine(1, 1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, p.getID());
        ordine.aggiungiMerce(merce, 2);
        ArrayList<String> ordine1 = new ArrayList<>();
        ordine1.add(String.valueOf(ordine.getID()));
        ordine1.add(String.valueOf(ordine.getIDCliente()));
        ordine1.add(ordine.getNomeCliente());
        ordine1.add(ordine.getCognomeCliente());
        ordine1.add(String.valueOf(ordine.getTotalePrezzo()));
        ordine1.add(ordine.getStato().toString());
        ordine1.add(String.valueOf(ordine.getPuntoPrelievo()));
        ordine1.add(ordine.getMerci().toString());
        assertEquals(ordine1, ordine.getDettagli());
    }

    @Test
    void addResidenza() {
        cliente1 = new Cliente("Matteo", "Rondini");
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine = new Ordine(1, 1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, p.getID());
        Ordine ordine1 = new Ordine(1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, p.getID());
        ordine.addResidenza("Via Giuseppe Garibaldi");
        ordine1.addResidenza("Via Giuseppe Rossi");
        assertEquals(ordine.getResidenza(), "Via Giuseppe Garibaldi");
        assertEquals(-1, ordine1.getPuntoPrelievo());

    }

    @Test
    void aggiungiMerce() {
        cliente1 = new Cliente("Matteo", "Rondini");
        Ordine ordine = new Ordine(1, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, -1);
        MerceOrdine merce = new MerceOrdine(12, "matita", StatoOrdine.IN_DEPOSITO, 1);
        ordine.aggiungiMerce(merce, 15);
        assertEquals(merce.getQuantita(), 15);
        assertTrue(ordine.getMerci().contains(merce));
        assertEquals(merce.getIDOrdine(), 1);

    }

}
