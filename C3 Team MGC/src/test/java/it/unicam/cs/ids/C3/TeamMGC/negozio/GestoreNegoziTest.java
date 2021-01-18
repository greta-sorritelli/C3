package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GestoreNegoziTest {

    static final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();

    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("delete from sys.negozi;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        updateData("delete from sys.ordini;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        updateData("INSERT INTO sys.negozi (nome, indirizzo) VALUES ('Emporio', 'Via Culmone');");
        updateData("INSERT INTO sys.negozi (nome, indirizzo) VALUES ('Burrobirra', 'Via Hagrid');");
        updateData("INSERT INTO sys.negozi (nome, indirizzo) VALUES ('Ollivander', 'Via Vol de Mort');");
    }

    @Test
    void getItem() throws SQLException {
        assertEquals("Via Culmone", gestoreNegozi.getItem(1).getIndirizzo());
        ArrayList<String> prova = new ArrayList<>();
        prova.add("1");
        prova.add("Emporio");
        prova.add(null);
        prova.add(null);
        prova.add(null);
        prova.add("Via Culmone");
        prova.add(null);
        prova.add("[]");
        assertEquals(gestoreNegozi.getItem(1).getDettagli(), prova);
        assertEquals("Emporio", gestoreNegozi.getItem(1).getNome());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreNegozi.getItem(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getItems() throws SQLException {
        ArrayList<GestoreInventario> test = gestoreNegozi.getItems();
        assertEquals(1, test.get(0).getID());
        assertEquals(2, test.get(1).getID());
        assertEquals(3, test.get(2).getID());
        assertEquals("Emporio", test.get(0).getNome());
        assertEquals("Burrobirra", test.get(1).getNome());
        assertEquals("Ollivander", test.get(2).getNome());
    }

    @Test
    void getDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> test = gestoreNegozi.getDettagliItems();
        assertEquals(test.get(0).get(1), "Emporio");
        assertEquals(test.get(0).get(5), "Via Culmone");
        assertEquals(test.get(1).get(1), "Burrobirra");
        assertEquals(test.get(1).get(5), "Via Hagrid");
        assertEquals(test.get(2).get(1), "Ollivander");
        assertEquals(test.get(2).get(5), "Via Vol de Mort");
    }

    @Test
    void getDettagliItemsConOrdini() throws SQLException {
        Cliente simpleCliente = new SimpleCliente("Yoshi", "Haloa");
        SimpleOrdine simpleOrdine1 = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), 1);
        PuntoPrelievo magazzino = new SimplePuntoPrelievo("Matelica", "Giardini");
        simpleOrdine1.setPuntoPrelievo(magazzino.getID());
        simpleOrdine1.setStato(StatoOrdine.PAGATO);

        assertEquals(1, gestoreNegozi.getDettagliItemsConOrdini(magazzino.getID()).size());
        assertEquals("1", gestoreNegozi.getDettagliItemsConOrdini(magazzino.getID()).get(0).get(0));
        assertEquals("Emporio", gestoreNegozi.getDettagliItemsConOrdini(magazzino.getID()).get(0).get(1));
        assertEquals("Via Culmone", gestoreNegozi.getDettagliItemsConOrdini(magazzino.getID()).get(0).get(5));
        simpleOrdine1.setStato(StatoOrdine.IN_DEPOSITO);
        assertEquals(0, gestoreNegozi.getDettagliItemsConOrdini(magazzino.getID()).size());
    }
}