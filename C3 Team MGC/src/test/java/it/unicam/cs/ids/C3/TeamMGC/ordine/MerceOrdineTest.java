package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MerceOrdineTest {
    static MerceOrdine merceOrdineTest;
    static Ordine ordineTest;
    static Negozio negozioTest;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.merci;");
        updateData("alter table merci AUTO_INCREMENT = 1;");
        updateData("delete from sys.ordini;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        negozioTest = new Negozio("Trinkets", "Cleptomania", null, null, "Via delle Trombette", null);
        Cliente cliente = new Cliente("Marco", "Papera");
        ordineTest = new Ordine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozioTest.getIDNegozio());
        merceOrdineTest = new MerceOrdine(10, "test allSet", StatoOrdine.PAGATO, ordineTest.getID());
    }

    @Test
    void getDettagli() throws SQLException {
        MerceOrdine merceTest = new MerceOrdine(26, "Test Dettagli", StatoOrdine.DA_PAGARE, ordineTest.getID());
        ArrayList<String> dettagli = new ArrayList<>();
        dettagli.add(String.valueOf(merceTest.getID()));
        dettagli.add(String.valueOf(ordineTest.getID()));
        dettagli.add("26.0");
        dettagli.add("Test Dettagli");
        dettagli.add("0");
        dettagli.add(StatoOrdine.DA_PAGARE.toString());
        assertEquals(dettagli, merceTest.getDettagli());

        ordineTest.aggiungiMerce(merceTest, 15);
        merceTest.setPrezzo(42.0);
        merceTest.setStato(StatoOrdine.PAGATO);

        dettagli.clear();
        dettagli.add(String.valueOf(merceTest.getID()));
        dettagli.add(String.valueOf(ordineTest.getID()));
        dettagli.add("42.0");
        dettagli.add("Test Dettagli");
        dettagli.add("15");
        dettagli.add(StatoOrdine.PAGATO.toString());
        assertEquals(dettagli, merceTest.getDettagli());
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

    @Test
    void update() {
    }
}