package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class CorriereTest {

    @BeforeEach
    void clearDB() throws SQLException {
        updateData("TRUNCATE `sys`.`corrieri`;");
    }

    @Test
    void creazioneCorriere() {
        Corriere corriere = new Corriere(StatoCorriere.DISPONIBILE, 30);
        assertEquals(1, corriere.getID());
    }

    @Test
    void getDettagli() {
        Corriere corriere = new Corriere(StatoCorriere.DISPONIBILE, 30);
        ArrayList<String> corriere1 = new ArrayList<>();
        corriere1.add(corriere.getStato().toString());
        corriere1.add(String.valueOf(corriere.getCapienza()));
        assertEquals(corriere.getDettagli(),corriere1);
    }

    @Test
    void setStato() {
        Corriere corriere = new Corriere(StatoCorriere.DISPONIBILE, 30);
        Corriere corriere1 = new Corriere(5,StatoCorriere.NON_DISPONIBILE, 30);
        corriere.setStato(StatoCorriere.NON_DISPONIBILE);
        corriere1.setStato((StatoCorriere.DISPONIBILE));
        assertEquals(corriere.getStato(), StatoCorriere.NON_DISPONIBILE);
        assertEquals(corriere1.getStato(), StatoCorriere.DISPONIBILE);
    }

    @Test
    void setCapienza() {
        Corriere corriere = new Corriere(StatoCorriere.DISPONIBILE, 30);
        corriere.setCapienza(52);
        assertEquals(corriere.getCapienza(),52);
    }
}