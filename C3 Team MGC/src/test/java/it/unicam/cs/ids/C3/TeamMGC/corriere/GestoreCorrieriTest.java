package it.unicam.cs.ids.C3.TeamMGC.corriere;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestoreCorrieriTest {

    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("TRUNCATE `sys`.`corrieri`;");
        updateData("INSERT INTO `sys`.`corrieri` (`nome`, `cognome`, `stato`, `capienza`) VALUES ('Clarissa', 'Albanese', 'true', '52');");
        updateData("INSERT INTO `sys`.`corrieri` (`nome`, `cognome`, `stato`, `capienza`) VALUES ('Matteo', 'Rondini','false','13');");
        updateData("INSERT INTO `sys`.`corrieri` (`nome`,`cognome`,`stato`, `capienza`) VALUES ('Greta', 'Sorritelli','true','16');");
    }

    @AfterAll
    static void setDisponibilita() {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        gestoreCorrieri.setDisponibilita(1, false);
        gestoreCorrieri.setDisponibilita(3, true);
        assertFalse(gestoreCorrieri.getItem(1).getDisponibilita());
        assertTrue(gestoreCorrieri.getItem(3).getDisponibilita());
    }

    @Test
    void getCorriere() {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        assertEquals(52, gestoreCorrieri.getItem(1).getCapienza());
        assertEquals("Matteo", gestoreCorrieri.getItem(2).getNome());
        assertEquals("true", String.valueOf(gestoreCorrieri.getItem(3).getDisponibilita()));
    }

    @Test
    void getCorrieri() {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        ArrayList<Corriere> test = gestoreCorrieri.getItems();
        assertEquals(1, test.get(0).getID());
        assertEquals(2, test.get(1).getID());
        assertEquals(3, test.get(2).getID());
        assertEquals("Clarissa", test.get(0).getNome());
        assertEquals("Matteo", test.get(1).getNome());
        assertEquals("Greta", test.get(2).getNome());
    }

    @Test
    void getCorrieriDisponibili() {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        Corriere corriere = new Corriere(2, "Matteo", "Rondini", false, 13);
        ArrayList<Corriere> test = gestoreCorrieri.getCorrieriDisponibili();
        assertEquals(1, test.get(0).getID());
        assertEquals(3, test.get(1).getID());
        assertTrue(test.get(0).getDisponibilita());
        assertTrue(test.get(1).getDisponibilita());
        assertFalse(test.contains(corriere));
    }

    @Test
    void getDettagliCorrieri(){
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        ArrayList<ArrayList<String>> test = gestoreCorrieri.getDettagliItems();
        assertEquals(test.get(0).get(0), "Clarissa");
        assertEquals(test.get(1).get(0), "Matteo");
        assertEquals(test.get(2).get(0), "Greta");
        assertEquals(test.get(0).get(3), "52");
        assertEquals(test.get(1).get(3), "13");
        assertEquals(test.get(2).get(3), "16");
    }

    @Test
    void getDettagliCorrieriDisponibili() {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        ArrayList<ArrayList<String>> test = gestoreCorrieri.getDettagliCorrieriDisponibili();
        assertEquals(test.get(0).get(0), "Clarissa");
        assertEquals(test.get(0).get(1), "Albanese");
        assertEquals(test.get(0).get(2), "true");
        assertEquals(test.get(0).get(3), "52");
        assertEquals(test.get(1).get(0), "Greta");
        assertEquals(test.get(1).get(1), "Sorritelli");
        assertEquals(test.get(1).get(2), "true");
        assertEquals(test.get(1).get(3), "16");
        assertFalse(test.get(1).contains("Matteo"));
    }

    @Test
    void selezionaCorriere() {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        assertEquals("Clarissa", gestoreCorrieri.selezionaCorriere(1).get(0));
        assertEquals("true", gestoreCorrieri.selezionaCorriere(3).get(2));
        assertEquals("13", gestoreCorrieri.selezionaCorriere(2).get(3));
    }

    @Test
    void setCapienza() {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        gestoreCorrieri.setCapienza(1, 20);
        gestoreCorrieri.setCapienza(2, 10);
        assertEquals(20, gestoreCorrieri.getItem(1).getCapienza());
        assertEquals(10, gestoreCorrieri.getItem(2).getCapienza());
    }
}