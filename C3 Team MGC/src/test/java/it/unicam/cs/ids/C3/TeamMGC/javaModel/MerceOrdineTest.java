package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class MerceOrdineTest {
    static MerceOrdine merceOrdineTest;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("TRUNCATE `sys`.`merci`;");
        merceOrdineTest = new MerceOrdine(10, "test allSet", StatoOrdine.PAGATO);
    }

    @Test
    void setDescrizione() {
        assertEquals("test allSet", merceOrdineTest.getDescrizione());
        merceOrdineTest.setDescrizione("test setDescrizione");
        assertEquals("test setDescrizione", merceOrdineTest.getDescrizione());
    }

    @Test
    void setIDOrdine() {
        assertEquals(-1, merceOrdineTest.getIDOrdine());
        merceOrdineTest.setIDOrdine(50);
        assertEquals(50, merceOrdineTest.getIDOrdine());
    }

    @Test
    void setPrezzo() {
        assertEquals(10, merceOrdineTest.getPrezzo());
        merceOrdineTest.setPrezzo(100);
        assertEquals(100, merceOrdineTest.getPrezzo());
    }

    @Test
    void setQuantita() {
        assertEquals(0, merceOrdineTest.getQuantita());
        merceOrdineTest.setQuantita(10);
        assertEquals(10, merceOrdineTest.getQuantita());
    }

    @Test
    void setStato() {
        assertEquals(StatoOrdine.PAGATO, merceOrdineTest.getStato());
        merceOrdineTest.setStato(StatoOrdine.AFFIDATO_AL_CORRIERE);
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, merceOrdineTest.getStato());
    }
}