package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

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
    void verificaCodiceTest() throws SQLException {
        GestoreClienti gestoreClienti = new GestoreClienti();
        ArrayList<String> dettagli = gestoreClienti.inserisciDati("Mario","Rossi");
        Ordine ordine = new Ordine(Integer.parseInt(dettagli.get(0)), "Mario", "Rossi");
        String codice = gestoreClienti.verificaEsistenzaCodice(Integer.parseInt(dettagli.get(0)), ordine.getID());
        Cliente cliente = gestoreClienti.getItem(Integer.parseInt(dettagli.get(0)));
        assertNotNull(cliente.getCodiceRitiro());
        assertEquals(codice, cliente.getCodiceRitiro());
        IMagazziniere m = new IMagazziniere();
        assertTrue(m.verificaCodice(cliente.getID(), codice));
    }
}