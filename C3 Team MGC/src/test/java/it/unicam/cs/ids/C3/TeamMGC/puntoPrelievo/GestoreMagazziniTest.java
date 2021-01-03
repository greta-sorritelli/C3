package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GestoreMagazziniTest {

    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("delete from sys.punti_prelievo;");
        updateData("alter table punti_prelievo AUTO_INCREMENT = 1;");
        updateData("INSERT INTO `sys`.`punti_prelievo` (`nome`, `indirizzo`) VALUES ('B1', 'via Giacinto');");
        updateData("INSERT INTO `sys`.`punti_prelievo` (`nome`, `indirizzo`) VALUES ('B2', 'via Giuseppe');");
        updateData("INSERT INTO `sys`.`punti_prelievo` (`nome`, `indirizzo`) VALUES ('B3', 'via Paolo');");
    }

    @Test
    void getDettagliMagazziniDisponibili() {
        GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
        ArrayList<ArrayList<String>> test = gestoreMagazzini.getDettagliMagazziniDisponibili();
        assertEquals(test.get(0).get(0),"B1");
        assertEquals(test.get(0).get(1), "via Giacinto");
        assertEquals(test.get(1).get(0),"B2");
        assertEquals(test.get(1).get(1), "via Giuseppe");
        assertEquals(test.get(2).get(0),"B3");
        assertEquals(test.get(2).get(1), "via Paolo");

    }

    @Test
    void getMagazziniDisponibili() {
        GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
        ArrayList<PuntoPrelievo> test = gestoreMagazzini.getMagazziniDisponibili();
        assertEquals(test.get(0).getNome(),"B1");
        assertEquals(test.get(1).getIndirizzo(),"via Giuseppe");
        assertEquals(test.get(2).getNome(),"B3");
    }

    @Test
    void getPuntoPrelievo() {
        GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
        assertEquals("B1", gestoreMagazzini.getPuntoPrelievo(1).getNome());
        assertEquals("via Giuseppe", gestoreMagazzini.getPuntoPrelievo(2).getIndirizzo());
        assertEquals("via Paolo", gestoreMagazzini.getPuntoPrelievo(3).getIndirizzo());
    }

    @Test
    void sceltaPuntoPrelievo() {
        GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
        assertEquals("B1", gestoreMagazzini.sceltaPuntoPrelievo(1).getNome());
        assertEquals("via Giuseppe", gestoreMagazzini.sceltaPuntoPrelievo(2).getIndirizzo());
        assertEquals("via Paolo", gestoreMagazzini.sceltaPuntoPrelievo(3).getIndirizzo());
    }

}