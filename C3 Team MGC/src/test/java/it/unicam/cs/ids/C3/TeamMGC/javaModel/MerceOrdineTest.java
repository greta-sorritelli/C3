package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class MerceOrdineTest {
    static MerceOrdine merceOrdineTest;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("TRUNCATE sys.merci;");
        merceOrdineTest = new MerceOrdine(10, "test allSet", StatoOrdine.PAGATO);
    }

    @Test
    void setDescrizione() throws SQLException {
        assertEquals("test allSet", merceOrdineTest.getDescrizione());
        merceOrdineTest.setDescrizione("test setDescrizione");
        assertEquals("test setDescrizione", merceOrdineTest.getDescrizione());

        ResultSet rs = executeQuery("SELECT descrizione FROM sys.merci where ID = 1;");
        if (rs.next())
            assertEquals("test setDescrizione", rs.getString("descrizione"));
    }

    @Test
    void setIDOrdine() throws SQLException {
        assertEquals(-1, merceOrdineTest.getIDOrdine());
        merceOrdineTest.setIDOrdine(50);
        assertEquals(50, merceOrdineTest.getIDOrdine());

        ResultSet rs = executeQuery("SELECT IDOrdine FROM sys.merci where ID = 1;");
        if (rs.next())
            assertEquals(50, rs.getInt("IDOrdine"));
    }

    @Test
    void setPrezzo() throws SQLException {
        assertEquals(10, merceOrdineTest.getPrezzo());
        merceOrdineTest.setPrezzo(100);
        assertEquals(100, merceOrdineTest.getPrezzo());

        ResultSet rs = executeQuery("SELECT prezzo FROM sys.merci where ID = 1;");
        if (rs.next())
            assertEquals(100, rs.getDouble("prezzo"));
    }

    @Test
    void setQuantita() throws SQLException {
        assertEquals(0, merceOrdineTest.getQuantita());
        merceOrdineTest.setQuantita(10);
        assertEquals(10, merceOrdineTest.getQuantita());

        ResultSet rs = executeQuery("SELECT quantita FROM sys.merci where ID = 1;");
        if (rs.next())
            assertEquals(10, rs.getInt("quantita"));
    }

    @Test
    void setStato() throws SQLException {
        assertEquals(StatoOrdine.PAGATO, merceOrdineTest.getStato());
        merceOrdineTest.setStato(StatoOrdine.AFFIDATO_AL_CORRIERE);
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, merceOrdineTest.getStato());

        ResultSet rs = executeQuery("SELECT stato FROM sys.merci where ID = 1;");
        if (rs.next())
            assertEquals("AFFIDATO_AL_CORRIERE", rs.getString("stato"));
    }
}