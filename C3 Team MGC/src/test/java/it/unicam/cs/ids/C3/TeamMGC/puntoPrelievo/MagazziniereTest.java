package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MagazziniereTest {

    @BeforeEach
    void clearDB() throws SQLException {
        updateData("delete from sys.magazzinieri;");
        updateData("alter table magazzinieri AUTO_INCREMENT = 1;");
    }

    @Test
    void creazioneMagazziniere() throws SQLException {
        PuntoPrelievo p = new PuntoPrelievo("via Verdi","B1");
        Magazziniere magazziniere = new Magazziniere(p.getID(),"Mario", "Rossi");

        assertEquals(p.getID(), magazziniere.getIDPuntoPrelievo());
        assertEquals("Mario", magazziniere.getNome());
        assertEquals("Rossi", magazziniere.getCognome());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Magazziniere(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }
}