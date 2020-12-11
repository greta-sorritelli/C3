package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class OrdineTest {

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("TRUNCATE `sys`.`ordini`;");
    }

    @Test
    void creazioneOrdine() {
        Ordine ordine = new Ordine(2,"Matteo","Rondini",52,StatoOrdine.RITIRATO,null);
        assertEquals(1, ordine.getID());
    }

    @Test
    void setPuntoPrelievo() {
        Ordine ordine = new Ordine(1,2,"Matteo","Rondini",52,StatoOrdine.RITIRATO,null);
        PuntoPrelievo p = new PuntoPrelievo("via ciao","B2");
        Ordine ordine1 = new Ordine(2,"Matteo","Rondini",52,StatoOrdine.RITIRATO,null);
        ordine.setPuntoPrelievo(p);
        PuntoPrelievo p1 = new PuntoPrelievo("via op","B3");
        ordine1.setPuntoPrelievo(p1);
        assertEquals(p,ordine.getPuntoPrelievo());
    }

    @Test
    void setStato() {
        Ordine ordine = new Ordine(1,2,"Matteo","Rondini",52,StatoOrdine.RITIRATO,null);
        PuntoPrelievo p = new PuntoPrelievo("via ciao","B2");
        Ordine ordine1 = new Ordine(2,"Matteo","Rondini",52,StatoOrdine.RITIRATO,p);
        ordine.setStato(StatoOrdine.RITIRATO);
        assertEquals(ordine.getStato(), StatoOrdine.RITIRATO);
        assertNotEquals(ordine.getStato(), StatoOrdine.AFFIDATO_AL_CORRIERE);
        ordine1.setStato(StatoOrdine.PAGATO);
        assertEquals(ordine1.getStato(), StatoOrdine.PAGATO);

    }

    @Test
    void getDettagli() {
       Merce merce = new Merce(13,"ciao");
       Ordine ordine = new Ordine(1,2,"Matteo","Rondini",52,StatoOrdine.RITIRATO,null);
       ordine.aggiungiMerce(merce,2);
       System.out.println(ordine.getDettagli().toString());
    }

    @Test
    void addResidenza() {
        //TODO
    }
}