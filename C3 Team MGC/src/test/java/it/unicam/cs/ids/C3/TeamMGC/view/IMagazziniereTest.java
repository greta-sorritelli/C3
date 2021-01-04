package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IMagazziniereTest {

    static PuntoPrelievo puntoPrelievo;


    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
        puntoPrelievo = new PuntoPrelievo("Castelraimondo", "A1");
    }

    @Test
    void cercaClienteTest() {
        Cliente cliente = new Cliente("Mario", "Rossi");
        IMagazziniere m = new IMagazziniere();
        assertEquals(cliente, m.cercaCliente(1));
        assertNull(m.cercaCliente(2));
    }

}