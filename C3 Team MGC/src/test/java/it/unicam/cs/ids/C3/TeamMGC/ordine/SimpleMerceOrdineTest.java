package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.CategoriaNegozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleNegozio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class SimpleMerceOrdineTest {
    static MerceOrdine simpleMerceOrdineTest;
    static Ordine ordineTest;
    static Negozio negozioTest;
    static Cliente clienteTest;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.merci;");
        updateData("alter table merci AUTO_INCREMENT = 1;");
        updateData("delete from sys.ordini;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        negozioTest = new SimpleNegozio("Trinkets", CategoriaNegozio.ABBIGLIAMENTO, null, null, "Via delle Trombette", null);
        clienteTest = new SimpleCliente("Marco", "Papera");
        ordineTest = new SimpleOrdine(clienteTest.getID(), clienteTest.getNome(), clienteTest.getCognome(), negozioTest.getID());
        simpleMerceOrdineTest = new SimpleMerceOrdine(10, "test allSet", StatoOrdine.PAGATO, ordineTest.getID());
    }

    @Test
    void creazioneMerceOrdine() {
        assertEquals(ordineTest.getID(), simpleMerceOrdineTest.getIDOrdine());
        assertEquals(StatoOrdine.PAGATO, simpleMerceOrdineTest.getStato());
        assertEquals("test allSet", simpleMerceOrdineTest.getDescrizione());
        assertEquals(10, simpleMerceOrdineTest.getPrezzo());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new SimpleMerceOrdine(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getDettagli() throws SQLException {
        MerceOrdine merceTest = new SimpleMerceOrdine(26, "Test Dettagli", StatoOrdine.DA_PAGARE, ordineTest.getID());
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
    void setIDOrdine() throws SQLException {
        Negozio negozio = new SimpleNegozio("SportLand", CategoriaNegozio.SPORT, null, null, "Via delle Trombette,15", null);
        Cliente cliente = new SimpleCliente("Tommaso", "Cane");
        Ordine ordine = new SimpleOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio.getID());
        MerceOrdine merce = new SimpleMerceOrdine(5, "test", StatoOrdine.PAGATO, ordine.getID());

        assertEquals(ordine.getID(), merce.getIDOrdine());
        merce.setIDOrdine(ordineTest.getID());
        assertEquals(ordineTest.getID(), simpleMerceOrdineTest.getIDOrdine());

        ResultSet rs = executeQuery("SELECT IDOrdine FROM sys.merci where ID = " + merce.getID() + ";");
        if (rs.next())
            assertEquals(ordineTest.getID(), rs.getInt("IDOrdine"));
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
    void testEquals_toString_hashCode() throws SQLException {
        MerceOrdine merceOrdine = new SimpleMerceOrdine(800, "Collana di diamanti", StatoOrdine.DA_PAGARE, ordineTest.getID());
        MerceOrdine merceOrdineDiversa = new SimpleMerceOrdine(100, "Collana", StatoOrdine.DA_PAGARE, ordineTest.getID());
        MerceOrdine merceOrdineCopia = new SimpleMerceOrdine(merceOrdine.getID());

        assertEquals(merceOrdine, merceOrdineCopia);
        assertNotEquals(merceOrdine, merceOrdineDiversa);
        assertEquals(merceOrdine.hashCode(), merceOrdineCopia.hashCode());
        assertNotEquals(merceOrdine.hashCode(), merceOrdineDiversa.hashCode());

        assertEquals("ID=" + merceOrdine.getID() + ", IDOrdine=" + ordineTest.getID() + ", prezzo=800.0, descrizione='Collana di diamanti', quantita=0, stato=DA_PAGARE", merceOrdine.toString());
        assertEquals("ID=" + merceOrdine.getID() + ", IDOrdine=" + ordineTest.getID() + ", prezzo=800.0, descrizione='Collana di diamanti', quantita=0, stato=DA_PAGARE", merceOrdineCopia.toString());
    }

    @Test
    void update() throws SQLException {
        MerceOrdine simpleMerceOrdine = new SimpleMerceOrdine(12, "Upload", StatoOrdine.DA_PAGARE, ordineTest.getID());
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