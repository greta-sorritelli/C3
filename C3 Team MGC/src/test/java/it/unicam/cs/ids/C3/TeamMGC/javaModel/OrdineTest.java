package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class OrdineTest {

    @BeforeEach
    void clearDB() throws SQLException {
        updateData("TRUNCATE `sys`.`ordini`;");
        updateData("TRUNCATE `sys`.`merci`;");

    }

    @Test
    void creazioneOrdine() {
        Ordine ordine = new Ordine(2, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, null);
        assertEquals(1, ordine.getID());
    }

    @Test
    void setPuntoPrelievo() {
        Ordine ordine = new Ordine(1, 2, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, null);
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine1 = new Ordine(2, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, null);
        ordine.setPuntoPrelievo(p);
        PuntoPrelievo p1 = new PuntoPrelievo("via op", "B3");
        ordine1.setPuntoPrelievo(p1);
        assertEquals(p, ordine.getPuntoPrelievo());
    }

    @Test
    void setStato() {
        Ordine ordine = new Ordine(1, 2, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, null);
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine1 = new Ordine(2, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, p);
        ordine.setStato(StatoOrdine.RITIRATO);
        assertEquals(ordine.getStato(), StatoOrdine.RITIRATO);
        assertNotEquals(ordine.getStato(), StatoOrdine.AFFIDATO_AL_CORRIERE);
        ordine1.setStato(StatoOrdine.AFFIDATO_AL_CORRIERE);
        assertEquals(ordine1.getStato(), StatoOrdine.AFFIDATO_AL_CORRIERE);

    }

    @Test
    void getDettagli() {
        MerceOrdine merce = new MerceOrdine(12, "matita", StatoOrdine.IN_DEPOSITO);
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine = new Ordine(1, 2, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, p);
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
        PuntoPrelievo p = new PuntoPrelievo("via ciao", "B2");
        Ordine ordine = new Ordine(1, 2, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, p);
        Ordine ordine1 = new Ordine(2, "Matteo", "Rondini", 52, StatoOrdine.RITIRATO, p);
        ordine.addResidenza("Via Giuseppe Garibaldi");
        ordine1.addResidenza("Via Giuseppe Rossi");
        assertEquals(ordine.getResidenza(), "Via Giuseppe Garibaldi");
        assertNull(ordine1.getPuntoPrelievo());

    }
}