package it.unicam.cs.ids.C3.TeamMGC.javaModel;

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
        updateData("INSERT INTO `sys`.`corrieri` (`stato`, `capienza`) VALUES ('DISPONIBILE','52');");
        updateData("INSERT INTO `sys`.`corrieri` (`stato`, `capienza`) VALUES ('NON_DISPONIBILE','13');");
        updateData("INSERT INTO `sys`.`corrieri` (`stato`, `capienza`) VALUES ('DISPONIBILE','16');");
    }

    @Test
    void getCorrieriDisponibili() {
        GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
        ArrayList<Corriere> test = gestoreCorrieri.getCorrieriDisponibili();
        assertEquals(test.get(0).getID(), 1);
        assertEquals(test.get(1).getDisponibilita(), true);
    }
}