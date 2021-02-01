package it.unicam.cs.ids.C3.TeamMGC.personale;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.negozio.CategoriaNegozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleNegozio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestorePersonaleTest {

    static GestorePersonale gestorePersonale;
    static Negozio negozio;

    @BeforeAll
    static void clearDB() throws SQLException {
        negozio = new SimpleNegozio("Bazar", CategoriaNegozio.MERCERIA, "09:00", "18:30", "Via Roma, 30", "123-123-123");
        gestorePersonale = new GestorePersonale(negozio.getID());
        gestorePersonale.reset();
        updateData("delete from sys.personale;");
        updateData("alter table personale AUTO_INCREMENT = 1;");
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
}