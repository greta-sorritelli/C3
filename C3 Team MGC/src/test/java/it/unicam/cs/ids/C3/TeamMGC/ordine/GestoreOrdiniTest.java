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
    void getOrdine() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente1 = new Cliente("Maria", "Giuseppa");
        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(cliente1.getID(), cliente1.getNome(), cliente1.getCognome());
        int ID1 = Integer.parseInt(ordine1.get(0));
        PuntoPrelievo p = new PuntoPrelievo("Via dei Sequence Diagram", "SD 1");
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);
        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));

        gestoreOrdini.registraMerce(IDMerce1,5, ID1);
        gestoreOrdini.terminaOrdine(ID1);
        gestoreOrdini.getOrdine(ID1).setPuntoPrelievo(p.getID());

        assertEquals(cliente1.getID(), gestoreOrdini.getOrdine(ID1).getIDCliente());
        assertEquals("Maria",gestoreOrdini.getOrdine(ID1).getNomeCliente());
        assertEquals("Giuseppa",gestoreOrdini.getOrdine(ID1).getCognomeCliente());
        assertEquals(260.0, gestoreOrdini.getOrdine(ID1).getTotalePrezzo());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getOrdine(ID1).getStato());
        assertEquals(p.getID(), gestoreOrdini.getOrdine(ID1).getPuntoPrelievo());
        assertEquals(gestoreOrdini.getMerceOrdine(1),gestoreOrdini.getOrdine(ID1).getMerci().get(0));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.getOrdine(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getDettagliOrdine() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente1 = new Cliente("Maria", "Giuseppa");
        PuntoPrelievo p = new PuntoPrelievo("Via dei Sequence Diagram", "SD 1");
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);

        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(cliente1.getID(), cliente1.getNome(), cliente1.getCognome());
        int ID1 = Integer.parseInt(ordine1.get(0));

        gestoreOrdini.registraMerce(IDMerce1,5, ID1);
        gestoreOrdini.terminaOrdine(ID1);
        gestoreOrdini.getOrdine(ID1).setPuntoPrelievo(p.getID());

        ArrayList<String> ordineLista = new ArrayList<>();
        ordineLista.add(ordine1.get(0));
        ordineLista.add(String.valueOf(cliente1.getID()));
        ordineLista.add("Maria");
        ordineLista.add("Giuseppa");
        ordineLista.add("260.0");
        ordineLista.add(StatoOrdine.PAGATO.toString());
        ordineLista.add(String.valueOf(p.getID()));
        ordineLista.add(gestoreOrdini.getOrdine(ID1).getMerci().toString());
        assertEquals(ordineLista, gestoreOrdini.getDettagliOrdine(ID1));
    }

    @Test
    void getDettagliMerce() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);

        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);
        ArrayList<String> merceDettagli2 = negozio.inserisciNuovaMerce(10, "matita", 20);
        ArrayList<String> merceDettagli3 = negozio.inserisciNuovaMerce(20, "pennello", 5);

        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        int IDMerce2 = Integer.parseInt(merceDettagli2.get(0));
        int IDMerce3 = Integer.parseInt(merceDettagli3.get(0));

        Cliente cliente1 = new Cliente("Maria", "Giuseppa");

        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(cliente1.getID(), cliente1.getNome(), cliente1.getCognome());
        int ID1 = Integer.parseInt(ordine1.get(0));

        gestoreOrdini.registraMerce(IDMerce1,5, ID1);
        gestoreOrdini.registraMerce(IDMerce2,10,ID1);
        gestoreOrdini.registraMerce(IDMerce3,5, ID1);

        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(1).getStato());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(2).getStato());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(3).getStato());

        gestoreOrdini.setStatoMerce(1,StatoOrdine.AFFIDATO_AL_CORRIERE);
        gestoreOrdini.setStatoMerce(2,StatoOrdine.IN_TRANSITO);
        gestoreOrdini.setStatoMerce(3,StatoOrdine.RITIRATO);

        assertEquals("gomma", gestoreOrdini.getDettagliMerce(StatoOrdine.AFFIDATO_AL_CORRIERE).get(0).get(3));
        assertEquals("matita", gestoreOrdini.getDettagliMerce(StatoOrdine.IN_TRANSITO).get(0).get(3));
        assertEquals("pennello", gestoreOrdini.getDettagliMerce(StatoOrdine.RITIRATO).get(0).get(3));
        assertEquals("52.0", gestoreOrdini.getDettagliMerce(StatoOrdine.AFFIDATO_AL_CORRIERE).get(0).get(2));
        assertEquals("10.0", gestoreOrdini.getDettagliMerce(StatoOrdine.IN_TRANSITO).get(0).get(2));
        assertEquals("20.0", gestoreOrdini.getDettagliMerce(StatoOrdine.RITIRATO).get(0).get(2));
    }

    @Test
    void registraMerce() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);

        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);
        ArrayList<String> merceDettagli2 = negozio.inserisciNuovaMerce(10, "matita", 20);
        ArrayList<String> merceDettagli3 = negozio.inserisciNuovaMerce(20, "pennello", 5);
        ArrayList<String> merceDettagli4 = negozio.inserisciNuovaMerce(15, "maglietta", 0);

        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        int IDMerce2 = Integer.parseInt(merceDettagli2.get(0));
        int IDMerce3 = Integer.parseInt(merceDettagli3.get(0));
        int IDMerce4 = Integer.parseInt(merceDettagli4.get(0));

        Cliente cliente1 = new Cliente("Maria", "Giuseppa");
        Cliente cliente2 = new Cliente("Giuseppe", "Rossi");

        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(cliente1.getID(), cliente1.getNome(), cliente1.getCognome());
        ArrayList<String> ordine2 = gestoreOrdini.registraOrdine(cliente2.getID(), cliente2.getNome(), cliente2.getCognome());

        int ID1 = Integer.parseInt(ordine1.get(0));
        int ID2 = Integer.parseInt(ordine2.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 10, Integer.parseInt(ordine1.get(0)));
        gestoreOrdini.registraMerce(IDMerce2, 4, Integer.parseInt(ordine2.get(0)));

        ordine1 = gestoreOrdini.getOrdine(ID1).getDettagli();
        ordine2 = gestoreOrdini.getOrdine(ID2).getDettagli();

        assertEquals(520.0, Double.parseDouble(ordine1.get(4)));
        assertTrue(negozio.getMerceDisponibile().contains(negozio.getMerce(IDMerce1)));
        assertEquals("[MerceOrdine{ID=1, IDOrdine=1, prezzo=52.0, descrizione='gomma', quantita=10, stato=PAGATO}]", ordine1.get(7));

        assertEquals(40.0, Double.parseDouble(ordine2.get(4)));
        assertTrue(negozio.getMerceDisponibile().contains(negozio.getMerce(IDMerce2)));

        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraMerce(IDMerce3, 6, ID1));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraMerce(IDMerce4, 2, ID2));
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
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.getOrdine(1000));
    }

    //todo CAMBIARE INFORMAZIONI DEGLI OGGETTI

    @Test
    void setPuntoPrelievo() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome());
        PuntoPrelievo puntoPrelievo = new PuntoPrelievo("Roma", "Magazzino Centrale Lazio");
        assertEquals(-1, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getPuntoPrelievo());
        gestoreOrdini.setPuntoPrelievo(Integer.parseInt(ordine.get(0)), puntoPrelievo.getID());
        assertEquals(puntoPrelievo.getID(), gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getPuntoPrelievo());

    }

    @Test
    void setStatoMerce() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome());

        negozio.inserisciNuovaMerce(52, "gomma", 10);
        gestoreOrdini.registraMerce(1, 10, Integer.parseInt(ordine.get(0)));
        gestoreOrdini.terminaOrdine(Integer.parseInt(ordine.get(0)));

        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(1).getStato());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
        gestoreOrdini.setStatoMerce(1, StatoOrdine.AFFIDATO_AL_CORRIERE);
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, gestoreOrdini.getMerceOrdine(1).getStato());
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
    }

    @Test
    void setStatoOrdine() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome());
        assertEquals(StatoOrdine.DA_PAGARE, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
        gestoreOrdini.setStatoOrdine(Integer.parseInt(ordine.get(0)), StatoOrdine.IN_DEPOSITO);
        assertEquals(StatoOrdine.IN_DEPOSITO, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
    }

    @Test
    void terminaOrdine() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        Merce merce = new Merce(negozio.getID(), 52, "gomma", 10);
        Merce merce1 = new Merce(negozio.getID(), 10, "matita", 20);
        Cliente cliente = new Cliente("Maria", "Giuseppa");
        GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome());

        gestoreOrdini.registraMerce(merce.getID(), 5, Integer.parseInt(ordine.get(0)));
        gestoreOrdini.registraMerce(merce1.getID(), 10, Integer.parseInt(ordine.get(0)));
        ordine = gestoreOrdini.terminaOrdine(Integer.parseInt(ordine.get(0)));
        assertSame(StatoOrdine.PAGATO.toString(), ordine.get(5));
        gestoreOrdini.getMerceOrdine(merce.getID()).setStato(StatoOrdine.DA_PAGARE);
        gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getMerci().get(0).setStato(StatoOrdine.DA_PAGARE);
        int idOrdine = Integer.parseInt(ordine.get(0));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.terminaOrdine(idOrdine));
    }
}