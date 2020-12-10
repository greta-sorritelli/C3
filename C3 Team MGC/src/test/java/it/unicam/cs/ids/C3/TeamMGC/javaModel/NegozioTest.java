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
        updateData("TRUNCATE `sys`.`merci`;");
        updateData("INSERT INTO `sys`.`merci` (`IDOrdine`, `prezzo`, `descrizione`, `quantita`, `stato`) VALUES ('1', '1', 'test', '1', 'PAGATO');");
        updateData("INSERT INTO `sys`.`merci` (`IDOrdine`, `prezzo`, `descrizione`, `quantita`, `stato`) VALUES ('1', '2', 'test', '1', 'PAGATO');");
        updateData("INSERT INTO `sys`.`merci` (`IDOrdine`, `prezzo`, `descrizione`, `quantita`, `stato`) VALUES ('1', '10', 'test', '1', 'PAGATO');");
    }

    @Test
    void getMerceDisponibile() {
        Negozio negozio = new Negozio();
        ArrayList<Merce> toTest = negozio.getMerceDisponibile();
        assertEquals(toTest.get(0).getID(), 1);
        assertEquals(toTest.get(1).getIDOrdine(), 1);
        assertEquals(toTest.get(2).getStato(), StatoOrdine.PAGATO);
    }
}