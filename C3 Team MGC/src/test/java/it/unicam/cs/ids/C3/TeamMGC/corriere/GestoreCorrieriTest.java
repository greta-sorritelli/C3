package it.unicam.cs.ids.C3.TeamMGC.corriere;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestoreCorrieriTest {

    static final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();

    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("TRUNCATE sys.corrieri;");
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato) VALUES ('Clarissa', 'Albanese', 'true');");
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato) VALUES ('Matteo', 'Rondini', 'false');");
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato) VALUES ('Greta', 'Sorritelli', 'true');");
    }

    @AfterAll
    static void setDisponibilita() throws SQLException {
        gestoreCorrieri.setDisponibilita(1, false);
        gestoreCorrieri.setDisponibilita(3, true);
        assertFalse(gestoreCorrieri.getItem(1).getDisponibilita());
        assertTrue(gestoreCorrieri.getItem(3).getDisponibilita());
    }

    @Test
    void addCorriere() throws SQLException {
        ArrayList<String> dettagli = gestoreCorrieri.inserisciDati("Luigi", "Bianchi");

        assertEquals("Luigi", dettagli.get(1));
        assertEquals("Bianchi", dettagli.get(2));
        assertEquals("true", dettagli.get(3));
    }

    @Test
    void inserisciDati() throws SQLException {
        ArrayList<String> test = gestoreCorrieri.inserisciDati("Giuseppe","Bianchi");
        assertTrue(gestoreCorrieri.getItems().contains(gestoreCorrieri.getItem(Integer.parseInt(test.get(0)))));
        assertEquals("Giuseppe",gestoreCorrieri.getItem(Integer.parseInt(test.get(0))).getNome());
        assertEquals("Bianchi",gestoreCorrieri.getItem(Integer.parseInt(test.get(0))).getCognome());
    }

    @Test
    void getCorriere() throws SQLException {
        assertEquals("Matteo", gestoreCorrieri.getItem(2).getNome());
        assertEquals("false", String.valueOf(gestoreCorrieri.getItem(3).getDisponibilita()));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreCorrieri.getItem(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getCorrieri() throws SQLException {
        ArrayList<SimpleCorriere> test = gestoreCorrieri.getItems();
        assertEquals(1, test.get(0).getID());
        assertEquals(2, test.get(1).getID());
        assertEquals(3, test.get(2).getID());
        assertEquals("Clarissa", test.get(0).getNome());
        assertEquals("Matteo", test.get(1).getNome());
        assertEquals("Greta", test.get(2).getNome());
    }

    @Test
    void getCorrieriDisponibili() throws SQLException {
        SimpleCorriere simpleCorriere = new SimpleCorriere("Matteo", "Rondini", false);

        gestoreCorrieri.getItems().get(0).setDisponibilita(true);
        gestoreCorrieri.getItems().get(2).setDisponibilita(true);

        ArrayList<SimpleCorriere> test = gestoreCorrieri.getCorrieriDisponibili();

        assertEquals(1, test.get(0).getID());
        assertEquals(3, test.get(1).getID());
        assertTrue(test.get(0).getDisponibilita());
        assertTrue(test.get(1).getDisponibilita());
        assertFalse(test.contains(simpleCorriere));

        gestoreCorrieri.setDisponibilita(test.get(0).getID(),false);
        gestoreCorrieri.setDisponibilita(test.get(1).getID(),false);
        gestoreCorrieri.setDisponibilita(test.get(2).getID(),false);

        Exception e1 = assertThrows(IllegalArgumentException.class, gestoreCorrieri::getCorrieriDisponibili);
        assertEquals("Corrieri disponibili non presenti.", e1.getMessage());
    }

    @Test
    void getDettagliCorrieri() throws SQLException {
        ArrayList<ArrayList<String>> test = gestoreCorrieri.getDettagliItems();
        assertEquals("Clarissa",test.get(0).get(1));
        assertEquals("Matteo",test.get(1).get(1));
        assertEquals("Greta",test.get(2).get(1) );
        assertEquals("Albanese",test.get(0).get(2));
        assertEquals("Rondini",test.get(1).get(2));
        assertEquals("Sorritelli",test.get(2).get(2));
        assertEquals("false",test.get(0).get(3));
        assertEquals("false",test.get(1).get(3) );
        assertEquals("false",test.get(2).get(3));
    }

    @Test
    void getDettagliCorrieriDisponibili() throws SQLException {
        ArrayList<ArrayList<String>> test = gestoreCorrieri.getDettagliCorrieriDisponibili();
        assertEquals("Clarissa",test.get(0).get(1));
        assertEquals("Albanese",test.get(0).get(2));
        assertEquals("true",test.get(0).get(3));
        assertEquals("Greta",test.get(1).get(1));
        assertEquals("Sorritelli",test.get(1).get(2));
        assertEquals("true",test.get(1).get(3));
        assertEquals(2, test.size());

        gestoreCorrieri.setDisponibilita(Integer.parseInt(test.get(0).get(0)),false);
        gestoreCorrieri.setDisponibilita(Integer.parseInt(test.get(1).get(0)),false);

        Exception e1 = assertThrows(IllegalArgumentException.class, gestoreCorrieri::getDettagliCorrieriDisponibili);
        assertEquals("Corrieri disponibili non presenti.", e1.getMessage());
    }

    @Test
    void selezionaCorriere() throws SQLException {
        assertEquals("Clarissa", gestoreCorrieri.selezionaCorriere(1).get(1));
        assertEquals("false", gestoreCorrieri.selezionaCorriere(3).get(3));
    }
}