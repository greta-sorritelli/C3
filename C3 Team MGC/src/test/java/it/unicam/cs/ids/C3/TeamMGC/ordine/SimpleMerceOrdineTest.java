package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleMerceOrdineTest {
    static SimpleMerceOrdine simpleMerceOrdineTest;
    static SimpleOrdine simpleOrdineTest;
    static Negozio negozioTest;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.merci;");
        updateData("alter table merci AUTO_INCREMENT = 1;");
        updateData("delete from sys.ordini;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        negozioTest = new Negozio("Trinkets", "Cleptomania", null, null, "Via delle Trombette", null);
        Cliente simpleCliente = new SimpleCliente("Marco", "Papera");
        simpleOrdineTest = new SimpleOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozioTest.getID());
        simpleMerceOrdineTest = new SimpleMerceOrdine(10, "test allSet", StatoOrdine.PAGATO, simpleOrdineTest.getID());
    }

    @Test
    void getDettagli() throws SQLException {
        SimpleMerceOrdine merceTest = new SimpleMerceOrdine(26, "Test Dettagli", StatoOrdine.DA_PAGARE, simpleOrdineTest.getID());
        ArrayList<String> dettagli = new ArrayList<>();
        dettagli.add(String.valueOf(merceTest.getID()));
        dettagli.add(String.valueOf(simpleOrdineTest.getID()));
        dettagli.add("26.0");
        dettagli.add("Test Dettagli");
        dettagli.add("0");
        dettagli.add(StatoOrdine.DA_PAGARE.toString());
        assertEquals(dettagli, merceTest.getDettagli());

        simpleOrdineTest.aggiungiMerce(merceTest, 15);
        merceTest.setPrezzo(42.0);
        merceTest.setStato(StatoOrdine.PAGATO);

        dettagli.clear();
        dettagli.add(String.valueOf(merceTest.getID()));
        dettagli.add(String.valueOf(simpleOrdineTest.getID()));
        dettagli.add("42.0");
        dettagli.add("Test Dettagli");
        dettagli.add("15");
        dettagli.add(StatoOrdine.PAGATO.toString());
        assertEquals(dettagli, merceTest.getDettagli());
    }

    @Test
    void setDescrizione() throws SQLException {
        assertEquals("test allSet", simpleMerceOrdineTest.getDescrizione());
        simpleMerceOrdineTest.setDescrizione("test setDescrizione");
        assertEquals("test setDescrizione", simpleMerceOrdineTest.getDescrizione());

        ResultSet rs = executeQuery("SELECT descrizione FROM sys.merci where ID = 1;");
        if (rs.next())
            assertEquals("test setDescrizione", rs.getString("descrizione"));
    }

    @Test
    void setPrezzo() throws SQLException {
        assertEquals(10, simpleMerceOrdineTest.getPrezzo());
        simpleMerceOrdineTest.setPrezzo(100);
        assertEquals(100, simpleMerceOrdineTest.getPrezzo());

        ResultSet rs = executeQuery("SELECT prezzo FROM sys.merci where ID = 1;");
        if (rs.next())
            assertEquals(100, rs.getDouble("prezzo"));
    }

    @Test
    void setQuantita() throws SQLException {
        assertEquals(0, simpleMerceOrdineTest.getQuantita());
        simpleMerceOrdineTest.setQuantita(10);
        assertEquals(10, simpleMerceOrdineTest.getQuantita());

        ResultSet rs = executeQuery("SELECT quantita FROM sys.merci where ID = 1;");
        if (rs.next())
            assertEquals(10, rs.getInt("quantita"));
    }

    @Test
    void setStato() throws SQLException {
        assertEquals(StatoOrdine.PAGATO, simpleMerceOrdineTest.getStato());
        simpleMerceOrdineTest.setStato(StatoOrdine.AFFIDATO_AL_CORRIERE);
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, simpleMerceOrdineTest.getStato());

        ResultSet rs = executeQuery("SELECT stato FROM sys.merci where ID = 1;");
        if (rs.next())
            assertEquals("AFFIDATO_AL_CORRIERE", rs.getString("stato"));
    }

    @Test
    void update() throws SQLException {
        SimpleMerceOrdine simpleMerceOrdine = new SimpleMerceOrdine(12, "Upload", StatoOrdine.DA_PAGARE, simpleOrdineTest.getID());
        simpleMerceOrdine.setQuantita(1);
        assertEquals(1, simpleMerceOrdine.getQuantita());
        assertEquals(12, simpleMerceOrdine.getPrezzo());
        assertEquals("Upload", simpleMerceOrdine.getDescrizione());
        assertEquals(StatoOrdine.DA_PAGARE, simpleMerceOrdine.getStato());
        updateData("UPDATE sys.merci SET stato = 'PAGATO' where ID = '" + simpleMerceOrdine.getID() + "';");
        updateData("UPDATE sys.merci SET prezzo = '10' where ID = '" + simpleMerceOrdine.getID() + "';");
        updateData("UPDATE sys.merci SET descrizione = 'Mr Robot' where ID = '" + simpleMerceOrdine.getID() + "';");
        updateData("UPDATE sys.merci SET quantita = '2' where ID = '" + simpleMerceOrdine.getID() + "';");
        simpleMerceOrdine.update();
        assertEquals(2, simpleMerceOrdine.getQuantita());
        assertEquals(10, simpleMerceOrdine.getPrezzo());
        assertEquals("Mr Robot", simpleMerceOrdine.getDescrizione());
        assertEquals(StatoOrdine.PAGATO, simpleMerceOrdine.getStato());
    }
}