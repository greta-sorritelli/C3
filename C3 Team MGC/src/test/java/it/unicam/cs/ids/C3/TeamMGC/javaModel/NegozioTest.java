package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class NegozioTest {
    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("TRUNCATE `sys`.`inventario`;");
        updateData("INSERT INTO `sys`.`inventario` (`IDNegozio`, `prezzo`, `descrizione`, `quantita`) VALUES ('1', '10', 'test Negozio', '10');");
        updateData("INSERT INTO `sys`.`inventario` (`IDNegozio`, `prezzo`, `descrizione`, `quantita`) VALUES ('1', '5', 'test Negozio', '1');");
        updateData("INSERT INTO `sys`.`inventario` (`IDNegozio`, `prezzo`, `descrizione`, `quantita`) VALUES ('1', '50', 'test Negozio', '20');");
    }

    @Test
    void getMerceDisponibile() {
        Negozio negozio = new Negozio();
        ArrayList<Merce> toTest = negozio.getMerceDisponibile();
        assertEquals(toTest.get(0).getID(), 1);
        assertEquals(toTest.get(1).getIDNegozio(), 1);
        assertEquals(toTest.get(2).getDescrizione(), "test Negozio");
    }
}