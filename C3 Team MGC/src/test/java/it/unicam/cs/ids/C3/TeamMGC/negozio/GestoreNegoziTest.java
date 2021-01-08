package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GestoreNegoziTest {

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

    //todo controllare addNegozio (import dati dal database)
    @Test
    void getItem() throws SQLException {
//        GestoreNegozi gestoreNegozi = new GestoreNegozi();
//        assertEquals("Via Culmone", gestoreNegozi.getItem(1).getIndirizzo());
//        ArrayList<String> prova = new ArrayList<>();
//        prova.add("1");
//        prova.add("Emporio");
//        prova.add(null);
//        prova.add(null);
//        prova.add(null);
//        prova.add("Via Culmone");
//        prova.add(null);
//        prova.add(null);
//        assertEquals(gestoreNegozi.getItem(1).getDettagli(), prova);
//        assertEquals("Emporio", gestoreNegozi.getItem(1).getNome());
//        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreNegozi.getItem(1000));
//        assertEquals("ID non valido.", e1.getMessage());
    }

    //todo controllare addNegozio (import dati dal database)
    @Test
    void getItems() throws SQLException {
        GestoreNegozi gestoreNegozi = new GestoreNegozi();
        ArrayList<Negozio> test = gestoreNegozi.getItems();
        assertEquals(1, test.get(0).getIDNegozio());
        assertEquals(2, test.get(1).getIDNegozio());
        assertEquals(3, test.get(2).getIDNegozio());
//        String nome = test.get(0).getNome();
//        assertEquals("Emporio", nome);
//        assertEquals("Burrobirra", test.get(1).getNome());
//        assertEquals("Ollivander", test.get(2).getNome());
    }

    @Test
    void getDettagliItems() throws SQLException {
        GestoreNegozi gestoreNegozi = new GestoreNegozi();
        ArrayList<ArrayList<String>> test = gestoreNegozi.getDettagliItems();
        assertEquals(test.get(0).get(1), "Emporio");
        assertEquals(test.get(0).get(5), "Via Culmone");
        assertEquals(test.get(1).get(1), "Burrobirra");
        assertEquals(test.get(1).get(5), "Via Hagrid");
        assertEquals(test.get(2).get(1), "Ollivander");
        assertEquals(test.get(2).get(5), "Via Vol de Mort");
    }


    //todo finire
    @Test
    void getDettagliItemsConOrdini() throws SQLException {
        GestoreNegozi gestoreNegozi = new GestoreNegozi();
        Cliente cliente = new Cliente("Yoshi", "Haloa");
        Ordine ordine1 = new Ordine(cliente.getID(), cliente.getNome(), cliente.getCognome(), 1);
        ordine1.setStato(StatoOrdine.PAGATO);
        assertEquals(1, gestoreNegozi.getDettagliItemsConOrdini().size());
        System.out.println(gestoreNegozi.getDettagliItemsConOrdini());
        assertEquals(gestoreNegozi.getDettagliItems().get(0).get(1), "Emporio");
        assertEquals(gestoreNegozi.getDettagliItems().get(0).get(5), "Via Culmone");
        ordine1.setStato(StatoOrdine.IN_DEPOSITO);
        assertEquals(0, gestoreNegozi.getDettagliItemsConOrdini().size());
    }
}