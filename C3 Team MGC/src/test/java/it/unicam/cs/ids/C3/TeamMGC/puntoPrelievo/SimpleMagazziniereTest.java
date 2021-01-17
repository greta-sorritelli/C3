package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleMagazziniereTest {

    static SimpleMagazziniere mag;
    static PuntoPrelievo p;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.magazzinieri;");
        updateData("delete from sys.punti_prelievo;");
        updateData("alter table magazzinieri AUTO_INCREMENT = 1;");
        p = new SimplePuntoPrelievo("via Verdi","B1");
        mag = new SimpleMagazziniere(p.getID(),"Mario", "Rossi");
    }

    @Test
    void creazioneMagazziniere() throws SQLException {
        assertEquals(p.getID(), mag.getIDPuntoPrelievo());
        assertEquals(1, mag.getID());
        assertEquals("Mario", mag.getNome());
        assertEquals("Rossi", mag.getCognome());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new SimpleMagazziniere(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void importDB() throws SQLException {
        SimpleMagazziniere mag = new SimpleMagazziniere(1);
        assertEquals(1, mag.getID());
        assertEquals(p.getID(), mag.getIDPuntoPrelievo());
        assertEquals("Mario", mag.getNome());
        assertEquals("Rossi", mag.getCognome());



    }

}