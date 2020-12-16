package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestoreMagazziniTest {

    @Test
    void preparaDB() throws SQLException {
        updateData("delete from sys.punti_prelievo;");
        updateData("alter table punti_prelievo AUTO_INCREMENT = 1;");
        updateData("INSERT INTO `sys`.`punti_prelievo` (`nome`, `indirizzo`) VALUES ('B1', 'via Giacinto');");
        updateData("INSERT INTO `sys`.`punti_prelievo` (`nome`, `indirizzo`) VALUES ('B2', 'via Giuseppe');");
        updateData("INSERT INTO `sys`.`punti_prelievo` (`nome`, `indirizzo`) VALUES ('B3', 'via Paolo');");
    }

    @Test
    void getMagazziniDisponibili() {
        GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
        ArrayList<PuntoPrelievo> test = gestoreMagazzini.getMagazziniDisponibili();
        assertEquals(test.get(0).getNome(),"B1");
        assertEquals(test.get(1).getIndirizzo(),"via Giuseppe");
        assertEquals(test.get(2).getNome(),"B3");
    }
}