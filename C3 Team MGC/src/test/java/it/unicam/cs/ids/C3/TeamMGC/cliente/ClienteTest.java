package it.unicam.cs.ids.C3.TeamMGC.cliente;

import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteTest {

    @BeforeEach
    void clearDB() throws SQLException {
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
    }

    @Test
    void creazioneCliente() throws SQLException {
        Cliente cliente = new Cliente("Mario", "Rossi");
        assertEquals(1, cliente.getID());
    }

    @Test
    void getDettagli() throws SQLException {
        Cliente clienteTest = new Cliente("Greta", "Sorritelli");
        ArrayList<String> clienteLista = new ArrayList<>();
        clienteLista.add(String.valueOf(clienteTest.getID()));
        clienteLista.add("Greta");
        clienteLista.add("Sorritelli");
        clienteLista.add("");
        clienteLista.add("");
        assertEquals(clienteLista, clienteTest.getDettagli());

        clienteTest.setCodiceRitiro("123456789");
        String data = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
        clienteLista.clear();
        clienteLista.add(String.valueOf(clienteTest.getID()));
        clienteLista.add("Greta");
        clienteLista.add("Sorritelli");
        clienteLista.add("123456789");
        clienteLista.add(data);
        assertEquals(clienteLista, clienteTest.getDettagli());
    }

    @Test
    void setCodiceRitiro() throws SQLException {
        Cliente cliente = new Cliente("Matteo", "Rondini");
        String data = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
        assertEquals("", cliente.getDataCreazioneCodice());
        cliente.setCodiceRitiro("85963214");
        assertEquals("85963214", cliente.getCodiceRitiro());
        assertEquals(data, cliente.getDataCreazioneCodice());
    }

    @Test
    void update() throws SQLException {
        Cliente clienteTest = new Cliente("Luigi", "Bianchi");
        int IDTest = clienteTest.getID();
        assertEquals("Luigi", clienteTest.getNome());
        assertEquals("Bianchi", clienteTest.getCognome());
        assertEquals("", clienteTest.getCodiceRitiro());
        assertEquals("", clienteTest.getDataCreazioneCodice());

        updateData("UPDATE sys.clienti SET nome = 'Waluigi' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.clienti SET cognome = 'Neri' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.clienti SET codiceRitiro = '123456789' WHERE (ID = '" + IDTest + "');");
        String data = new SimpleDateFormat("yyyy-MM-dd").format(java.sql.Date.from(Instant.now()));
        updateData("UPDATE sys.clienti SET dataCreazione = '" + data + "' WHERE (ID = '" + IDTest + "');");

        clienteTest.update();
        assertEquals("Waluigi", clienteTest.getNome());
        assertEquals("Neri", clienteTest.getCognome());
        assertEquals("123456789", clienteTest.getCodiceRitiro());
        assertEquals(data, clienteTest.getDataCreazioneCodice());
    }
}