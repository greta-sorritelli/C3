package it.unicam.cs.ids.C3.TeamMGC.negozio;

import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;
import it.unicam.cs.ids.C3.TeamMGC.corriere.SimpleCorriere;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class SimpleMerceTest {
    static Merce simpleMerceTest;
    static Negozio negozioTest;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.inventario;");
        updateData("alter table inventario AUTO_INCREMENT = 1;");
        updateData("delete from sys.negozi;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        negozioTest = new SimpleNegozio("Bazar", CategoriaNegozio.MERCERIA, null, null, "via roma", null);
        simpleMerceTest = new SimpleMerce(1, 12, "test allSet", 10);
    }

    @Test
    void creazioneMerce() throws SQLException {
        Negozio negozio = new SimpleNegozio("Negozio di Bici", CategoriaNegozio.SPORT, "09:00", "16:00", "Via dei Test", "12345");
        Merce simpleMerce = new SimpleMerce(negozio.getID(), 60, "sciarpa",3);

        assertEquals(negozio.getID(), simpleMerce.getIDNegozio());
        assertEquals(60, simpleMerce.getPrezzo());
        assertEquals("sciarpa", simpleMerce.getDescrizione());
        assertEquals(3, simpleMerce.getQuantita());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new SimpleMerce(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void delete() throws SQLException {
        Merce toDelete = new SimpleMerce(1, 15, "test delete", 10);
        int tmpID = toDelete.getID();
        toDelete.delete();
        ResultSet rs = executeQuery("SELECT * FROM sys.inventario where ID = '" + tmpID + "';");
        assertFalse(rs.next());
        assertEquals(-1, toDelete.getID());
        assertEquals(-1, toDelete.getIDNegozio());
        assertEquals(-1, toDelete.getPrezzo());
        assertEquals("", toDelete.getDescrizione());
        assertEquals(-1, toDelete.getQuantita());
    }

    @Test
    void compareTo() throws SQLException {
        Merce merce = new SimpleMerce(1, 15, "test", 10);
        Merce merceCopia = new SimpleMerce(merce.getID());
        Merce merce2 = new SimpleMerce(1, 25, "test1", 5);

        assertEquals(0, merce.compareTo(merceCopia));
        assertEquals(-1, merce.compareTo(merce2));
        assertEquals(1, merce2.compareTo(merce));

        assertThrows(NullPointerException.class, () -> merce.compareTo(null));
    }

    @Test
    void getDettagli() throws SQLException {
        Merce simpleMerce = new SimpleMerce(1, 6.5, "test getDettagli", 2);
        int IDTest = simpleMerce.getID();
        ArrayList<String> toControl = new ArrayList<>();
        toControl.add(String.valueOf(IDTest));
        toControl.add("1");
        toControl.add("6.5");
        toControl.add("test getDettagli");
        toControl.add("2");
        assertEquals(toControl, simpleMerce.getDettagli());

        updateData("UPDATE sys.inventario SET prezzo = '25' WHERE (ID = '" + IDTest + "');");
        assertNotEquals(toControl, simpleMerce.getDettagli());
        toControl.clear();
        toControl.add(String.valueOf(IDTest));
        toControl.add("1");
        toControl.add("25.0");
        toControl.add("test getDettagli");
        toControl.add("2");
        assertEquals(toControl, simpleMerce.getDettagli());
    }

    @Test
    void setDescrizione() throws SQLException {
        assertEquals("test allSet", simpleMerceTest.getDescrizione());
        simpleMerceTest.setDescrizione("test setDescrizione");
        assertEquals("test setDescrizione", simpleMerceTest.getDescrizione());

        ResultSet rs = executeQuery("SELECT descrizione FROM sys.inventario where ID = 1;");
        if (rs.next())
            assertEquals("test setDescrizione", rs.getString("descrizione"));
    }

    @Test
    void setPrezzo() throws SQLException {
        assertEquals(12, simpleMerceTest.getPrezzo());
        simpleMerceTest.setPrezzo(15);
        assertEquals(15, simpleMerceTest.getPrezzo());

        ResultSet rs = executeQuery("SELECT prezzo FROM sys.inventario where ID = 1;");
        if (rs.next())
            assertEquals(15, rs.getDouble("prezzo"));
    }

    @Test
    void setQuantita() throws SQLException {
        assertEquals(10, simpleMerceTest.getQuantita());
        simpleMerceTest.setQuantita(100);
        assertEquals(100, simpleMerceTest.getQuantita());

        ResultSet rs = executeQuery("SELECT quantita FROM sys.inventario where ID = 1;");
        if (rs.next())
            assertEquals(100, rs.getInt("quantita"));
    }

    @Test
    void update() throws SQLException {
        Merce simpleMerceTest = new SimpleMerce(1, 6.5, "test update", 2);
        int IDTest = simpleMerceTest.getID();
        assertEquals(6.5, simpleMerceTest.getPrezzo());
        assertEquals("test update", simpleMerceTest.getDescrizione());
        assertEquals(2, simpleMerceTest.getQuantita());

        updateData("UPDATE sys.inventario SET prezzo = '12.0' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.inventario SET descrizione = 'test update 2.0' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.inventario SET quantita = '6' WHERE (ID = '" + IDTest + "');");

        simpleMerceTest.update();
        assertEquals(12.0, simpleMerceTest.getPrezzo());
        assertEquals("test update 2.0", simpleMerceTest.getDescrizione());
        assertEquals(6, simpleMerceTest.getQuantita());
    }

    @Test
    void testEquals_toString_hashCode() throws SQLException {
        Merce simpleMerceTest = new SimpleMerce(1, 6.5, "test", 2);
        Merce simpleMerceTestCopia = new SimpleMerce(simpleMerceTest.getID());
        Merce simpleMerceTest2 = new SimpleMerce(1, 14, "test", 3);
        assertEquals(simpleMerceTest, simpleMerceTestCopia);
        assertNotEquals(simpleMerceTest, simpleMerceTest2);
        assertEquals(simpleMerceTest.hashCode(),simpleMerceTestCopia.hashCode());
        assertEquals("ID=" + simpleMerceTest.getID() + ", IDNegozio=1, prezzo=6.5, descrizione='test', quantita=2",simpleMerceTest.toString());
        assertEquals("ID=" + simpleMerceTest2.getID() + ", IDNegozio=1, prezzo=14.0, descrizione='test', quantita=3",simpleMerceTest2.toString());
    }
}