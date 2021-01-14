package it.unicam.cs.ids.C3.TeamMGC.cliente;

import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleOrdine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestoreClientiTest {

    static final GestoreClienti gestoreClienti = GestoreClienti.getInstance();

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
        updateData("INSERT INTO sys.clienti (nome, cognome) VALUES ('Clarissa', 'Albanese');");
        updateData("INSERT INTO sys.clienti (nome, cognome) VALUES ('Matteo', 'Rondini');");
    }

    @Test
    void getCliente() throws SQLException {
        assertEquals("Clarissa", gestoreClienti.getItem(1).getNome());
        assertEquals("Matteo", gestoreClienti.getItem(2).getNome());
        assertEquals("Albanese", gestoreClienti.getItem(1).getCognome());
        assertEquals("Rondini", gestoreClienti.getItem(2).getCognome());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreClienti.getItem(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getClienti() throws SQLException {
        ArrayList<SimpleCliente> test = gestoreClienti.getItems();
        assertEquals(1, test.get(0).getID());
        assertEquals(2, test.get(1).getID());
        assertEquals("Clarissa", test.get(0).getNome());
        assertEquals("Matteo", test.get(1).getNome());
        assertEquals("Albanese", test.get(0).getCognome());
        assertEquals("Rondini", test.get(1).getCognome());
    }

    @Test
    void getDettagliClienti() throws SQLException {
        ArrayList<ArrayList<String>> test = gestoreClienti.getDettagliItems();
        assertEquals(test.get(0).get(0), "1");
        assertEquals(test.get(0).get(1), "Clarissa");
        assertEquals(test.get(0).get(2), "Albanese");
        assertEquals(test.get(1).get(0), "2");
        assertEquals(test.get(1).get(1), "Matteo");
        assertEquals(test.get(1).get(2), "Rondini");
    }

    @Test
    void inserisciDati() throws SQLException {
        ArrayList<String> test = gestoreClienti.inserisciDati("Sabrina","Spellman");
        assertTrue(gestoreClienti.getItems().contains(gestoreClienti.getItem(Integer.parseInt(test.get(0)))));
        assertEquals("Sabrina",gestoreClienti.getItem(Integer.parseInt(test.get(0))).getNome());
        assertEquals("Spellman",gestoreClienti.getItem(Integer.parseInt(test.get(0))).getCognome());

    }

    @Test
    void verificaCodice() throws SQLException {
        ArrayList<String> dettagli = gestoreClienti.inserisciDati("Mario", "Rossi");
        Negozio negozio = new Negozio("Trinkets", "Cleptomania", null, null, "Via delle Trombette", null);
        SimpleOrdine simpleOrdine = new SimpleOrdine(Integer.parseInt(dettagli.get(0)), dettagli.get(1), dettagli.get(2), negozio.getID());
        String codice = gestoreClienti.verificaEsistenzaCodice(Integer.parseInt(dettagli.get(0)), simpleOrdine.getID());
        assertTrue(gestoreClienti.verificaCodice(Integer.parseInt(dettagli.get(0)),codice));
        assertFalse(gestoreClienti.verificaCodice(Integer.parseInt(dettagli.get(0)),"1256"));

    }

    @Test
    void verificaEsistenzaCodiceTest() throws SQLException {
        ArrayList<String> dettagli = gestoreClienti.inserisciDati("Mario", "Rossi");
        Negozio negozio = new Negozio("Trinkets", "Cleptomania", null, null, "Via delle Trombette", null);
        SimpleOrdine simpleOrdine = new SimpleOrdine(Integer.parseInt(dettagli.get(0)), dettagli.get(1), dettagli.get(2), negozio.getID());
        String codice = gestoreClienti.verificaEsistenzaCodice(Integer.parseInt(dettagli.get(0)), simpleOrdine.getID());
        SimpleCliente simpleCliente = gestoreClienti.getItem(Integer.parseInt(dettagli.get(0)));
        assertNotEquals("", simpleCliente.getCodiceRitiro());
        assertEquals(codice, simpleCliente.getCodiceRitiro());
        assertTrue(gestoreClienti.verificaCodice(simpleCliente.getID(), codice));
        assertEquals(codice, gestoreClienti.verificaEsistenzaCodice(Integer.parseInt(dettagli.get(0)), simpleOrdine.getID()));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreClienti.verificaEsistenzaCodice(1000, 1000));
        assertEquals("ID non valido.", e1.getMessage());
    }
}