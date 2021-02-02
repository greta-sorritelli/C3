package it.unicam.cs.ids.C3.TeamMGC.personale;

import it.unicam.cs.ids.C3.TeamMGC.negozio.CategoriaNegozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleNegozio;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestorePersonaleTest {

    static GestorePersonale gestorePersonale;
    static Negozio negozio;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.personale;");
        updateData("delete from sys.negozi");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        updateData("alter table personale AUTO_INCREMENT = 1;");
        negozio = new SimpleNegozio("Bazar", CategoriaNegozio.MERCERIA, "09:00", "18:30", "Via Roma, 30", "123-123-123");
        gestorePersonale = new GestorePersonale(negozio.getID());
        gestorePersonale.reset();
        updateData("INSERT INTO sys.personale (IDNegozio, ruolo, nome, cognome, password) VALUES (" + negozio.getID() + ", 'COMMESSO', 'Andrea','Rossi','12345678');");
        updateData("INSERT INTO sys.personale (IDNegozio, ruolo, nome, cognome, password) VALUES (" + negozio.getID() + ", 'ADDETTO_MAGAZZINO', 'Stefano','Verdi','12345678');");
        updateData("INSERT INTO sys.personale (IDNegozio, ruolo, nome, cognome, password) VALUES (" + negozio.getID() + ", 'COMMERCIANTE', 'Riccardo','Benno','12345678');");
    }

    @Test
    void getAddetti() throws SQLException {
        Personale addetto = gestorePersonale.getItem(2);
        assertTrue(gestorePersonale.getAddetti().contains(addetto));
        assertEquals(1, gestorePersonale.getAddetti().size());
        assertEquals(2, gestorePersonale.getAddetti().get(0).getID());
        assertEquals(Ruolo.ADDETTO_MAGAZZINO, gestorePersonale.getAddetti().get(0).getRuolo());
        assertEquals(negozio.getID(), gestorePersonale.getAddetti().get(0).getIDNegozio());
        assertEquals("Stefano", gestorePersonale.getAddetti().get(0).getNome());
        assertEquals("Verdi", gestorePersonale.getAddetti().get(0).getCognome());
    }

    @Test
    void getCommerciante() throws SQLException {
        Personale commerciante = gestorePersonale.getItem(3);
        assertEquals(commerciante, gestorePersonale.getCommerciante());
        assertEquals(3, gestorePersonale.getCommerciante().getID());
        assertEquals(Ruolo.COMMERCIANTE, gestorePersonale.getCommerciante().getRuolo());
        assertEquals(negozio.getID(), gestorePersonale.getCommerciante().getIDNegozio());
        assertEquals("Riccardo", gestorePersonale.getCommerciante().getNome());
        assertEquals("Benno", gestorePersonale.getCommerciante().getCognome());
    }

    @Test
    void getCommessi() throws SQLException {
        Personale commesso = gestorePersonale.getItem(1);
        assertTrue(gestorePersonale.getCommessi().contains(commesso));
        assertEquals(1, gestorePersonale.getCommessi().size());
        assertEquals(1, gestorePersonale.getCommessi().get(0).getID());
        assertEquals(Ruolo.COMMESSO, gestorePersonale.getCommessi().get(0).getRuolo());
        assertEquals(negozio.getID(), gestorePersonale.getCommessi().get(0).getIDNegozio());
        assertEquals("Andrea", gestorePersonale.getCommessi().get(0).getNome());
        assertEquals("Rossi", gestorePersonale.getCommessi().get(0).getCognome());
    }

    @Test
    void getDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> test = gestorePersonale.getDettagliItems();
        assertEquals("1", test.get(0).get(0));
        assertEquals("COMMESSO", test.get(0).get(1));
        assertEquals(negozio.getID(), Integer.parseInt(test.get(0).get(2)));
        assertEquals("Andrea", test.get(0).get(3));
        assertEquals("Rossi", test.get(0).get(4));
        assertEquals("2", test.get(1).get(0));
        assertEquals("ADDETTO_MAGAZZINO", test.get(1).get(1));
        assertEquals(negozio.getID(), Integer.parseInt(test.get(1).get(2)));
        assertEquals("Stefano", test.get(1).get(3));
        assertEquals("Verdi", test.get(1).get(4));
        assertEquals("3", test.get(2).get(0));
        assertEquals("COMMERCIANTE", test.get(2).get(1));
        assertEquals(negozio.getID(), Integer.parseInt(test.get(2).get(2)));
        assertEquals("Riccardo", test.get(2).get(3));
        assertEquals("Benno", test.get(2).get(4));
    }

    @Test
    void getItem() throws SQLException {
        assertEquals("Andrea", gestorePersonale.getItem(1).getNome());
        assertEquals("Stefano", gestorePersonale.getItem(2).getNome());
        assertEquals("Riccardo", gestorePersonale.getItem(3).getNome());
        assertEquals("Rossi", gestorePersonale.getItem(1).getCognome());
        assertEquals("Verdi", gestorePersonale.getItem(2).getCognome());
        assertEquals("Benno", gestorePersonale.getItem(3).getCognome());
        assertEquals(Ruolo.COMMESSO, gestorePersonale.getItem(1).getRuolo());
        assertEquals(Ruolo.ADDETTO_MAGAZZINO, gestorePersonale.getItem(2).getRuolo());
        assertEquals(Ruolo.COMMERCIANTE, gestorePersonale.getItem(3).getRuolo());

        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestorePersonale.getItem(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getItems() throws SQLException {
        ArrayList<Personale> test = gestorePersonale.getItems();
        assertEquals(1, test.get(0).getID());
        assertEquals(Ruolo.COMMESSO, test.get(0).getRuolo());
        assertEquals(negozio.getID(), test.get(0).getIDNegozio());
        assertEquals("Andrea", test.get(0).getNome());
        assertEquals("Rossi", test.get(0).getCognome());
        assertEquals(2, test.get(1).getID());
        assertEquals(Ruolo.ADDETTO_MAGAZZINO, test.get(1).getRuolo());
        assertEquals(negozio.getID(), test.get(1).getIDNegozio());
        assertEquals("Stefano", test.get(1).getNome());
        assertEquals("Verdi", test.get(1).getCognome());
        assertEquals(3, test.get(2).getID());
        assertEquals(Ruolo.COMMERCIANTE, test.get(2).getRuolo());
        assertEquals(negozio.getID(), test.get(2).getIDNegozio());
        assertEquals("Riccardo", test.get(2).getNome());
        assertEquals("Benno", test.get(2).getCognome());
    }

    @Test
    void inserisciAddetto() throws SQLException {
        ArrayList<String> addetto = gestorePersonale.inserisciAddetto("Clarissa", "Albanese", "12345678");
        int IDAddetto = Integer.parseInt(addetto.get(0));
        assertTrue(gestorePersonale.getDettagliItems().contains(addetto));
        assertEquals("Clarissa", gestorePersonale.getItem(IDAddetto).getNome());
        assertEquals("Albanese", gestorePersonale.getItem(IDAddetto).getCognome());
    }

    @Test
    void inserisciCommerciante() throws SQLException {
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestorePersonale.inserisciCommerciante("Clarissa", "Albanese", "12345678"));
        assertEquals("Commerciante già presente.", e1.getMessage());

        Negozio negozio = new SimpleNegozio("SportLand", CategoriaNegozio.SPORT, "09:00", "18:30", "Via Firenze, 30", "1452763");
        GestorePersonale g = new GestorePersonale(negozio.getID());

        ArrayList<String> commerciante = g.inserisciCommerciante("Clarissa", "Albanese", "12345678");
        int IDCommerciante = Integer.parseInt(commerciante.get(0));
        assertTrue(g.getDettagliItems().contains(commerciante));
        assertEquals("Clarissa", g.getItem(IDCommerciante).getNome());
        assertEquals("Albanese", g.getItem(IDCommerciante).getCognome());
    }

    @Test
    void inserisciCommesso() throws SQLException {
        Negozio negozio = new SimpleNegozio("King", CategoriaNegozio.SPORT, "09:00", "18:30", "Via Ciao, 30", "1478526");
        GestorePersonale g = new GestorePersonale(negozio.getID());

        ArrayList<String> commesso = g.inserisciCommesso("Clarissa", "Albanese", "12345678");
        int IDCommesso = Integer.parseInt(commesso.get(0));
        assertTrue(g.getDettagliItems().contains(commesso));
        assertEquals("Clarissa", g.getItem(IDCommesso).getNome());
        assertEquals("Albanese", g.getItem(IDCommesso).getCognome());
    }
}