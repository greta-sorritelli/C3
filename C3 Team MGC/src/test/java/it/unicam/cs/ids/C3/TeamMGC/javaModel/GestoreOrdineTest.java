package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestoreOrdineTest {

    @BeforeEach
    void clearDB() throws SQLException {
        updateData("TRUNCATE `sys`.`ordini`;");
        updateData("TRUNCATE `sys`.`merci`;");
        updateData("TRUNCATE `sys`.`inventario`;");
        updateData("TRUNCATE `sys`.`clienti`;");
    }

    @Test
    void registraOrdine() {
        Negozio negozio = new Negozio(1);
        GestoreOrdine gestoreOrdine = new GestoreOrdine(negozio);
        Cliente cliente = new Cliente("Maria","Giuseppa");
        Ordine ordine = gestoreOrdine.registraOrdine(1, "Maria", "Giuseppa");

        assertEquals(1,cliente.getID());
        assertEquals(1, ordine.getID());
        assertEquals(1, ordine.getIDCliente());
        assertEquals("Maria", ordine.getNomeCliente());
        assertEquals("Giuseppa", ordine.getCognomeCliente());
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdine.registraOrdine(10, "Matteo", "Rondini"));

    }

    @Test
    void registraMerce() {
        Negozio negozio = new Negozio(1);

        Merce merce = new Merce(1, 52, "gomma", 10);
        Merce merce1 = new Merce(1, 10, "matita", 20);
        Merce merce2 = new Merce(1,20,"pennello",5);
        Merce merce3 = new Merce(1,15,"maglietta",0);

        Cliente cliente = new Cliente("Maria","Giuseppa");
        Cliente cliente1 = new Cliente("Giuseppe", "Rossi");

        negozio.addMerce(merce);
        negozio.addMerce(merce1);
        negozio.addMerce(merce2);
        negozio.addMerce(merce3);

        GestoreOrdine gestoreOrdine = new GestoreOrdine(negozio);
        Ordine ordine = gestoreOrdine.registraOrdine(1, "Maria", "Giuseppa");
        Ordine ordine1 = gestoreOrdine.registraOrdine(2, "Giuseppe", "Rossi");

        gestoreOrdine.registraMerce(1, 10, ordine);
        gestoreOrdine.registraMerce(2, 6, ordine1);

        assertEquals(520, ordine.getTotalePrezzo());
        assertTrue(negozio.getMerceDisponibile().contains(merce));
        assertEquals(ordine.getID(), ordine.getMerci().get(0).getIDOrdine());
        assertEquals(60, ordine1.getTotalePrezzo());
        assertEquals(ordine1.getID(), ordine1.getMerci().get(0).getIDOrdine());
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdine.registraMerce(3,6, ordine1));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdine.registraMerce(4,2, ordine1));

    }

    @Test
    void terminaOrdine() {
        Negozio negozio = new Negozio(1);
        Merce merce = new Merce(1, 52, "gomma", 10);
        Merce merce1 = new Merce(1, 10, "matita", 20);
        Cliente cliente = new Cliente("Maria","Giuseppa");
        GestoreOrdine gestoreOrdine = new GestoreOrdine(negozio);
        Ordine ordine = gestoreOrdine.registraOrdine(1, "Maria", "Giuseppa");

        gestoreOrdine.registraMerce(1,5,ordine);
        gestoreOrdine.registraMerce(2,10,ordine);
        gestoreOrdine.terminaOrdine(ordine);
        assertSame(StatoOrdine.PAGATO, ordine.getStato());

    }
}