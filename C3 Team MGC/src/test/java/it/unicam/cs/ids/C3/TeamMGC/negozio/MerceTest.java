package it.unicam.cs.ids.C3.TeamMGC.negozio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class MerceTest {
    static Merce merceTest;
    static Negozio negozioTest;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.inventario;");
        updateData("alter table inventario AUTO_INCREMENT = 1;");
        updateData("delete from sys.negozi;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        negozioTest = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        merceTest = new Merce(1, 12, "test allSet", 10);
    }

    @Test
    void delete() throws SQLException {
        Merce toDelete = new Merce(1, 15, "test delete", 10);
        toDelete.delete();
        int tmpID = toDelete.getID();

        ResultSet rs = executeQuery("SELECT quantita FROM sys.inventario where ID = '" + tmpID + "';");
        assertFalse(rs.next());
    }

    @Test
    void getDettagli() throws SQLException {
        Merce merce = new Merce(1, 6.5, "test getDettagli", 2);
        int IDTest = merce.getID();
        ArrayList<String> toControl = new ArrayList<>();
        toControl.add(String.valueOf(IDTest));
        toControl.add("1");
        toControl.add("6.5");
        toControl.add("test getDettagli");
        toControl.add("2");
        assertEquals(toControl, merce.getDettagli());

        updateData("UPDATE sys.inventario SET prezzo = '25' WHERE (ID = '" + IDTest + "');");
        assertNotEquals(toControl, merce.getDettagli());
        toControl.clear();
        toControl.add(String.valueOf(IDTest));
        toControl.add("1");
        toControl.add("25.0");
        toControl.add("test getDettagli");
        toControl.add("2");
        assertEquals(toControl, merce.getDettagli());
    }

    @Test
    void setDescrizione() throws SQLException {
        assertEquals("test allSet", merceTest.getDescrizione());
        merceTest.setDescrizione("test setDescrizione");
        assertEquals("test setDescrizione", merceTest.getDescrizione());

        ResultSet rs = executeQuery("SELECT descrizione FROM sys.inventario where ID = 1;");
        if (rs.next())
            assertEquals("test setDescrizione", rs.getString("descrizione"));

    }

    @Test
    void setPrezzo() throws SQLException {
        assertEquals(12, merceTest.getPrezzo());
        merceTest.setPrezzo(15);
        assertEquals(15, merceTest.getPrezzo());

        ResultSet rs = executeQuery("SELECT prezzo FROM sys.inventario where ID = 1;");
        if (rs.next())
            assertEquals(15, rs.getDouble("prezzo"));
    }

    @Test
    void setQuantita() throws SQLException {
        assertEquals(10, merceTest.getQuantita());
        merceTest.setQuantita(100);
        assertEquals(100, merceTest.getQuantita());

        ResultSet rs = executeQuery("SELECT quantita FROM sys.inventario where ID = 1;");
        if (rs.next())
            assertEquals(100, rs.getInt("quantita"));
    }
}