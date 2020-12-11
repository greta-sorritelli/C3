package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteTest {

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("TRUNCATE `sys`.`clienti`;");
    }

    @Test
    void creazioneCliente() {
       Cliente cliente = new Cliente("Mario", "Rossi");
       assertEquals(1, cliente.getID());
    }

    @Test
    void setCodiceRitiro() {
        Cliente cliente = new Cliente("Matteo", "Rondini");
//        assertNull(cliente.getDataCreazioneCodice());
        cliente.setCodiceRitiro("85963214");
        assertEquals(2, cliente.getID());
//        Date after = cliente.getDataCreazioneCodice();
        //todo finire il test
    }
}