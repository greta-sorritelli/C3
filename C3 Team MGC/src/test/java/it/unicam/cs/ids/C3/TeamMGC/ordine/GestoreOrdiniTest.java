package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;
import it.unicam.cs.ids.C3.TeamMGC.corriere.SimpleCorriere;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleMerce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleNegozio;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GestoreOrdiniTest {
    static final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    static Negozio negozio;

    @BeforeAll
    static void clearDB() throws SQLException {
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
        updateData("delete from sys.punti_prelievo;");
        updateData("alter table punti_prelievo AUTO_INCREMENT = 1;");
        negozio = new SimpleNegozio("Merceria", "Oggettistica", "09:00", "18:30", "Via Roma, 30", "123-123-123");
    }

    @Test
    void addResidenza() throws SQLException {
        Cliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordineDettagli = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio);
        Ordine ordine = gestoreOrdini.getOrdine(Integer.parseInt(ordineDettagli.get(0)));
        assertEquals(9, ordineDettagli.size());
        gestoreOrdini.addResidenza(ordine.getID(), "via Roma, 8");
        assertEquals(ordine.getResidenza(), "via Roma, 8");
        gestoreOrdini.addResidenza(ordine.getID(), "via Colombo, 9");
        ArrayList<String> ordineNew = ordine.getDettagli();
        assertNotEquals(ordineDettagli, ordineNew);
        assertNotEquals(ordineNew.get(6), "via Roma, 8");
        assertEquals(ordineNew.get(6), "via Colombo, 9");
    }

    @Test
    void associaMerceCorriere() throws SQLException {
        Cliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordineDettagli = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio);
        Ordine ordine = gestoreOrdini.getOrdine(Integer.parseInt(ordineDettagli.get(0)));
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(100, "Borsa", 15);
        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        gestoreOrdini.registraMerce(IDMerce1, 5, ordine.getID(), negozio);
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(IDMerce1).getStato());
        Corriere corriere = new SimpleCorriere("Giulio", "Bartolini", true);
        gestoreOrdini.associaMerceCorriere(corriere.getID(), IDMerce1);
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, gestoreOrdini.getMerceOrdine(IDMerce1).getStato());
    }

    @Test
    void getDettagliMerce() throws SQLException {
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(100, "Borsa", 15);
        ArrayList<String> merceDettagli2 = negozio.inserisciNuovaMerce(20, "Cuffie", 20);
        ArrayList<String> merceDettagli3 = negozio.inserisciNuovaMerce(30, "Microfono", 5);

        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        int IDMerce2 = Integer.parseInt(merceDettagli2.get(0));
        int IDMerce3 = Integer.parseInt(merceDettagli3.get(0));

        Cliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");

        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio);
        int IDOrdine = Integer.parseInt(ordine.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 5, IDOrdine, negozio);
        gestoreOrdini.registraMerce(IDMerce2, 10, IDOrdine, negozio);
        gestoreOrdini.registraMerce(IDMerce3, 5, IDOrdine, negozio);

        int IDMerceOrdine1 = gestoreOrdini.getOrdine(IDOrdine).getMerci().get(0).getID();
        int IDMerceOrdine2 = gestoreOrdini.getOrdine(IDOrdine).getMerci().get(1).getID();
        int IDMerceOrdine3 = gestoreOrdini.getOrdine(IDOrdine).getMerci().get(2).getID();

        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(IDMerceOrdine1).getStato());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(IDMerceOrdine2).getStato());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(IDMerceOrdine3).getStato());

        gestoreOrdini.setStatoMerce(IDMerceOrdine1, StatoOrdine.AFFIDATO_AL_CORRIERE);
        gestoreOrdini.setStatoMerce(IDMerceOrdine2, StatoOrdine.IN_TRANSITO);
        gestoreOrdini.setStatoMerce(IDMerceOrdine3, StatoOrdine.RITIRATO);

        assertTrue(gestoreOrdini.getDettagliMerce(StatoOrdine.AFFIDATO_AL_CORRIERE).contains(
                gestoreOrdini.getMerceOrdine(IDMerceOrdine1).getDettagli()));
        assertTrue(gestoreOrdini.getDettagliMerce(StatoOrdine.IN_TRANSITO).contains(
                gestoreOrdini.getMerceOrdine(IDMerceOrdine2).getDettagli()));
        assertTrue(gestoreOrdini.getDettagliMerce(StatoOrdine.RITIRATO).contains(
                gestoreOrdini.getMerceOrdine(IDMerceOrdine3).getDettagli()));
    }

    @Test
    void getDettagliMerciOfCorriere() throws SQLException {
        Cliente cliente = new SimpleCliente("Maria", "Giuseppa");
        PuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 3");
        Corriere corriere = new SimpleCorriere("Luca", "Blu", true);

        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio);
        int IDOrdine = Integer.parseInt(ordine.get(0));

        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(8, "Maglietta", 2);
        ArrayList<String> merceDettagli2 = negozio.inserisciNuovaMerce(2.5, "Caramella", 10);

        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        int IDMerce2 = Integer.parseInt(merceDettagli2.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 2, IDOrdine, negozio);
        gestoreOrdini.registraMerce(IDMerce2, 8, IDOrdine, negozio);

        int IDMerceOrdine1 = gestoreOrdini.getOrdine(IDOrdine).getMerci().get(0).getID();
        int IDMerceOrdine2 = gestoreOrdini.getOrdine(IDOrdine).getMerci().get(1).getID();

        gestoreOrdini.terminaOrdine(IDOrdine);
        gestoreOrdini.getOrdine(IDOrdine).setPuntoPrelievo(p.getID());
        gestoreOrdini.associaMerceCorriere(corriere.getID(), IDMerceOrdine1);
        gestoreOrdini.associaMerceCorriere(corriere.getID(), IDMerceOrdine2);

        ArrayList<ArrayList<String>> toTest = gestoreOrdini.getDettagliMerciOfCorriere(corriere.getID(), StatoOrdine.AFFIDATO_AL_CORRIERE);
        assertFalse(toTest.isEmpty());
        assertEquals(String.valueOf(IDOrdine), toTest.get(0).get(1));
        assertEquals(String.valueOf(IDOrdine), toTest.get(1).get(1));
        assertEquals("8.0", toTest.get(0).get(2));
        assertEquals("2.5", toTest.get(1).get(2));
        assertEquals("Maglietta", toTest.get(0).get(3));
        assertEquals("Caramella", toTest.get(1).get(3));
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE.toString(), toTest.get(0).get(5));
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE.toString(), toTest.get(1).get(5));
    }

    @Test
    void getDettagliMerciResidenza() throws SQLException {
        Cliente cliente = new SimpleCliente("Maria", "Giuseppa");

        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio);
        int IDOrdine = Integer.parseInt(ordine.get(0));

        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(16, "Jeans", 2);
        ArrayList<String> merceDettagli2 = negozio.inserisciNuovaMerce(25, "Caricatore", 10);

        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        int IDMerce2 = Integer.parseInt(merceDettagli2.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 2, IDOrdine, negozio);
        gestoreOrdini.registraMerce(IDMerce2, 8, IDOrdine, negozio);

        gestoreOrdini.terminaOrdine(IDOrdine);
        gestoreOrdini.getOrdine(IDOrdine).setResidenza("Camerino, Piazza della Computer Science 30");

        ArrayList<ArrayList<String>> toTest = gestoreOrdini.getDettagliMerciResidenza("Camerino, Piazza della Computer Science 30");
        assertFalse(toTest.isEmpty());
        assertEquals(String.valueOf(IDOrdine), toTest.get(0).get(1));
        assertEquals(String.valueOf(IDOrdine), toTest.get(1).get(1));
        assertEquals("16.0", toTest.get(0).get(2));
        assertEquals("25.0", toTest.get(1).get(2));
        assertEquals("Jeans", toTest.get(0).get(3));
        assertEquals("Caricatore", toTest.get(1).get(3));
        assertEquals(StatoOrdine.PAGATO.toString(), toTest.get(0).get(5));
        assertEquals(StatoOrdine.PAGATO.toString(), toTest.get(1).get(5));
    }

    @Test
    void getDettagliOrdine() throws SQLException {
        Cliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");
        PuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 1");
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(1.20, "Righello", 10);

        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio);
        int IDOrdine = Integer.parseInt(ordine1.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 5, IDOrdine, negozio);
        gestoreOrdini.terminaOrdine(IDOrdine);
        gestoreOrdini.getOrdine(IDOrdine).setPuntoPrelievo(p.getID());

        ArrayList<String> ordineLista = new ArrayList<>();
        ordineLista.add(ordine1.get(0));
        ordineLista.add(String.valueOf(simpleCliente1.getID()));
        ordineLista.add("Maria");
        ordineLista.add("Giuseppa");
        ordineLista.add("6.0");
        ordineLista.add(StatoOrdine.PAGATO.toString());
        ordineLista.add(String.valueOf(p.getID()));
        ordineLista.add(String.valueOf(negozio.getID()));
        ordineLista.add(gestoreOrdini.getOrdine(IDOrdine).getMerci().toString());
        assertEquals(ordineLista, gestoreOrdini.getDettagliOrdine(IDOrdine));
    }

    @Test
    void getInDepositMerci() throws SQLException {
        Cliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");
        PuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 2");
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(2, "Temperino", 10);

        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio);
        int IDOrdine = Integer.parseInt(ordine1.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 5, IDOrdine, negozio);
        gestoreOrdini.getOrdine(IDOrdine).setPuntoPrelievo(p.getID());
        int IDMerceOrdine1 = gestoreOrdini.getOrdine(IDOrdine).getMerci().get(0).getID();
        gestoreOrdini.setStatoMerce(IDMerceOrdine1, StatoOrdine.IN_DEPOSITO);

        ArrayList<Ordine> ordini = new ArrayList<>();
        ordini.add(gestoreOrdini.getOrdine(IDOrdine));

        ArrayList<ArrayList<String>> merciInDeposito = gestoreOrdini.getInDepositMerci(ordini);

        assertFalse(merciInDeposito.isEmpty());
        assertEquals(String.valueOf(IDMerceOrdine1), merciInDeposito.get(0).get(0));
        assertEquals(String.valueOf(IDOrdine), merciInDeposito.get(0).get(1));
        assertEquals("2.0", merciInDeposito.get(0).get(2));
        assertEquals("Temperino", merciInDeposito.get(0).get(3));
        assertEquals("5", merciInDeposito.get(0).get(4));
        assertEquals(StatoOrdine.IN_DEPOSITO.toString(), merciInDeposito.get(0).get(5));
    }

    @Test
    void getMerceOrdine() throws SQLException {
        Cliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio);
        int ID1 = Integer.parseInt(ordine1.get(0));
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(55.5, "Gonna", 10);
        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 5, ID1, negozio);
        int IDMerceOrdine1 = gestoreOrdini.getOrdine(Integer.parseInt(ordine1.get(0))).getMerci().get(0).getID();

        assertEquals(ID1, gestoreOrdini.getMerceOrdine(IDMerceOrdine1).getIDOrdine());
        assertEquals(55.5, gestoreOrdini.getMerceOrdine(IDMerceOrdine1).getPrezzo());
        assertEquals("Gonna", gestoreOrdini.getMerceOrdine(IDMerceOrdine1).getDescrizione());
        assertEquals(5, gestoreOrdini.getMerceOrdine(IDMerceOrdine1).getQuantita());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(IDMerceOrdine1).getStato());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.getMerceOrdine(5206));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    void getMerciFromNegozioToMagazzino() throws SQLException {
        Cliente cliente = new SimpleCliente("Maria", "Giuseppa");
        PuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 3");

        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio);
        int IDOrdine = Integer.parseInt(ordine.get(0));

        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(20, "Tastiera", 10);
        ArrayList<String> merceDettagli2 = negozio.inserisciNuovaMerce(14, "Mouse", 4);
        ArrayList<String> merceDettagli3 = negozio.inserisciNuovaMerce(6, "Chiavetta USB", 2);
        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        int IDMerce2 = Integer.parseInt(merceDettagli2.get(0));
        int IDMerce3 = Integer.parseInt(merceDettagli3.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 5, IDOrdine, negozio);
        gestoreOrdini.registraMerce(IDMerce2, 2, IDOrdine, negozio);
        gestoreOrdini.registraMerce(IDMerce3, 1, IDOrdine, negozio);
        gestoreOrdini.terminaOrdine(IDOrdine);
        gestoreOrdini.getOrdine(IDOrdine).setPuntoPrelievo(p.getID());

        ArrayList<ArrayList<String>> toTest = gestoreOrdini.getMerciFromNegozioToMagazzino(negozio.getID(), p.getID());
        assertFalse(toTest.isEmpty());
        assertEquals(String.valueOf(IDOrdine), toTest.get(0).get(1));
        assertEquals(String.valueOf(IDOrdine), toTest.get(1).get(1));
        assertEquals(String.valueOf(IDOrdine), toTest.get(2).get(1));
        assertEquals("20.0", toTest.get(0).get(2));
        assertEquals("14.0", toTest.get(1).get(2));
        assertEquals("6.0", toTest.get(2).get(2));
        assertEquals("Tastiera", toTest.get(0).get(3));
        assertEquals("Mouse", toTest.get(1).get(3));
        assertEquals("Chiavetta USB", toTest.get(2).get(3));
        assertEquals(StatoOrdine.PAGATO.toString(), toTest.get(0).get(5));
        assertEquals(StatoOrdine.PAGATO.toString(), toTest.get(1).get(5));
        assertEquals(StatoOrdine.PAGATO.toString(), toTest.get(2).get(5));
    }

    @Test
    void getMerciMagazzino() throws SQLException {
        Cliente cliente = new SimpleCliente("Maria", "Giuseppa");
        PuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 3");

        ArrayList<String> ordine = gestoreOrdini.registraOrdine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozio);
        int IDOrdine = Integer.parseInt(ordine.get(0));

        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(70, "Chitarra", 20);
        ArrayList<String> merceDettagli2 = negozio.inserisciNuovaMerce(14, "Custodia", 10);
        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        int IDMerce2 = Integer.parseInt(merceDettagli2.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 15, IDOrdine, negozio);
        gestoreOrdini.registraMerce(IDMerce2, 8, IDOrdine, negozio);
        gestoreOrdini.terminaOrdine(IDOrdine);
        gestoreOrdini.getOrdine(IDOrdine).setPuntoPrelievo(p.getID());

        ArrayList<ArrayList<String>> toTest = gestoreOrdini.getDettagliMerciMagazzino(p.getID());
        assertFalse(toTest.isEmpty());
        assertEquals(String.valueOf(IDOrdine), toTest.get(0).get(1));
        assertEquals(String.valueOf(IDOrdine), toTest.get(1).get(1));
        assertEquals("70.0", toTest.get(0).get(2));
        assertEquals("14.0", toTest.get(1).get(2));
        assertEquals("Chitarra", toTest.get(0).get(3));
        assertEquals("Custodia", toTest.get(1).get(3));
        assertEquals(StatoOrdine.PAGATO.toString(), toTest.get(0).get(5));
        assertEquals(StatoOrdine.PAGATO.toString(), toTest.get(1).get(5));
    }

    @Test
    void getOrdine() throws SQLException {
        Cliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio);
        int IDOrdine = Integer.parseInt(ordine1.get(0));
        PuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 4");
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(3.5, "Lampadina", 22);
        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 5, IDOrdine, negozio);
        gestoreOrdini.terminaOrdine(IDOrdine);
        int IDMerceOrdine1 = gestoreOrdini.getOrdine(IDOrdine).getMerci().get(0).getID();
        gestoreOrdini.getOrdine(IDOrdine).setPuntoPrelievo(p.getID());

        assertEquals(simpleCliente1.getID(), gestoreOrdini.getOrdine(IDOrdine).getIDCliente());
        assertEquals("Maria", gestoreOrdini.getOrdine(IDOrdine).getNomeCliente());
        assertEquals("Giuseppa", gestoreOrdini.getOrdine(IDOrdine).getCognomeCliente());
        assertEquals(17.0, gestoreOrdini.getOrdine(IDOrdine).getTotalePrezzo());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getOrdine(IDOrdine).getStato());
        assertEquals(p.getID(), gestoreOrdini.getOrdine(IDOrdine).getPuntoPrelievo());
        assertEquals(gestoreOrdini.getMerceOrdine(IDMerceOrdine1), gestoreOrdini.getOrdine(IDOrdine).getMerci().get(0));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.getOrdine(1000));
        assertEquals("ID ordine non valido.", e1.getMessage());

        Ordine ordine2 = new SimpleOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio.getID());
        Ordine ordineTest = gestoreOrdini.getOrdine(ordine2.getID());
        assertEquals(ordine2, ordineTest);
    }

    @Test
    void registraMerce() throws SQLException {
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(0.5, "Gomma", 10);
        ArrayList<String> merceDettagli2 = negozio.inserisciNuovaMerce(3, "Matita", 20);
        ArrayList<String> merceDettagli3 = negozio.inserisciNuovaMerce(4, "Pennello", 5);
        ArrayList<String> merceDettagli4 = negozio.inserisciNuovaMerce(15, "Maglietta", 0);

        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        int IDMerce2 = Integer.parseInt(merceDettagli2.get(0));
        int IDMerce3 = Integer.parseInt(merceDettagli3.get(0));
        int IDMerce4 = Integer.parseInt(merceDettagli4.get(0));

        Cliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");
        Cliente simpleCliente2 = new SimpleCliente("Giuseppe", "Rossi");

        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio);
        ArrayList<String> ordine2 = gestoreOrdini.registraOrdine(simpleCliente2.getID(), simpleCliente2.getNome(), simpleCliente2.getCognome(), negozio);

        int ID1 = Integer.parseInt(ordine1.get(0));
        int ID2 = Integer.parseInt(ordine2.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 10, ID1, negozio);
        gestoreOrdini.registraMerce(IDMerce2, 4, ID2, negozio);
        int IDMerceOrdine1 = gestoreOrdini.getOrdine(ID1).getMerci().get(0).getID();

        ordine1 = gestoreOrdini.getOrdine(ID1).getDettagli();
        ordine2 = gestoreOrdini.getOrdine(ID2).getDettagli();

        assertEquals(5.0, Double.parseDouble(ordine1.get(4)));
        assertTrue(negozio.getItems().contains(negozio.getItem(IDMerce1)));
        assertEquals("[ID=" + IDMerceOrdine1 + ", IDOrdine=" + ID1 + ", prezzo=0.5, descrizione='Gomma', quantita=10, stato=PAGATO]", ordine1.get(8));

        assertEquals(12.0, Double.parseDouble(ordine2.get(4)));
        assertTrue(negozio.getItems().contains(negozio.getItem(IDMerce2)));

        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraMerce(IDMerce3, 6, ID1, negozio));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraMerce(IDMerce4, 2, ID2, negozio));
    }

    @Test
    void registraOrdine() throws SQLException {
        Cliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordineDettagli = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio);

        assertEquals(simpleCliente.getID(), Integer.parseInt(ordineDettagli.get(1)));
        assertEquals("Maria", ordineDettagli.get(2));
        assertEquals("Giuseppa", ordineDettagli.get(3));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraOrdine(1000, "Matteo", "Rondini", negozio));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.getOrdine(1000));
    }

    @Test
    void selezionaMerce() throws SQLException {
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(7, "Astuccio", 10);
        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        Cliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");

        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio);
        int IDOrdine = Integer.parseInt(ordine1.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 5, IDOrdine, negozio);
        int IDMerceOrdine1 = gestoreOrdini.getOrdine(IDOrdine).getMerci().get(0).getID();

        ArrayList<String> dettagliMerce = gestoreOrdini.selezionaMerce(IDMerceOrdine1);

        assertEquals(IDOrdine, Integer.parseInt(dettagliMerce.get(1)));
        assertEquals("7.0", dettagliMerce.get(2));
        assertEquals("Astuccio", dettagliMerce.get(3));
        assertEquals("5", dettagliMerce.get(4));
        assertEquals("PAGATO", dettagliMerce.get(5));
    }

    @Test
    void setPuntoPrelievo() throws SQLException {
        Cliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio);
        PuntoPrelievo simplePuntoPrelievo = new SimplePuntoPrelievo("Roma", "Magazzino Centrale Lazio");
        assertEquals(-1, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getPuntoPrelievo());
        gestoreOrdini.setPuntoPrelievo(Integer.parseInt(ordine.get(0)), simplePuntoPrelievo.getID());
        assertEquals(simplePuntoPrelievo.getID(), gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getPuntoPrelievo());

    }

    @Test
    void setStatoMerce() throws SQLException {
        Cliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio);

        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(2.4, "T-Rex", 14);
        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 10, Integer.parseInt(ordine.get(0)), negozio);
        gestoreOrdini.terminaOrdine(Integer.parseInt(ordine.get(0)));

        int IDMerceOrdine = gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getMerci().get(0).getID();

        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(IDMerceOrdine).getStato());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
        gestoreOrdini.setStatoMerce(IDMerceOrdine, StatoOrdine.AFFIDATO_AL_CORRIERE);
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, gestoreOrdini.getMerceOrdine(IDMerceOrdine).getStato());
        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
    }

    @Test
    void setStatoOrdine() throws SQLException {
        Cliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio);
        assertEquals(StatoOrdine.DA_PAGARE, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
        gestoreOrdini.setStatoOrdine(Integer.parseInt(ordine.get(0)), StatoOrdine.IN_DEPOSITO);
        assertEquals(StatoOrdine.IN_DEPOSITO, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
    }

    @Test
    void terminaOrdine() throws SQLException {
        Merce simpleMerce = new SimpleMerce(negozio.getID(), 60.5, "Cuscino", 40);
        Merce simpleMerce1 = new SimpleMerce(negozio.getID(), 1, "Big Bubble", 80);
        Cliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio);

        gestoreOrdini.registraMerce(simpleMerce.getID(), 5, Integer.parseInt(ordine.get(0)), negozio);
        gestoreOrdini.registraMerce(simpleMerce1.getID(), 10, Integer.parseInt(ordine.get(0)), negozio);
        ordine = gestoreOrdini.terminaOrdine(Integer.parseInt(ordine.get(0)));
        assertSame(StatoOrdine.PAGATO.toString(), ordine.get(5));

        int IDMerceOrdine1 = gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getMerci().get(0).getID();
        int IDMerceOrdine2 = gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getMerci().get(1).getID();

        gestoreOrdini.getMerceOrdine(IDMerceOrdine1).setStato(StatoOrdine.DA_PAGARE);
        gestoreOrdini.getMerceOrdine(IDMerceOrdine2).setStato(StatoOrdine.DA_PAGARE);
        int idOrdine = Integer.parseInt(ordine.get(0));
        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.terminaOrdine(idOrdine));
    }

}
