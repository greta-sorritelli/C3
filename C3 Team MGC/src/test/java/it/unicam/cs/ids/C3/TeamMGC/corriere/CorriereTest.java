package it.unicam.cs.ids.C3.TeamMGC.corriere;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class CorriereTest {

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("TRUNCATE sys.corrieri;");
    }

    @BeforeAll
    static void creazioneCorriere() {
        Corriere corriere = new Corriere("Clarissa", "Albanese", true, 30);
        assertEquals(1, corriere.getID());
        assertEquals("Clarissa", corriere.getNome());
        assertEquals("Albanese", corriere.getCognome());
        assertTrue(corriere.getDisponibilita());
        assertEquals(30, corriere.getCapienza());
    }

    @Test
    void getDettagli() throws SQLException {
        Corriere corriereTest = new Corriere("Mario", "Rossi", true, 30);
        int IDTest = corriereTest.getID();
        ArrayList<String> corriere1 = new ArrayList<>();
        corriere1.add("Mario");
        corriere1.add("Rossi");
        corriere1.add("true");
        corriere1.add("30");
        assertEquals(corriere1, corriereTest.getDettagli());

        updateData("UPDATE sys.corrieri SET capienza = '15' WHERE (ID = '" + IDTest + "');");
        assertNotEquals(corriere1, corriereTest.getDettagli());
        corriere1.clear();
        corriere1.add("Mario");
        corriere1.add("Rossi");
        corriere1.add("true");
        corriere1.add("15");
        assertEquals(corriere1, corriereTest.getDettagli());
    }

    @Test
    void setCapienza() {
        Corriere corriereTest = new Corriere("Matteo", "Rondini", true, 30);
        assertEquals(30, corriereTest.getCapienza());
        corriereTest.setCapienza(52);
        assertEquals(52, corriereTest.getCapienza());
    }

    @Test
    void setStato() {
        Corriere corriereTest = new Corriere("Greta", "Sorritelli", true, 30);
        assertTrue(corriereTest.getDisponibilita());
        corriereTest.setDisponibilita(false);
        assertFalse(corriereTest.getDisponibilita());
    }
}