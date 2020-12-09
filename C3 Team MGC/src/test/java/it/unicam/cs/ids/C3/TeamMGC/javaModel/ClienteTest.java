package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClienteTest {

    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("TRUNCATE `sys`.`clienti`;");
    }

    @Test
    void creazioneCliente() {
        Cliente cliente = new Cliente("Mario", "Terzi");
        assertEquals(1, cliente.getID());
    }

    @Test
    void setCodiceRitiro() {
        Cliente cliente = new Cliente("Test", "Test");
        assertNull(cliente.getDataCreazioneCodice());
        cliente.setCodiceRitiro("Test Prova");
        Date after = cliente.getDataCreazioneCodice();
        //todo finire il test
    }
}