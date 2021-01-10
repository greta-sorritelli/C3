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
        updateData("TRUNCATE sys.corrieri;");
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato, capienza) VALUES ('Clarissa', 'Albanese', 'true', '52');");
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato, capienza) VALUES ('Matteo', 'Rondini', 'false', '13');");
        updateData("INSERT INTO sys.corrieri (nome,cognome,stato, capienza) VALUES ('Greta', 'Sorritelli', 'true', '16');");
    }

    @AfterAll
    static void setDisponibilita() throws SQLException {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        gestoreCorrieri.setDisponibilita(1, false);
        gestoreCorrieri.setDisponibilita(3, true);
        assertFalse(gestoreCorrieri.getItem(1).getDisponibilita());
        assertTrue(gestoreCorrieri.getItem(3).getDisponibilita());
    }

    @Test
    void addCorriere() throws SQLException {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        ArrayList<String> dettagli = gestoreCorrieri.inserisciDati("Luigi", "Bianchi", 20);

        assertEquals("Luigi", dettagli.get(1));
        assertEquals("Bianchi", dettagli.get(2));
        assertEquals("true", dettagli.get(3));
        assertEquals("20", dettagli.get(4));
    }

    @Test
    void inserisciDati() throws SQLException {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        ArrayList<String> test = gestoreCorrieri.inserisciDati("Giuseppe","Bianchi",100);
        assertTrue(gestoreCorrieri.getItems().contains(gestoreCorrieri.getItem(Integer.parseInt(test.get(0)))));
        assertEquals("Giuseppe",gestoreCorrieri.getItem(Integer.parseInt(test.get(0))).getNome());
        assertEquals("Bianchi",gestoreCorrieri.getItem(Integer.parseInt(test.get(0))).getCognome());
        assertEquals(100,gestoreCorrieri.getItem(Integer.parseInt(test.get(0))).getCapienza());
    }

    @Test
    void getCorriere() throws SQLException {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        assertEquals(52, gestoreCorrieri.getItem(1).getCapienza());
        assertEquals("Matteo", gestoreCorrieri.getItem(2).getNome());
        assertEquals("false", String.valueOf(gestoreCorrieri.getItem(3).getDisponibilita()));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreCorrieri.getItem(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getCorrieri() throws SQLException {
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
    void getCorrieriDisponibili() throws SQLException {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        Corriere corriere = new Corriere("Matteo", "Rondini", false, 13);

        gestoreCorrieri.getItems().get(0).setDisponibilita(true);
        gestoreCorrieri.getItems().get(2).setDisponibilita(true);

        ArrayList<Corriere> test = gestoreCorrieri.getCorrieriDisponibili();

        assertEquals(1, test.get(0).getID());
        assertEquals(3, test.get(1).getID());
        assertTrue(test.get(0).getDisponibilita());
        assertTrue(test.get(1).getDisponibilita());
        assertFalse(test.contains(corriere));

        gestoreCorrieri.setDisponibilita(test.get(0).getID(),false);
        gestoreCorrieri.setDisponibilita(test.get(1).getID(),false);
        gestoreCorrieri.setDisponibilita(test.get(2).getID(),false);

        Exception e1 = assertThrows(IllegalArgumentException.class, gestoreCorrieri::getCorrieriDisponibili);
        assertEquals("Corrieri disponibili non presenti.", e1.getMessage());
    }

    @Test
    void getDettagliCorrieri() throws SQLException {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        ArrayList<ArrayList<String>> test = gestoreCorrieri.getDettagliItems();
        assertEquals(test.get(0).get(1), "Clarissa");
        assertEquals(test.get(1).get(1), "Matteo");
        assertEquals(test.get(2).get(1), "Greta");
        assertEquals(test.get(0).get(4), "52");
        assertEquals(test.get(1).get(4), "13");
        assertEquals(test.get(2).get(4), "16");
    }

    @Test
    void getDettagliCorrieriDisponibili() throws SQLException {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        ArrayList<ArrayList<String>> test = gestoreCorrieri.getDettagliCorrieriDisponibili();
        assertEquals(test.get(0).get(1), "Clarissa");
        assertEquals(test.get(0).get(2), "Albanese");
        assertEquals(test.get(0).get(3), "true");
        assertEquals(test.get(0).get(4), "52");
        assertEquals(test.get(1).get(1), "Greta");
        assertEquals(test.get(1).get(2), "Sorritelli");
        assertEquals(test.get(1).get(3), "true");
        assertEquals(test.get(1).get(4), "16");
        assertEquals(2, test.size());

        gestoreCorrieri.setDisponibilita(Integer.parseInt(test.get(0).get(0)),false);
        gestoreCorrieri.setDisponibilita(Integer.parseInt(test.get(1).get(0)),false);

        Exception e1 = assertThrows(IllegalArgumentException.class, gestoreCorrieri::getDettagliCorrieriDisponibili);
        assertEquals("Corrieri disponibili non presenti.", e1.getMessage());
    }

    @Test
    void selezionaCorriere() throws SQLException {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        assertEquals("Clarissa", gestoreCorrieri.selezionaCorriere(1).get(1));
        assertEquals("false", gestoreCorrieri.selezionaCorriere(3).get(3));
        assertEquals("13", gestoreCorrieri.selezionaCorriere(2).get(4));
    }

    @Test
    void setCapienza() throws SQLException {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        gestoreCorrieri.setCapienza(1, 20);
        gestoreCorrieri.setCapienza(2, 10);
        assertEquals(20, gestoreCorrieri.getItem(1).getCapienza());
        assertEquals(10, gestoreCorrieri.getItem(2).getCapienza());

        gestoreCorrieri.setCapienza(1, 0);
        assertEquals(0, gestoreCorrieri.getItem(1).getCapienza());
        assertFalse(gestoreCorrieri.getDisponibilita(1));
    }
}