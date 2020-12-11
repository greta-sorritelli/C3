package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class MerceTest {
    static Merce merceTest;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("TRUNCATE `sys`.`inventario`;");
        merceTest = new Merce(1, 12, "test allSet", 10);
    }

    @Test
    void getDettagli() {
        Merce merce = new Merce(5, 6.5, "test getDettagli", 2);
        ArrayList<String> toControl = new ArrayList<>();
        toControl.add("2");
        toControl.add("5");
        toControl.add("6.5");
        toControl.add("test getDettagli");
        toControl.add("2");
        assertEquals(toControl, merce.getDettagli());
    }

    @Test
    void setDescrizione() {
        assertEquals("test allSet", merceTest.getDescrizione());
        merceTest.setDescrizione("test setDescrizione");
        assertEquals("test setDescrizione", merceTest.getDescrizione());
    }

    @Test
    void setPrezzo() {
        assertEquals(12, merceTest.getPrezzo());
        merceTest.setPrezzo(15);
        assertEquals(15, merceTest.getPrezzo());

    }

    @Test
    void setQuantita() {
        assertEquals(10, merceTest.getQuantita());
        merceTest.setQuantita(100);
        assertEquals(100, merceTest.getQuantita());
    }
}