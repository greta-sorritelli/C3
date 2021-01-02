package it.unicam.cs.ids.C3.TeamMGC.cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClienteTest {

    @BeforeEach
    void clearDB() throws SQLException {
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
    }

    @Test
    void creazioneCliente() {
        Cliente cliente = new Cliente("Mario", "Rossi");
        assertEquals(1, cliente.getID());
    }

    @Test
    void setCodiceRitiro() {
        Cliente cliente = new Cliente("Matteo", "Rondini");
        String data = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
        assertNull(cliente.getDataCreazioneCodice());
        cliente.setCodiceRitiro("85963214");
        assertEquals("85963214", cliente.getCodiceRitiro());
        assertEquals(data, cliente.getDataCreazioneCodice());
    }
}