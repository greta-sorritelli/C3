package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdineTest {

    @Test
    void setStato() {
        Ordine ordine = new Ordine(5);
        ordine.setStato(StatoOrdine.RITIRATO);
        assertEquals(ordine.getStato(), StatoOrdine.RITIRATO);
        assertNotEquals(ordine.getStato(), StatoOrdine.AFFIDATO_AL_CORRIERE);

    }
}