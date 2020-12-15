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
    void getMerce() {
        Negozio negozio = new Negozio(1);
        Merce merce1 = negozio.getMerce(1);
        Merce merce2 = negozio.getMerce(1);
        merce1.setQuantita(500);
        assertEquals(merce1.getQuantita(), 500);
        assertEquals(merce2.getQuantita(), 500);
    }

    @Test
    void getMerceDisponibile() {
        Negozio negozio = new Negozio(1);
        ArrayList<Merce> inventario = negozio.getMerceDisponibile();
        assertEquals(1, inventario.get(0).getID());
        assertEquals(1, inventario.get(1).getIDNegozio());
        assertEquals("test Negozio", inventario.get(2).getDescrizione());

        assertEquals(3, inventario.size());
        inventario = negozio.getMerceDisponibile();
        assertEquals(3, inventario.size());
    }
}