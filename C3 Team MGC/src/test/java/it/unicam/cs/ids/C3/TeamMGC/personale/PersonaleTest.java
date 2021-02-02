package it.unicam.cs.ids.C3.TeamMGC.personale;

import it.unicam.cs.ids.C3.TeamMGC.negozio.CategoriaNegozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleNegozio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class PersonaleTest {

    private static Negozio negozioTest;

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.personale;");
        updateData("alter table personale AUTO_INCREMENT = 1;");
        negozioTest = new SimpleNegozio("SportLand", CategoriaNegozio.SPORT, "09:00", "18:30", "Via Firenze, 30", "1452763");
    }

    @Test
    void compareTo() throws SQLException {
        Personale commesso = new Commesso(negozioTest.getID(), "Mario", "Rossi");
        Personale commessoCopia = new Commesso(commesso.getID());
        Personale addetto = new AddettoMagazzinoNegozio(negozioTest.getID(), "Giuseppe", "Antonio");

        assertEquals(0, commesso.compareTo(commessoCopia));
        assertEquals(-1, commesso.compareTo(addetto));
        assertEquals(1, addetto.compareTo(commesso));

        assertThrows(NullPointerException.class, () -> commesso.compareTo(null));
    }

    @Test
    void creazionePersonale() throws SQLException {
        Personale commesso = new Commesso(negozioTest.getID(), "Mario", "Rossi");
        assertEquals(negozioTest.getID(), commesso.getIDNegozio());
        assertEquals("Mario", commesso.getNome());
        assertEquals("Rossi", commesso.getCognome());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Commesso(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void setIDNegozio() throws SQLException {
        Negozio negozio = new SimpleNegozio("SportLand", CategoriaNegozio.SPORT, "09:00", "18:30", "Via Firenze, 30", "1452763");
        Personale addetto = new AddettoMagazzinoNegozio(negozioTest.getID(), "Giuseppe", "Antonio");
        assertEquals(negozioTest.getID(), addetto.getIDNegozio());
        addetto.setIDNegozio(negozio.getID());
        assertEquals(negozio.getID(), addetto.getIDNegozio());

        ResultSet rs = executeQuery("SELECT IDNegozio FROM sys.personale where ID = " + addetto.getID() + ";");
        if (rs.next())
            assertEquals(negozio.getID(), rs.getInt("IDNegozio"));
    }

    @Test
    void update() throws SQLException {
        Personale commerciante = new Commerciante(negozioTest.getID(), "Andrea", "Bianchi");
        int IDTest = commerciante.getID();
        assertEquals(commerciante.getNome(), "Andrea");
        assertEquals(commerciante.getCognome(), "Bianchi");

        updateData("UPDATE sys.personale SET nome = 'Giovanni' WHERE (ID = '" + IDTest + "');");
        updateData("UPDATE sys.personale SET cognome = 'Bazzi' WHERE (ID = '" + IDTest + "');");

        commerciante.update();
        assertEquals(commerciante.getNome(), "Giovanni");
        assertEquals(commerciante.getCognome(), "Bazzi");
    }

    @Test
    void testEquals_toString_hashCode() throws SQLException {
        Personale commerciante = new Commerciante(negozioTest.getID(), "Fry", "Philip");
        Personale commercianteCopia = new Commerciante(commerciante.getID());
        Personale commerciante2 = new Commerciante(negozioTest.getID(), "Leela", "Turanga");
        assertEquals(commerciante, commercianteCopia);
        assertNotEquals(commerciante, commerciante2);
        assertEquals(commerciante.hashCode(), commercianteCopia.hashCode());
        assertEquals("ID=" + commerciante.getID() + ", ruolo=COMMERCIANTE, IDNegozio=" + commerciante.getIDNegozio() + ", nome='Fry', cognome='Philip'", commerciante.toString());
        assertEquals("ID=" + commerciante2.getID() + ", ruolo=COMMERCIANTE, IDNegozio=" + commerciante2.getIDNegozio() + ", nome='Leela', cognome='Turanga'", commerciante2.toString());
    }
}