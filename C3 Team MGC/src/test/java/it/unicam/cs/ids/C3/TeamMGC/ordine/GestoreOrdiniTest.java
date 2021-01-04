package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestoreOrdiniTest {

    @BeforeEach
    void clearDB() throws SQLException {
        updateData("delete from sys.ordini;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        updateData("delete from `sys`.`merci`;");
        updateData("alter table merci AUTO_INCREMENT = 1;");
        updateData("delete from sys.inventario;");
        updateData("alter table inventario AUTO_INCREMENT = 1;");
        updateData("delete from `sys`.`clienti`;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
        updateData("delete from `sys`.`negozi`;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
    }

    @Test
    void registraOrdine() {
        Negozio negozio = new Negozio("ABC", null, null, null, null, null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        Ordine ordine = gestoreOrdini.registraOrdine(1, "Maria", "Giuseppa");

        assertEquals(1, cliente.getID());
        assertEquals(1, ordine.getID());
        assertEquals(1, ordine.getIDCliente());
        assertEquals("Maria", ordine.getNomeCliente());
        assertEquals("Giuseppa", ordine.getCognomeCliente());
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraOrdine(10, "Matteo", "Rondini"));

    }

    @Test
    void registraMerce() {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        Merce merce = new Merce(negozio.getIDNegozio(), 52, "gomma", 10);
        Merce merce1 = new Merce(negozio.getIDNegozio(), 10, "matita", 20);
        Merce merce2 = new Merce(negozio.getIDNegozio(), 20, "pennello", 5);
        Merce merce3 = new Merce(negozio.getIDNegozio(), 15, "maglietta", 0);

        Cliente cliente = new Cliente("Maria", "Giuseppa");
        Cliente cliente1 = new Cliente("Giuseppe", "Rossi");

        negozio.addMerce(merce);
        negozio.addMerce(merce1);
        negozio.addMerce(merce2);
        negozio.addMerce(merce3);

        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Ordine ordine = gestoreOrdini.registraOrdine(cliente.getID(), "Maria", "Giuseppa");
        Ordine ordine1 = gestoreOrdini.registraOrdine(cliente1.getID(), "Giuseppe", "Rossi");

        gestoreOrdini.registraMerce(merce.getID(), 10, ordine.getID());
        gestoreOrdini.registraMerce(merce2.getID(), 4, ordine1.getID());

        assertEquals(520, ordine.getTotalePrezzo());
        assertTrue(negozio.getMerceDisponibile().contains(merce));
        assertEquals(ordine.getID(), ordine.getMerci().get(0).getIDOrdine());
        assertEquals(80, ordine1.getTotalePrezzo());
        assertEquals(ordine1.getID(), ordine1.getMerci().get(0).getIDOrdine());
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraMerce(3, 6, ordine1.getID()));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraMerce(4, 2, ordine1.getID()));

    }

    @Test
    void terminaOrdine() {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        Merce merce = new Merce(1, 52, "gomma", 10);
        Merce merce1 = new Merce(1, 10, "matita", 20);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Ordine ordine = gestoreOrdini.registraOrdine(cliente.getID(), "Maria", "Giuseppa");

        gestoreOrdini.registraMerce(merce.getID(), 5, ordine.getID());
        gestoreOrdini.registraMerce(merce1.getID(), 10, ordine.getID());
        gestoreOrdini.terminaOrdine(ordine.getID());
        assertSame(StatoOrdine.PAGATO, ordine.getStato());

    }

    @Test
    void addResidenza() {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        Ordine ordine = gestoreOrdini.registraOrdine(cliente.getID(), "Maria", "Giuseppa");
        ordine.setStato(StatoOrdine.PAGATO);
        assertNull(ordine.getResidenza());
        gestoreOrdini.addResidenza(ordine.getID(), "via Roma, 8");
        assertEquals(ordine.getResidenza(), "via Roma, 8");
        gestoreOrdini.addResidenza(ordine.getID(), "via Colombo, 9");
        assertNotEquals(ordine.getResidenza(), "via Roma, 8");
        assertEquals(ordine.getResidenza(), "via Colombo, 9");
    }
}