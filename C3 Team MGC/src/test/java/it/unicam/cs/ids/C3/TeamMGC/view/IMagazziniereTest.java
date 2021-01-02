package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

public class IMagazziniereTest {

    static PuntoPrelievo puntoPrelievo = new PuntoPrelievo("Castelraimondo", "A1");


    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
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
        //todo
    }

    @Test
    void verificaCodiceTest() {
        ICommesso commesso = new ICommesso();
        Cliente cliente = new Cliente("Mario", "Rossi");
        Ordine ordine = new Ordine(cliente.getID(), "Mario", "Rossi");
        assertNull(cliente.getCodiceRitiro());
        String codice = commesso.verificaEsistenzaCodice(cliente, ordine);
        assertNotNull(cliente.getCodiceRitiro());
        assertEquals(codice, cliente.getCodiceRitiro());
        IMagazziniere m = new IMagazziniere();
        assertTrue(m.verificaCodice(cliente.getID(), codice));
    }
}