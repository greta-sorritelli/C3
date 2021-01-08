package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestoreOrdiniTest {

    @Test
    void addResidenza() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome());
        Ordine ordine1 = gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0)));
        assertEquals(ordine.size(), 8);
        gestoreOrdini.addResidenza(ordine1.getID(), "via Roma, 8");
        assertEquals(ordine1.getResidenza(), "via Roma, 8");
        gestoreOrdini.addResidenza(ordine1.getID(), "via Colombo, 9");
        ArrayList<String> ordineNew = ordine1.getDettagli();
        assertNotEquals(ordine, ordineNew);
        assertNotEquals(ordineNew.get(6), "via Roma, 8");
        assertEquals(ordineNew.get(6), "via Colombo, 9");
    }

    @BeforeEach
    void clearDB() throws SQLException {
        updateData("delete from sys.ordini;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        updateData("delete from sys.merci;");
        updateData("alter table merci AUTO_INCREMENT = 1;");
        updateData("delete from sys.inventario;");
        updateData("alter table inventario AUTO_INCREMENT = 1;");
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
        updateData("delete from sys.negozi;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
    }

    @Test
    void registraMerce() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        Merce merce = new Merce(negozio.getIDNegozio(), 52, "gomma", 10);
        Merce merce1 = new Merce(negozio.getIDNegozio(), 10, "matita", 20);
        Merce merce2 = new Merce(negozio.getIDNegozio(), 20, "pennello", 5);
        Merce merce3 = new Merce(negozio.getIDNegozio(), 15, "maglietta", 0);

        Cliente cliente1 = new Cliente("Maria", "Giuseppa");
        Cliente cliente2 = new Cliente("Giuseppe", "Rossi");

        negozio.addMerce(merce);
        negozio.addMerce(merce1);
        negozio.addMerce(merce2);
        negozio.addMerce(merce3);

        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(cliente1.getID(), cliente1.getNome(), cliente1.getCognome());
        ArrayList<String> ordine2 = gestoreOrdini.registraOrdine(cliente2.getID(), cliente2.getNome(), cliente2.getCognome());

        int ID1 = Integer.parseInt(ordine1.get(0));
        int ID2 = Integer.parseInt(ordine2.get(0));

        gestoreOrdini.registraMerce(merce.getID(), 10, Integer.parseInt(ordine1.get(0)));
        gestoreOrdini.registraMerce(merce1.getID(), 4, Integer.parseInt(ordine2.get(0)));

        ordine1 = gestoreOrdini.getOrdine(ID1).getDettagli();
        ordine2 = gestoreOrdini.getOrdine(ID2).getDettagli();

        assertEquals(520.0, Double.parseDouble(ordine1.get(4)));
        assertTrue(negozio.getMerceDisponibile().contains(merce));
        assertEquals("[MerceOrdine{ID=1, IDOrdine=1, prezzo=52.0, descrizione='gomma', quantita=10, stato=PAGATO}]", ordine1.get(7));

        assertEquals(40.0, Double.parseDouble(ordine2.get(4)));
        assertTrue(negozio.getMerceDisponibile().contains(merce1));

        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraMerce(merce2.getID(), 6, ID1));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraMerce(merce3.getID(), 2, ID2));
    }

    @Test
    void registraOrdine() throws SQLException {
        Negozio negozio = new Negozio("ABC", null, null, null, null, null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        ArrayList<String> ordineDettagli = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome());

        assertEquals(cliente.getID(), Integer.parseInt(ordineDettagli.get(1)));
        assertEquals("Maria", ordineDettagli.get(2));
        assertEquals("Giuseppa", ordineDettagli.get(3));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraOrdine(1000, "Matteo", "Rondini"));
    }

    //todo CAMBIARE INFORMAZIONI DEGLI OGGETTI

    @Test
    void setPuntoPrelievo() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome());
        PuntoPrelievo puntoPrelievo = new PuntoPrelievo("Roma", "Magazzino Centrale Lazio");
        assertEquals(-1, gestoreOrdini.getOrdine(1).getPuntoPrelievo());
        gestoreOrdini.setPuntoPrelievo(Integer.parseInt(ordine.get(0)),1);
        assertEquals(1, gestoreOrdini.getOrdine(1).getPuntoPrelievo());

    }

    @Test
    void setStatoMerce() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome());
        Merce merce = new Merce(negozio.getIDNegozio(), 52, "gomma", 10);
        negozio.addMerce(merce);
        gestoreOrdini.registraMerce(1,10,Integer.parseInt(ordine.get(0)));
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(1).getStato());
        gestoreOrdini.setStatoMerce(1,StatoOrdine.AFFIDATO_AL_CORRIERE);
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, gestoreOrdini.getMerceOrdine(1).getStato());
    }

    @Test
    void setStatoOrdine() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome());
        assertEquals(StatoOrdine.DA_PAGARE, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
        gestoreOrdini.setStatoOrdine(Integer.parseInt(ordine.get(0)),StatoOrdine.IN_DEPOSITO);
        assertEquals(StatoOrdine.IN_DEPOSITO, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
    }

    @Test
    void terminaOrdine() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        Merce merce = new Merce(negozio.getIDNegozio(), 52, "gomma", 10);
        Merce merce1 = new Merce(negozio.getIDNegozio(), 10, "matita", 20);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome());

        gestoreOrdini.registraMerce(merce.getID(), 5, Integer.parseInt(ordine.get(0)));
        gestoreOrdini.registraMerce(merce1.getID(), 10, Integer.parseInt(ordine.get(0)));
        ordine = gestoreOrdini.terminaOrdine(Integer.parseInt(ordine.get(0)));
        assertSame(StatoOrdine.PAGATO.toString(), ordine.get(5));
    }
}