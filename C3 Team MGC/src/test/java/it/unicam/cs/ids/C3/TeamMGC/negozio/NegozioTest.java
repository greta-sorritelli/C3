package it.unicam.cs.ids.C3.TeamMGC.negozio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class NegozioTest {

    @Test
    void getDettagli() throws SQLException {
        Negozio negozio = new Negozio("Negozio di Bici", "Sport", "09:00-16:00", "", "Via dei Test", "12345");
        ArrayList<String> dettagli = negozio.inserisciNuovaMerce(52, "gomma", 10);
        ArrayList<String> test = new ArrayList<>();
        test.add(String.valueOf(negozio.getIDNegozio()));
        test.add("Negozio di Bici");
        test.add("Sport");
        test.add("09:00-16:00");
        test.add("");
        test.add("Via dei Test");
        test.add("12345");
        test.add("[Merce{ID=" + dettagli.get(0) + ", IDNegozio=" + dettagli.get(1) + ", prezzo=52.0, descrizione='gomma', quantita=10}]");
        assertEquals(test, negozio.getDettagli());
        assertThrows(IllegalArgumentException.class, () -> new Negozio(1000));
    }

    @Test
    void getMerce() throws SQLException {
        Negozio negozio = new Negozio(1);
        Merce merce1 = negozio.getMerce(1);
        Merce merce2 = negozio.getMerce(1);
        merce1.setQuantita(500);
        assertEquals(merce1.getQuantita(), 500);
        assertEquals(merce2.getQuantita(), 500);
        assertTrue(negozio.getMerceDisponibile().contains(merce1));
        assertTrue(negozio.getMerceDisponibile().contains(merce2));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> negozio.getMerce(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getMerceDisponibile() throws SQLException {
        Negozio negozio = new Negozio(1);
        ArrayList<Merce> inventario = negozio.getMerceDisponibile();
        assertEquals(1, inventario.get(0).getID());
        assertEquals(1, inventario.get(1).getIDNegozio());
        assertEquals("test Negozio", inventario.get(2).getDescrizione());

        assertEquals(3, inventario.size());
        inventario = negozio.getMerceDisponibile();
        assertEquals(3, inventario.size());
    }

    @Test
    void inserisciNuovaMerce() throws SQLException {
        Negozio negozio = new Negozio(1);
        ArrayList<String> test = negozio.inserisciNuovaMerce(10, "jeans", 5);
        assertTrue(negozio.getMerceDisponibile().contains(negozio.getMerce(Integer.parseInt(test.get(0)))));
        assertEquals(10, negozio.getMerce(Integer.parseInt(test.get(0))).getPrezzo());
        assertEquals("jeans", negozio.getMerce(Integer.parseInt(test.get(0))).getDescrizione());
        assertEquals(5, negozio.getMerce(Integer.parseInt(test.get(0))).getQuantita());
    }

    @BeforeEach
    void preparaDB() throws SQLException {
        updateData("delete from sys.inventario;");
        updateData("alter table inventario AUTO_INCREMENT = 1;");
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('1', '10', 'test Negozio', '10');");
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('1', '5', 'test Negozio', '1');");
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('1', '50', 'test Negozio', '20');");
    }

    @Test
    void removeMerce() throws SQLException {
        Negozio negozio = new Negozio(1);
        Merce toDelete = new Merce(1, 15, "test delete", 10);
        negozio.removeMerce(toDelete.getID());
        assertFalse(negozio.getMerceDisponibile().contains(toDelete));
    }

    @Test
    void selezionaMerce() throws SQLException {
        Negozio negozio = new Negozio("Negozio di Bici", "Sport", "09:00-16:00", "", "Via dei Test", "12345");
        ArrayList<String> dettagli = negozio.inserisciNuovaMerce(52, "gomma", 10);
        assertEquals("52.0", negozio.selezionaMerce(Integer.parseInt(dettagli.get(0))).get(2));
        assertEquals("gomma", negozio.selezionaMerce(Integer.parseInt(dettagli.get(0))).get(3));
        assertEquals("10", negozio.selezionaMerce(Integer.parseInt(dettagli.get(0))).get(4));
    }

    @Test
    void setQuantita() throws SQLException {
        Negozio negozio = new Negozio(1);
        Merce merce = negozio.getMerce(1);
        assertEquals(10, merce.getQuantita());
        merce.setQuantita(100);
        assertEquals(100, merce.getQuantita());
        negozio.setQuantita(merce.getID(), 20);
        assertEquals(20, negozio.getMerce(merce.getID()).getQuantita());
        ResultSet rs = executeQuery("SELECT quantita FROM sys.inventario where ID = 1;");
        if (rs.next())
            assertEquals(20, rs.getInt("quantita"));
    }

    @Test
    void update() throws SQLException {
        Negozio negozio = new Negozio("Negozio di Bici", "Sport", "09:00", "16:00", "Via dei Test", "12345");
        int IDTest = negozio.getIDNegozio();
        assertEquals("Negozio di Bici", negozio.getNome());
        assertEquals("Sport", negozio.getCategoria());
        assertEquals("09:00", negozio.getOrarioApertura());
        assertEquals("16:00", negozio.getOrarioChiusura());
        assertEquals("Via dei Test", negozio.getIndirizzo());
        assertEquals("12345", negozio.getTelefono());

        updateData("UPDATE sys.negozi SET nome = 'Negozio di scarpe' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET categoria = 'Scarpe' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET orarioApertura = '08:00' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET orarioChiusura = '15:00' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET indirizzo = 'Via degli assert' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.negozi SET telefono = '338599' WHERE (ID = '" + IDTest + "');");

        negozio.update();
        assertEquals("Negozio di scarpe", negozio.getNome());
        assertEquals("Scarpe", negozio.getCategoria());
        assertEquals("08:00", negozio.getOrarioApertura());
        assertEquals("15:00", negozio.getOrarioChiusura());
        assertEquals("Via degli assert", negozio.getIndirizzo());
        assertEquals("338599", negozio.getTelefono());


    }
}