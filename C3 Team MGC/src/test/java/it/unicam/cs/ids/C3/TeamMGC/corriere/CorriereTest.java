package it.unicam.cs.ids.C3.TeamMGC.corriere;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class CorriereTest {

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("TRUNCATE sys.corrieri;");
    }

    @BeforeAll
    static void creazioneCorriere() throws SQLException {
        Corriere corriere = new Corriere("Clarissa", "Albanese", true);
        assertEquals(1, corriere.getID());
        assertEquals("Clarissa", corriere.getNome());
        assertEquals("Albanese", corriere.getCognome());
        assertTrue(corriere.getDisponibilita());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Corriere(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getDettagli() throws SQLException {
        Corriere corriereTest = new Corriere("Mario", "Rossi", true);
        ArrayList<String> corriereLista = new ArrayList<>();
        corriereLista.add(String.valueOf(corriereTest.getID()));
        corriereLista.add("Mario");
        corriereLista.add("Rossi");
        corriereLista.add("true");
        assertEquals(corriereLista, corriereTest.getDettagli());

        corriereTest.setDisponibilita(false);
        corriereLista.clear();
        corriereLista.add(String.valueOf(corriereTest.getID()));
        corriereLista.add("Mario");
        corriereLista.add("Rossi");
        corriereLista.add("false");
        assertEquals(corriereLista, corriereTest.getDettagli());
    }

//    @Test
//    void setCapienza() throws SQLException {
//        Corriere corriereTest = new Corriere("Matteo", "Rondini", true);
//        assertEquals(30, corriereTest.getCapienza());
//        corriereTest.setCapienza(52);
//        assertEquals(52, corriereTest.getCapienza());
//
//        Exception e1 = assertThrows(IllegalArgumentException.class, () -> corriereTest.setCapienza(-10));
//        assertEquals("Capienza non valida.", e1.getMessage());
//
//        corriereTest.setCapienza(0);
//        assertEquals(0, corriereTest.getCapienza());
//        assertFalse(corriereTest.getDisponibilita());
//    }

    @Test
    void setDisponibilita() throws SQLException {
        Corriere corriereTest = new Corriere("Greta", "Sorritelli", true);
        assertTrue(corriereTest.getDisponibilita());
        corriereTest.setDisponibilita(false);
        assertFalse(corriereTest.getDisponibilita());
    }

    @Test
    void testEquals() throws SQLException {
        Corriere corriere = new Corriere("Fry", "Philip", true);
        Corriere corriereCopia = new Corriere(corriere.getID());
        Corriere corriere2 = new Corriere("Leela", "Turanga", true);
        assertEquals(corriere, corriereCopia);
        assertNotEquals(corriere, corriere2);
    }

    @Test
    void update() throws SQLException {
        Corriere corriereTest = new Corriere("Mario", "Rossi", true);
        int IDTest = corriereTest.getID();
        assertEquals(corriereTest.getNome(), "Mario");
        assertEquals(corriereTest.getCognome(), "Rossi");
        assertTrue(corriereTest.getDisponibilita());

        updateData("UPDATE sys.corrieri SET nome = 'Giovanni' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.corrieri SET cognome = 'Bazzi' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.corrieri SET stato = 'false' WHERE (ID = '" + IDTest + "');");

        corriereTest.update();
        assertEquals(corriereTest.getNome(), "Giovanni");
        assertEquals(corriereTest.getCognome(), "Bazzi");
        assertFalse(corriereTest.getDisponibilita());
    }
}