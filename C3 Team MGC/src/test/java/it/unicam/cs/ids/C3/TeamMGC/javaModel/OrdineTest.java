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
        Ordine ordine = new Ordine(2,"Matteo","Rondini",52);
        assertEquals(1, ordine.getID());
    }

    @Test
    void setStato() {
        Ordine ordine = new Ordine(1,2,"Matteo","Rondini",52,StatoOrdine.RITIRATO,null);
        ordine.setStato(StatoOrdine.RITIRATO);
        assertEquals(ordine.getStato(), StatoOrdine.RITIRATO);
        assertNotEquals(ordine.getStato(), StatoOrdine.AFFIDATO_AL_CORRIERE);

    }

    @Test
    void getDettagli() {
//        Merce merce = new Merce()
       Ordine ordine = new Ordine(1,2,"Matteo","Rondini",52,StatoOrdine.RITIRATO,null);
        System.out.println(ordine.getDettagli().toString());


    }
}