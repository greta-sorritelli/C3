package it.unicam.cs.ids.C3.TeamMGC.negozio;

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

    @Test
    @Order(1)
    void getDettagliMerce() throws SQLException {
        ArrayList<ArrayList<String>> merci = negozioTest.getDettagliItems();
        assertEquals(negozioTest.getID(),Integer.parseInt(merci.get(0).get(1)));
        assertEquals("10.0",merci.get(0).get(2));
        assertEquals("test Negozio",merci.get(0).get(3));
        assertEquals("10",merci.get(0).get(4));
        assertEquals(negozioTest.getID(),Integer.parseInt(merci.get(1).get(1)));
        assertEquals("5.0",merci.get(1).get(2));
        assertEquals("test Negozio",merci.get(1).get(3));
        assertEquals("1",merci.get(1).get(4));
        assertEquals(negozioTest.getID(),Integer.parseInt(merci.get(2).get(1)));
        assertEquals("50.0",merci.get(2).get(2));
        assertEquals("test Negozio",merci.get(2).get(3));
        assertEquals("20",merci.get(2).get(4));
    }

    @Test
    void getDettagli() throws SQLException {
        Negozio negozio = new SimpleNegozio("Negozio1", "Sport", "09:00", "16:00", "Via degli Assert", "123456");
        ArrayList<String> dettagli = negozio.inserisciNuovaMerce(52, "gomma", 10);
        ArrayList<String> test = new ArrayList<>();
        test.add(String.valueOf(negozio.getID()));
        test.add("Negozio1");
        test.add("Sport");
        test.add("09:00");
        test.add("16:00");
        test.add("Via degli Assert");
        test.add("123456");
        test.add("[ID=" + dettagli.get(0) + ", IDNegozio=" + dettagli.get(1) + ", prezzo=52.0, descrizione='gomma', quantita=10]");
        assertEquals(test, negozio.getDettagli());
        assertThrows(IllegalArgumentException.class, () -> new SimpleNegozio(1000));
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
        ArrayList<Merce> inventario = negozioTest.getItems();
        assertEquals(1, inventario.get(0).getID());
        assertEquals(1, inventario.get(1).getIDNegozio());
        assertEquals("test Negozio", inventario.get(2).getDescrizione());

        assertEquals(5, inventario.size());
        inventario = negozioTest.getItems();
        assertEquals(5, inventario.size());
    }

    @Test
    void inserisciNuovaMerce() throws SQLException {
        ArrayList<String> test = negozioTest.inserisciNuovaMerce(10, "jeans", 5);
        assertTrue(negozioTest.getItems().contains(negozioTest.getItem(Integer.parseInt(test.get(0)))));
        assertEquals(10, negozioTest.getItem(Integer.parseInt(test.get(0))).getPrezzo());
        assertEquals("jeans", negozioTest.getItem(Integer.parseInt(test.get(0))).getDescrizione());
        assertEquals(5, negozioTest.getItem(Integer.parseInt(test.get(0))).getQuantita());
    }

   @BeforeAll
     static void preparaDB() throws SQLException {
        updateData("delete from sys.negozi;");
        updateData("delete from sys.inventario;");
        updateData("alter table inventario AUTO_INCREMENT = 1;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        negozioTest = new SimpleNegozio("Negozio di Bici", "Sport", "09:00", "16:00", "Via dei Test", "12345");
        negozioTest.inserisciNuovaMerce(10,"test Negozio",10);
        negozioTest.inserisciNuovaMerce(5,"test Negozio",1);
        negozioTest.inserisciNuovaMerce(50,"test Negozio",20);
    }

    @Test
    void removeMerce() throws SQLException {
        Merce simpleMerce = new SimpleMerce(negozioTest.getID(),15,"test delete",10);
        assertTrue(negozioTest.getItems().contains(negozioTest.getItem(simpleMerce.getID())));
        negozioTest.removeMerce(simpleMerce.getID());
        assertFalse(negozioTest.getItems().contains(simpleMerce));
    }

    @Test
    void selezionaMerce() throws SQLException {
        ArrayList<String> dettagli = negozioTest.inserisciNuovaMerce(52, "gomma", 10);
        assertEquals("52.0", negozioTest.selezionaMerce(Integer.parseInt(dettagli.get(0))).get(2));
        assertEquals("gomma", negozioTest.selezionaMerce(Integer.parseInt(dettagli.get(0))).get(3));
        assertEquals("10", negozioTest.selezionaMerce(Integer.parseInt(dettagli.get(0))).get(4));
    }

    @Test
    void setQuantita() throws SQLException {
        Merce simpleMerce = negozioTest.getItem(1);
        assertEquals(10, simpleMerce.getQuantita());
        simpleMerce.setQuantita(100);
        assertEquals(100, simpleMerce.getQuantita());
        negozioTest.setQuantita(simpleMerce.getID(), 20);
        assertEquals(20, negozioTest.getItem(simpleMerce.getID()).getQuantita());
        ResultSet rs = executeQuery("SELECT quantita FROM sys.inventario where ID = 1;");
        if (rs.next())
            assertEquals(20, rs.getInt("quantita"));
    }

    @Test
    void update() throws SQLException {
        int IDTest = negozioTest.getID();
        assertEquals("Negozio di Bici", negozioTest.getNome());
        assertEquals("Sport", negozioTest.getCategoria());
        assertEquals("09:00", negozioTest.getOrarioApertura());
        assertEquals("16:00", negozioTest.getOrarioChiusura());
        assertEquals("Via dei Test", negozioTest.getIndirizzo());
        assertEquals("12345", negozioTest.getTelefono());

        updateData("UPDATE sys.negozi SET nome = 'Negozio di scarpe' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET categoria = 'Scarpe' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET orarioApertura = '08:00' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET orarioChiusura = '15:00' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET indirizzo = 'Via degli assert' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET telefono = '338599' WHERE (ID = '" + IDTest + "');");

        negozioTest.update();
        assertEquals("Negozio di scarpe", negozioTest.getNome());
        assertEquals("Scarpe", negozioTest.getCategoria());
        assertEquals("08:00", negozioTest.getOrarioApertura());
        assertEquals("15:00", negozioTest.getOrarioChiusura());
        assertEquals("Via degli assert", negozioTest.getIndirizzo());
        assertEquals("338599", negozioTest.getTelefono());


    }
}