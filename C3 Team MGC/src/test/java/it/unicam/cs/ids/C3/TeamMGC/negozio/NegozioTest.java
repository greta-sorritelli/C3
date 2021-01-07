package it.unicam.cs.ids.C3.TeamMGC.negozio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class NegozioTest {
    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("delete from sys.inventario;");
        updateData("alter table inventario AUTO_INCREMENT = 1;");
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('1', '10', 'test Negozio', '10');");
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('1', '5', 'test Negozio', '1');");
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('1', '50', 'test Negozio', '20');");
    }

    @Test
    void getMerce() throws SQLException {
        Negozio negozio = new Negozio(1);
        Merce merce1 = negozio.getMerce(1);
        Merce merce2 = negozio.getMerce(1);
        merce1.setQuantita(500);
        assertEquals(merce1.getQuantita(), 500);
        assertEquals(merce2.getQuantita(), 500);
        assertTrue(negozio.getMerceDisponibile().contains(merce1));
        assertTrue(negozio.getMerceDisponibile().contains(merce2));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> negozio.getMerce(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getMerceDisponibile() throws SQLException {
        Negozio negozio = new Negozio(1);
        ArrayList<Merce> inventario = negozio.getMerceDisponibile();
        assertEquals(1, inventario.get(0).getID());
        assertEquals(1, inventario.get(1).getIDNegozio());
        assertEquals("test Negozio", inventario.get(2).getDescrizione());

        assertEquals(3, inventario.size());
        inventario = negozio.getMerceDisponibile();
        assertEquals(3, inventario.size());
    }

    @Test
    void removeMerce() throws SQLException {
        Negozio negozio = new Negozio(1);
        Merce toDelete = new Merce(1, 15, "test delete", 10);
        negozio.removeMerce(toDelete.getID());
        assertFalse(negozio.getMerceDisponibile().contains(toDelete));
    }
}