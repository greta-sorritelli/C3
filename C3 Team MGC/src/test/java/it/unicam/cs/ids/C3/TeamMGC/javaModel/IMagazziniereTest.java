package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IMagazziniereTest {


    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("TRUNCATE `sys`.`clienti`;");
    }

    @Test
    void cercaClienteTest() {
        Cliente cliente = new Cliente("Mario", "Rossi");
        IMagazziniere m = new IMagazziniere();
        assertEquals(cliente, m.cercaCliente(1));
        assertNull(m.cercaCliente(2));
    }

    @Test
    void mandaAlertTest() {
    }

    @Test
    void verificaCodiceTest() {
    }
}
