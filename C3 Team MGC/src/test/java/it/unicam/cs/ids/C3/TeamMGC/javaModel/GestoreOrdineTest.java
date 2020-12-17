package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestoreOrdineTest {

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
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        Merce merce = new Merce(negozio.getIDNegozio(), 52, "gomma", 10);
        Merce merce1 = new Merce(negozio.getIDNegozio(), 10, "matita", 20);
        Merce merce2 = new Merce(negozio.getIDNegozio(), 20,"pennello",5);
        Merce merce3 = new Merce(negozio.getIDNegozio(), 15,"maglietta",0);

        Cliente cliente = new Cliente("Maria","Giuseppa");
        Cliente cliente1 = new Cliente("Giuseppe", "Rossi");

        negozio.addMerce(merce);
        negozio.addMerce(merce1);
        negozio.addMerce(merce2);
        negozio.addMerce(merce3);

        GestoreOrdine gestoreOrdine = new GestoreOrdine(negozio);
        Ordine ordine = gestoreOrdine.registraOrdine(cliente.getID(), "Maria", "Giuseppa");
        Ordine ordine1 = gestoreOrdine.registraOrdine(cliente1.getID(), "Giuseppe", "Rossi");

        gestoreOrdine.registraMerce(merce.getID(), 10, ordine);
        gestoreOrdine.registraMerce(merce2.getID(), 4, ordine1);

        assertEquals(520, ordine.getTotalePrezzo());
        assertTrue(negozio.getMerceDisponibile().contains(merce));
        assertEquals(ordine.getID(), ordine.getMerci().get(0).getIDOrdine());
        assertEquals(80, ordine1.getTotalePrezzo());
        assertEquals(ordine1.getID(), ordine1.getMerci().get(0).getIDOrdine());
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdine.registraMerce(3,6, ordine1));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdine.registraMerce(4,2, ordine1));

    }

    @Test
    void terminaOrdine() {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        Merce merce = new Merce(1, 52, "gomma", 10);
        Merce merce1 = new Merce(1, 10, "matita", 20);
        Cliente cliente = new Cliente("Maria","Giuseppa");
        GestoreOrdine gestoreOrdine = new GestoreOrdine(negozio);
        Ordine ordine = gestoreOrdine.registraOrdine(cliente.getID(), "Maria", "Giuseppa");

        gestoreOrdine.registraMerce(merce.getID(),5,ordine);
        gestoreOrdine.registraMerce(merce1.getID(),10,ordine);
        gestoreOrdine.terminaOrdine(ordine);
        assertSame(StatoOrdine.PAGATO, ordine.getStato());

    }
}