package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestoreNegoziTest {

    static final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();

    @BeforeAll
    static void preparaDB() throws SQLException {
        gestoreNegozi.reset();
        updateData("delete from sys.negozi;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        updateData("delete from sys.ordini;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        updateData("INSERT INTO sys.negozi (nome, indirizzo, categoria, orarioApertura, orarioChiusura, telefono) VALUES ('Emporio', 'Via Culmone', 'SPORT', '08:00', '18:00', 36985214);");
        updateData("INSERT INTO sys.negozi (nome, indirizzo, categoria, orarioApertura, orarioChiusura, telefono) VALUES ('Burrobirra', 'Via Hagrid', 'ALIMENTARI', '08:30', '18:30', 5775588);");
        updateData("INSERT INTO sys.negozi (nome, indirizzo, categoria, orarioApertura, orarioChiusura, telefono) VALUES ('Ollivander', 'Via Vol de Mort', 'ABBIGLIAMENTO', '09:00', '17:00', 7854169);");
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
        Ordine simpleOrdine1 = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), 1);
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

    @Test
    void getDettagliItemsWithPromozioni() throws SQLException {
        Negozio negozio = new SimpleNegozio("VinoVita", CategoriaNegozio.ALIMENTARI, "08:00", "20:00", "Via Montella", "336 5241639");
        ArrayList<String> merce = negozio.inserisciNuovaMerce(15, "mouse", 20);
        negozio.lanciaPromozione(Integer.parseInt(merce.get(0)), 10, "Promozione mouse");
        assertTrue(gestoreNegozi.getDettagliItemsWithPromozioni(CategoriaNegozio.ALIMENTARI).contains(negozio.getDettagli()));
        assertEquals(negozio.getID(), Integer.parseInt(gestoreNegozi.getDettagliItemsWithPromozioni(CategoriaNegozio.ALIMENTARI).get(0).get(0)));
    }

    @Test
    void getItem() throws SQLException {
        assertEquals("Via Culmone", gestoreNegozi.getItem(1).getIndirizzo());
        ArrayList<String> prova = new ArrayList<>();
        prova.add("1");
        prova.add("Emporio");
        prova.add("SPORT");
        prova.add("08:00");
        prova.add("18:00");
        prova.add("Via Culmone");
        prova.add("36985214");
        prova.add("[]");
        assertEquals(gestoreNegozi.getItem(1).getDettagli(), prova);
        assertEquals("Emporio", gestoreNegozi.getItem(1).getNome());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreNegozi.getItem(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getItems() throws SQLException {
        ArrayList<Negozio> test1 = gestoreNegozi.getItems();
        assertEquals("Emporio", test1.get(0).getNome());
        assertEquals("Burrobirra", test1.get(1).getNome());
        assertEquals("Ollivander", test1.get(2).getNome());
        assertEquals("Via Culmone", test1.get(0).getIndirizzo());
        assertEquals("Via Hagrid", test1.get(1).getIndirizzo());
        assertEquals("Via Vol de Mort", test1.get(2).getIndirizzo());
    }

    @Test
    void inserisciNegozio() throws SQLException {
        ArrayList<String> test = gestoreNegozi.inserisciNegozio("Ciao", CategoriaNegozio.ALIMENTARI, "Via Giuseppe Garibaldi", "338 5263410", "08:00", "19:00");
        int IDNegozio = Integer.parseInt(test.get(0));
        assertTrue(gestoreNegozi.getItems().contains(gestoreNegozi.getItem(IDNegozio)));
        assertEquals("Ciao", gestoreNegozi.getItem(IDNegozio).getNome());
        assertEquals(CategoriaNegozio.ALIMENTARI, gestoreNegozi.getItem(IDNegozio).getCategoria());
        assertEquals("Via Giuseppe Garibaldi", gestoreNegozi.getItem(IDNegozio).getIndirizzo());
        assertEquals("338 5263410", gestoreNegozi.getItem(IDNegozio).getTelefono());
        assertEquals("08:00", gestoreNegozi.getItem(IDNegozio).getOrarioApertura());
        assertEquals("19:00", gestoreNegozi.getItem(IDNegozio).getOrarioChiusura());

        gestoreNegozi.removeNegozio(IDNegozio);
    }

    @Test
    void removeNegozio() throws SQLException {
        ArrayList<String> test = gestoreNegozi.inserisciNegozio("AwLab", CategoriaNegozio.SPORT, "Via Montale", "338 584213", "07:00", "20:00");
        int IDNegozio = Integer.parseInt(test.get(0));
        Negozio negozio = gestoreNegozi.getItem(IDNegozio);
        assertTrue(gestoreNegozi.getItems().contains(negozio));
        gestoreNegozi.removeNegozio(IDNegozio);
        assertFalse(gestoreNegozi.getItems().contains(negozio));
    }

    @Test
    void testGetDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> test1 = gestoreNegozi.getDettagliItems(CategoriaNegozio.ABBIGLIAMENTO);
        assertEquals("Ollivander", test1.get(0).get(1));
        assertEquals("09:00", test1.get(0).get(3));
        assertEquals("17:00", test1.get(0).get(4));
        assertEquals("Via Vol de Mort", test1.get(0).get(5));
        assertEquals("7854169", test1.get(0).get(6));
        assertEquals(1, test1.size());
    }
}