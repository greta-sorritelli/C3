package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleMagazziniereTest {

    @BeforeEach
    void clearDB() throws SQLException {
        updateData("delete from sys.magazzinieri;");
        updateData("alter table magazzinieri AUTO_INCREMENT = 1;");
    }

    @Test
    void creazioneMagazziniere() throws SQLException {
        SimplePuntoPrelievo p = new SimplePuntoPrelievo("via Verdi","B1");
        SimpleMagazziniere simpleMagazziniere = new SimpleMagazziniere(p.getID(),"Mario", "Rossi");

        assertEquals(p.getID(), simpleMagazziniere.getIDPuntoPrelievo());
        assertEquals("Mario", simpleMagazziniere.getNome());
        assertEquals("Rossi", simpleMagazziniere.getCognome());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new SimpleMagazziniere(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }
}