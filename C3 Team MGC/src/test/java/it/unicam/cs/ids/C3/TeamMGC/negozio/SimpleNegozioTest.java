package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;
import it.unicam.cs.ids.C3.TeamMGC.corriere.SimpleCorriere;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SimpleNegozioTest {

    static Negozio negozioTest;

    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("delete from sys.negozi;");
        updateData("delete from sys.inventario;");
        updateData("alter table inventario AUTO_INCREMENT = 1;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        negozioTest = new SimpleNegozio("Negozio di Bici", CategoriaNegozio.SPORT, "09:00", "16:00", "Via dei Test", "12345");
        negozioTest.inserisciNuovaMerce(10, "test Negozio", 10);
        negozioTest.inserisciNuovaMerce(5, "test Negozio", 1);
        negozioTest.inserisciNuovaMerce(50, "test Negozio", 20);
        negozioTest.lanciaPromozione(1, 5, "Promozione");
    }

    @AfterAll
    static void setCategoria() throws SQLException {
        negozioTest.setCategoria(CategoriaNegozio.CASA);
        assertEquals(CategoriaNegozio.CASA, negozioTest.getCategoria());

        ResultSet rs = executeQuery("SELECT categoria FROM sys.negozi where ID = 1;");
        if (rs.next())
            assertEquals(CategoriaNegozio.CASA, CategoriaNegozio.valueOf(rs.getString("categoria")));
    }

    @Test
    void delete() throws SQLException {
        Negozio toDelete = new SimpleNegozio("BurroBirra", CategoriaNegozio.ALIMENTARI, "08:00", "20:00", "Via Carducci", "321 5236987");
        int tmpID = toDelete.getID();
        toDelete.delete();
        ResultSet rs = executeQuery("SELECT * FROM sys.negozi where ID = '" + tmpID + "';");
        assertFalse(rs.next());
        assertEquals(-1, toDelete.getID());
        assertEquals("", toDelete.getNome());
        assertNull(toDelete.getCategoria());
        assertEquals("", toDelete.getTelefono());
        assertEquals("", toDelete.getIndirizzo());
        assertEquals("", toDelete.getOrarioApertura());
        assertEquals("", toDelete.getOrarioChiusura());
    }

    @Test
    @Order(2)
    void getDettagliPromozioni() throws SQLException {
        assertEquals(1, negozioTest.getDettagliPromozioni().size());
        assertEquals("1", negozioTest.getDettagliPromozioni().get(0).get(0));
        assertEquals("Promozione", negozioTest.getDettagliPromozioni().get(0).get(1));
        assertEquals("5.0", negozioTest.getDettagliPromozioni().get(0).get(2));
        assertEquals("10.0", negozioTest.getDettagliPromozioni().get(0).get(3));
    }

    @Test
    @Order(1)
    void getPromozione() throws SQLException {
        assertEquals("Promozione", negozioTest.getPromozione(1).get(1));
        assertEquals(5, Double.parseDouble(negozioTest.getPromozione(1).get(2)));
        assertEquals(10, Double.parseDouble(negozioTest.getPromozione(1).get(3)));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> negozioTest.getPromozione(14526));
        assertEquals("IDMerce non valido.", e1.getMessage());
    }

    @Test
    void testToString() throws SQLException {
        Negozio negozio = new SimpleNegozio("KingSport", CategoriaNegozio.SPORT, "08:00", "20:00", "Via Carducci,16", "321 5412896");
        assertEquals("ID=" + negozio.getID() + ", nome='KingSport', categoria=SPORT, indirizzo='Via Carducci,16'", negozio.toString());
    }

    @Test
    void eliminaPromozione() throws SQLException {
        assertEquals(5, negozioTest.getItem(1).getPrezzo());
        negozioTest.eliminaPromozione(1);
        assertEquals(10, negozioTest.getItem(1).getPrezzo());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> negozioTest.eliminaPromozione(14526));
        assertEquals("IDMerce non valido.", e1.getMessage());
    }

    @Test
    void getDettagli() throws SQLException {
        Negozio negozio = new SimpleNegozio("Negozio1", CategoriaNegozio.SPORT, "09:00", "16:00", "Via degli Assert", "123456");
        ArrayList<String> dettagli = negozio.inserisciNuovaMerce(52, "gomma", 10);
        ArrayList<String> test = new ArrayList<>();
        test.add(String.valueOf(negozio.getID()));
        test.add("Negozio1");
        test.add("SPORT");
        test.add("09:00");
        test.add("16:00");
        test.add("Via degli Assert");
        test.add("123456");
        test.add("[ID=" + dettagli.get(0) + ", IDNegozio=" + dettagli.get(1) + ", prezzo=52.0, descrizione='gomma', quantita=10]");
        assertEquals(test, negozio.getDettagli());
        assertThrows(IllegalArgumentException.class, () -> new SimpleNegozio(1000));
    }

    @Test
    @Order(0)
    void getDettagliMerce() throws SQLException {
        ArrayList<ArrayList<String>> merci = negozioTest.getDettagliItems();
        assertEquals(negozioTest.getID(), Integer.parseInt(merci.get(0).get(1)));
        assertEquals("5.0", merci.get(0).get(2));
        assertEquals("test Negozio", merci.get(0).get(3));
        assertEquals("10", merci.get(0).get(4));
        assertEquals(negozioTest.getID(), Integer.parseInt(merci.get(1).get(1)));
        assertEquals("5.0", merci.get(1).get(2));
        assertEquals("test Negozio", merci.get(1).get(3));
        assertEquals("1", merci.get(1).get(4));
        assertEquals(negozioTest.getID(), Integer.parseInt(merci.get(2).get(1)));
        assertEquals("50.0", merci.get(2).get(2));
        assertEquals("test Negozio", merci.get(2).get(3));
        assertEquals("20", merci.get(2).get(4));
    }

    @Test
    void getMerce() throws SQLException {
        Merce merce1 = negozioTest.getItem(1);
        Merce merce2 = negozioTest.getItem(2);
        merce1.setQuantita(500);
        merce2.setQuantita(600);
        assertEquals(merce1.getQuantita(), 500);
        assertEquals(merce2.getQuantita(), 600);
        assertTrue(negozioTest.getItems().contains(merce1));
        assertTrue(negozioTest.getItems().contains(merce2));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> negozioTest.getItem(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getMerceDisponibile() throws SQLException {
        Negozio negozio = new SimpleNegozio("Negozio2", CategoriaNegozio.ABBIGLIAMENTO, "09:00", "16:00", "Via degli Assert,56", "5644");
        ArrayList<String> merce = negozio.inserisciNuovaMerce(15, "test Negozio", 10);
        ArrayList<Merce> inventario = negozio.getItems();
        assertEquals(Integer.parseInt(merce.get(0)), inventario.get(0).getID());
        assertEquals(negozio.getID(), inventario.get(0).getIDNegozio());
        assertEquals("test Negozio", inventario.get(0).getDescrizione());

        assertEquals(1, inventario.size());
        inventario = negozio.getItems();
        assertEquals(1, inventario.size());
    }

    @Test
    void getMerceVenduta() throws SQLException {
        Negozio negozio = new SimpleNegozio("Da Borsini", CategoriaNegozio.GIARDINAGGIO, "09:00", "16:00", "Via delle Probabilita, 100", "5644");
        Negozio negozio1 = new SimpleNegozio("Sportland", CategoriaNegozio.SPORT, "09:00", "16:00", "Via delle Trombette, 10", "523698");

        GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();

        Merce simpleMerce = new SimpleMerce(negozio.getID(), 14, "Cuscino", 10);
        Merce simpleMerce1 = new SimpleMerce(negozio.getID(), 18, "Big Bubble", 5);

        Cliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio);
        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio1);

        gestoreOrdini.registraMerce(simpleMerce.getID(), 5, Integer.parseInt(ordine.get(0)), negozio);
        gestoreOrdini.registraMerce(simpleMerce1.getID(), 1, Integer.parseInt(ordine.get(0)), negozio);
        gestoreOrdini.terminaOrdine(Integer.parseInt(ordine.get(0)));

        assertEquals(40, negozio.getMerceVenduta());
        assertEquals(0, negozio1.getMerceVenduta());

        Merce simpleMerce2 = new SimpleMerce(negozio1.getID(), 10, "Lenzuolo", 20);
        assertEquals(0, negozio1.getMerceVenduta());
        assertFalse(negozio1.getInventario().isEmpty());

        gestoreOrdini.registraMerce(simpleMerce2.getID(), 10, Integer.parseInt(ordine1.get(0)), negozio1);
        gestoreOrdini.terminaOrdine(Integer.parseInt(ordine1.get(0)));
        negozio1.removeMerce(simpleMerce2.getID());

        assertEquals(100, negozio1.getMerceVenduta());

    }

    @Test
    void getPrezzoMedio() throws SQLException {
        Negozio n = new SimpleNegozio("Negozio2", CategoriaNegozio.ABBIGLIAMENTO, "09:00", "16:00", "Via dei Test", "12345");
        n.inserisciNuovaMerce(10, "test Negozio", 10);
        n.inserisciNuovaMerce(5, "test Negozio", 1);
        assertEquals(7.5, n.getPrezzoMedio());
    }

    @Test
    void inserisciNuovaMerce() throws SQLException {
        ArrayList<String> test = negozioTest.inserisciNuovaMerce(10, "jeans", 5);
        assertTrue(negozioTest.getItems().contains(negozioTest.getItem(Integer.parseInt(test.get(0)))));
        assertEquals(10, negozioTest.getItem(Integer.parseInt(test.get(0))).getPrezzo());
        assertEquals("jeans", negozioTest.getItem(Integer.parseInt(test.get(0))).getDescrizione());
        assertEquals(5, negozioTest.getItem(Integer.parseInt(test.get(0))).getQuantita());
    }

    @Test
    void lanciaPromozione() throws SQLException {
        Negozio negozio = new SimpleNegozio("BurroBirra", CategoriaNegozio.ALIMENTARI, "08:00", "20:00", "Via Carducci", "321 5236987");
        ArrayList<String> dettagli = negozio.inserisciNuovaMerce(52, "gomma", 10);
        negozio.lanciaPromozione(Integer.parseInt(dettagli.get(0)), 40, "Promozione gomma");
        assertEquals(40, negozio.getItem(Integer.parseInt(dettagli.get(0))).getPrezzo());

        ResultSet rs = executeQuery("SELECT prezzoAttuale FROM sys.promozioni where IDNegozio = " + negozio.getID() + ";");
        if (rs.next())
            assertEquals(40, rs.getDouble("prezzoAttuale"));
    }

    @Test
    void removeMerce() throws SQLException {
        Merce simpleMerce = new SimpleMerce(negozioTest.getID(), 15, "test delete", 10);
        assertTrue(negozioTest.getItems().contains(negozioTest.getItem(simpleMerce.getID())));
        negozioTest.removeMerce(simpleMerce.getID());
        assertFalse(negozioTest.getItems().contains(simpleMerce));
    }

    @Test
    @Order(3)
    void setNuoviDatiPromozione() throws SQLException {
        negozioTest.setNuoviDatiPromozione(1, 2, "Promozione2");
        assertEquals("Promozione2", negozioTest.getPromozione(1).get(1));
        assertEquals(2, Double.parseDouble(negozioTest.getPromozione(1).get(2)));
        assertEquals(10, Double.parseDouble(negozioTest.getPromozione(1).get(3)));

        ResultSet rs = executeQuery("SELECT messaggio FROM sys.promozioni where IDNegozio = " + negozioTest.getID() + ";");
        if (rs.next())
            assertEquals("Promozione2", rs.getString("messaggio"));
    }

    @Test
    void setQuantita() throws SQLException {
        Merce simpleMerce = negozioTest.getItem(1);
        assertEquals(10, simpleMerce.getQuantita());
        simpleMerce.setQuantita(100);
        assertEquals(100, simpleMerce.getQuantita());
        negozioTest.setQuantitaMerce(simpleMerce.getID(), 20);
        assertEquals(20, negozioTest.getItem(simpleMerce.getID()).getQuantita());

        ResultSet rs = executeQuery("SELECT quantita FROM sys.inventario where ID = 1;");
        if (rs.next())
            assertEquals(20, rs.getInt("quantita"));
    }

    @Test
    void update() throws SQLException {
        int IDTest = negozioTest.getID();
        assertEquals("Negozio di Bici", negozioTest.getNome());
        assertEquals(CategoriaNegozio.SPORT, negozioTest.getCategoria());
        assertEquals("09:00", negozioTest.getOrarioApertura());
        assertEquals("16:00", negozioTest.getOrarioChiusura());
        assertEquals("Via dei Test", negozioTest.getIndirizzo());
        assertEquals("12345", negozioTest.getTelefono());

        updateData("UPDATE sys.negozi SET nome = 'Negozio di scarpe' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET categoria = 'ABBIGLIAMENTO' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET orarioApertura = '08:00' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET orarioChiusura = '15:00' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET indirizzo = 'Via degli assert' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET telefono = '338599' WHERE (ID = '" + IDTest + "');");

        negozioTest.update();
        assertEquals("Negozio di scarpe", negozioTest.getNome());
        assertEquals(CategoriaNegozio.ABBIGLIAMENTO, negozioTest.getCategoria());
        assertEquals("08:00", negozioTest.getOrarioApertura());
        assertEquals("15:00", negozioTest.getOrarioChiusura());
        assertEquals("Via degli assert", negozioTest.getIndirizzo());
        assertEquals("338599", negozioTest.getTelefono());
    }
}