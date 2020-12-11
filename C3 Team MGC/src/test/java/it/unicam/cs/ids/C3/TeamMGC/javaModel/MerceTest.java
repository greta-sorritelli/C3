package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class MerceTest {

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("TRUNCATE `sys`.`inventario`;");
    }

    @Test
    void creazioneMerce() {
        Merce merce = new Merce(1, 12, "test creazioneMerce", 20);
        assertEquals(1, merce.getID());
    }

    @Test
    void setQuantita() {
        Merce merce = new Merce(1, 10, "test setQuantita", 10);
        assertEquals(10, merce.getQuantita());
        merce.setQuantita(100);
        assertEquals(100, merce.getQuantita());
    }
}