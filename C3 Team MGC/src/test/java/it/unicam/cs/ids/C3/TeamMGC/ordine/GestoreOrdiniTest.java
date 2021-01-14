package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleMerce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GestoreOrdiniTest {

    static final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();

    @Test
    @Order(1)
    void addResidenza() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
        SimpleCliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio );
        SimpleOrdine simpleOrdine1 = gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0)));
        assertEquals(ordine.size(), 8);
        gestoreOrdini.addResidenza(simpleOrdine1.getID(), "via Roma, 8");
        assertEquals(simpleOrdine1.getResidenza(), "via Roma, 8");
        gestoreOrdini.addResidenza(simpleOrdine1.getID(), "via Colombo, 9");
        ArrayList<String> ordineNew = simpleOrdine1.getDettagli();
        assertNotEquals(ordine, ordineNew);
        assertNotEquals(ordineNew.get(6), "via Roma, 8");
        assertEquals(ordineNew.get(6), "via Colombo, 9");
    }

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
    }

    @Test
    @Order(2)
    void selezionaMerce() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);;
        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);
        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        SimpleCliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");

        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio );
        int ID1 = Integer.parseInt(ordine1.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 5, ID1, negozio);

        assertEquals(ID1, Integer.parseInt(gestoreOrdini.selezionaMerce(IDMerce1).get(1)));
        assertEquals("52.0", gestoreOrdini.selezionaMerce(IDMerce1).get(2));
        assertEquals("gomma", gestoreOrdini.selezionaMerce(IDMerce1).get(3));
        assertEquals("5", gestoreOrdini.selezionaMerce(IDMerce1).get(4));
        assertEquals("PAGATO", gestoreOrdini.selezionaMerce(IDMerce1).get(5));
    }

    @Test
    void getDettagliMerce() throws SQLException {
        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);

        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);
        ArrayList<String> merceDettagli2 = negozio.inserisciNuovaMerce(10, "matita", 20);
        ArrayList<String> merceDettagli3 = negozio.inserisciNuovaMerce(20, "pennello", 5);

        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
        int IDMerce2 = Integer.parseInt(merceDettagli2.get(0));
        int IDMerce3 = Integer.parseInt(merceDettagli3.get(0));

        SimpleCliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");

        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(),negozio );
        int ID1 = Integer.parseInt(ordine1.get(0));

        gestoreOrdini.registraMerce(IDMerce1, 5, ID1, negozio );
        gestoreOrdini.registraMerce(IDMerce2, 10, ID1, negozio );
        gestoreOrdini.registraMerce(IDMerce3, 5, ID1, negozio );

        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(1).getStato());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(2).getStato());
        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(3).getStato());

        gestoreOrdini.setStatoMerce(1, StatoOrdine.AFFIDATO_AL_CORRIERE);
        gestoreOrdini.setStatoMerce(2, StatoOrdine.IN_TRANSITO);
        gestoreOrdini.setStatoMerce(3, StatoOrdine.RITIRATO);

        assertEquals("gomma", gestoreOrdini.getDettagliMerce(StatoOrdine.AFFIDATO_AL_CORRIERE).get(0).get(3));
        assertEquals("matita", gestoreOrdini.getDettagliMerce(StatoOrdine.IN_TRANSITO).get(0).get(3));
        assertEquals("pennello", gestoreOrdini.getDettagliMerce(StatoOrdine.RITIRATO).get(0).get(3));
        assertEquals("52.0", gestoreOrdini.getDettagliMerce(StatoOrdine.AFFIDATO_AL_CORRIERE).get(0).get(2));
        assertEquals("10.0", gestoreOrdini.getDettagliMerce(StatoOrdine.IN_TRANSITO).get(0).get(2));
        assertEquals("20.0", gestoreOrdini.getDettagliMerce(StatoOrdine.RITIRATO).get(0).get(2));
    }
//
//    @Test
//    void getDettagliOrdine() throws SQLException {
//        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
//        SimpleCliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");
//        SimplePuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 1");
//        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);
//
//        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
//        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(),negozio );
//        int ID1 = Integer.parseInt(ordine1.get(0));
//
//        gestoreOrdini.registraMerce(IDMerce1, 5, ID1, negozio);
//        gestoreOrdini.terminaOrdine(ID1);
//        gestoreOrdini.getOrdine(ID1).setPuntoPrelievo(p.getID());
//
//        ArrayList<String> ordineLista = new ArrayList<>();
//        ordineLista.add(ordine1.get(0));
//        ordineLista.add(String.valueOf(simpleCliente1.getID()));
//        ordineLista.add("Maria");
//        ordineLista.add("Giuseppa");
//        ordineLista.add("260.0");
//        ordineLista.add(StatoOrdine.PAGATO.toString());
//        ordineLista.add(String.valueOf(p.getID()));
//        ordineLista.add(gestoreOrdini.getOrdine(ID1).getMerci().toString());
//        assertEquals(ordineLista, gestoreOrdini.getDettagliOrdine(ID1));
//    }
//
//    @Test
//        //todo da finire
//    void getInDepositMerci() throws SQLException {
//        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
//        SimpleCliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");
//        SimplePuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 1");
//        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);
//
//        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
//        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio );
//        int ID1 = Integer.parseInt(ordine1.get(0));
//
//        gestoreOrdini.registraMerce(IDMerce1, 5, ID1, negozio );
//        gestoreOrdini.getOrdine(ID1).setPuntoPrelievo(p.getID());
//        gestoreOrdini.setStatoMerce(IDMerce1, StatoOrdine.IN_DEPOSITO);
//
//        ArrayList<SimpleOrdine> ordini = new ArrayList<>();
//        ordini.add(gestoreOrdini.getOrdine(ID1));
//
//        ArrayList<ArrayList<String>> merciInDeposito = gestoreOrdini.getInDepositMerci(ordini);
//
//    }
//
//    @Test
//    void getMerceOrdine() throws SQLException {
//        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
//        SimpleCliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");
//        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio);
//        int ID1 = Integer.parseInt(ordine1.get(0));
//        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);
//        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
//
//        gestoreOrdini.registraMerce(IDMerce1, 5, ID1, negozio);
//        assertEquals(ID1, gestoreOrdini.getMerceOrdine(IDMerce1).getIDOrdine());
//        assertEquals(52, gestoreOrdini.getMerceOrdine(IDMerce1).getPrezzo());
//        assertEquals("gomma", gestoreOrdini.getMerceOrdine(IDMerce1).getDescrizione());
//        assertEquals(5, gestoreOrdini.getMerceOrdine(IDMerce1).getQuantita());
//        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(IDMerce1).getStato());
//        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.getMerceOrdine(5206));
//        assertEquals("ID non valido.", e1.getMessage());
//
//
//    }
//
//    @Test
//    void getOrdine() throws SQLException {
//        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
//        SimpleCliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");
//        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio );
//        int ID1 = Integer.parseInt(ordine1.get(0));
//        SimplePuntoPrelievo p = new SimplePuntoPrelievo("Via dei Sequence Diagram", "SD 1");
//        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);
//        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
//
//        gestoreOrdini.registraMerce(IDMerce1, 5, ID1, negozio );
//        gestoreOrdini.terminaOrdine(ID1);
//        gestoreOrdini.getOrdine(ID1).setPuntoPrelievo(p.getID());
//
//        assertEquals(simpleCliente1.getID(), gestoreOrdini.getOrdine(ID1).getIDCliente());
//        assertEquals("Maria", gestoreOrdini.getOrdine(ID1).getNomeCliente());
//        assertEquals("Giuseppa", gestoreOrdini.getOrdine(ID1).getCognomeCliente());
//        assertEquals(260.0, gestoreOrdini.getOrdine(ID1).getTotalePrezzo());
//        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getOrdine(ID1).getStato());
//        assertEquals(p.getID(), gestoreOrdini.getOrdine(ID1).getPuntoPrelievo());
//        assertEquals(gestoreOrdini.getMerceOrdine(1), gestoreOrdini.getOrdine(ID1).getMerci().get(0));
//        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.getOrdine(1000));
//        assertEquals("ID ordine non valido.", e1.getMessage());
//    }
//
//    @Test
//    void registraMerce() throws SQLException {
//        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
//
//        ArrayList<String> merceDettagli1 = negozio.inserisciNuovaMerce(52, "gomma", 10);
//        ArrayList<String> merceDettagli2 = negozio.inserisciNuovaMerce(10, "matita", 20);
//        ArrayList<String> merceDettagli3 = negozio.inserisciNuovaMerce(20, "pennello", 5);
//        ArrayList<String> merceDettagli4 = negozio.inserisciNuovaMerce(15, "maglietta", 0);
//
//        int IDMerce1 = Integer.parseInt(merceDettagli1.get(0));
//        int IDMerce2 = Integer.parseInt(merceDettagli2.get(0));
//        int IDMerce3 = Integer.parseInt(merceDettagli3.get(0));
//        int IDMerce4 = Integer.parseInt(merceDettagli4.get(0));
//
//        SimpleCliente simpleCliente1 = new SimpleCliente("Maria", "Giuseppa");
//        SimpleCliente simpleCliente2 = new SimpleCliente("Giuseppe", "Rossi");
//
//        ArrayList<String> ordine1 = gestoreOrdini.registraOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(),negozio );
//        ArrayList<String> ordine2 = gestoreOrdini.registraOrdine(simpleCliente2.getID(), simpleCliente2.getNome(), simpleCliente2.getCognome(),negozio );
//
//        int ID1 = Integer.parseInt(ordine1.get(0));
//        int ID2 = Integer.parseInt(ordine2.get(0));
//
//        gestoreOrdini.registraMerce(IDMerce1, 10, Integer.parseInt(ordine1.get(0)), negozio);
//        gestoreOrdini.registraMerce(IDMerce2, 4, Integer.parseInt(ordine2.get(0)), negozio);
//
//        ordine1 = gestoreOrdini.getOrdine(ID1).getDettagli();
//        ordine2 = gestoreOrdini.getOrdine(ID2).getDettagli();
//
//        assertEquals(520.0, Double.parseDouble(ordine1.get(4)));
//        assertTrue(negozio.getMerceDisponibile().contains(negozio.getMerce(IDMerce1)));
//        assertEquals("[MerceOrdine{ID=1, IDOrdine=1, prezzo=52.0, descrizione='gomma', quantita=10, stato=PAGATO}]", ordine1.get(7));
//
//        assertEquals(40.0, Double.parseDouble(ordine2.get(4)));
//        assertTrue(negozio.getMerceDisponibile().contains(negozio.getMerce(IDMerce2)));
//
//        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraMerce(IDMerce3, 6, ID1, negozio));
//        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraMerce(IDMerce4, 2, ID2, negozio));
//    }
//
//    @Test
//    void registraOrdine() throws SQLException {
//        Negozio negozio = new Negozio("ABC", null, null, null, null, null);
//        SimpleCliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
//        ArrayList<String> ordineDettagli = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(),negozio );
//
//        assertEquals(simpleCliente.getID(), Integer.parseInt(ordineDettagli.get(1)));
//        assertEquals("Maria", ordineDettagli.get(2));
//        assertEquals("Giuseppa", ordineDettagli.get(3));
//        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.registraOrdine(1000, "Matteo", "Rondini",negozio ));
//        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.getOrdine(1000));
//    }
//
//    @Test
//    void setPuntoPrelievo() throws SQLException {
//        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
//        SimpleCliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
//        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio);
//        SimplePuntoPrelievo simplePuntoPrelievo = new SimplePuntoPrelievo("Roma", "Magazzino Centrale Lazio");
//        assertEquals(-1, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getPuntoPrelievo());
//        gestoreOrdini.setPuntoPrelievo(Integer.parseInt(ordine.get(0)), simplePuntoPrelievo.getID());
//        assertEquals(simplePuntoPrelievo.getID(), gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getPuntoPrelievo());
//
//    }
//
//    //todo CAMBIARE INFORMAZIONI DEGLI OGGETTI
//
//    @Test
//    void setStatoMerce() throws SQLException {
//        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
//        SimpleCliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
//        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio );
//
//        negozio.inserisciNuovaMerce(52, "gomma", 10);
//        gestoreOrdini.registraMerce(1, 10, Integer.parseInt(ordine.get(0)), negozio);
//        gestoreOrdini.terminaOrdine(Integer.parseInt(ordine.get(0)));
//
//        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getMerceOrdine(1).getStato());
//        assertEquals(StatoOrdine.PAGATO, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
//        gestoreOrdini.setStatoMerce(1, StatoOrdine.AFFIDATO_AL_CORRIERE);
//        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, gestoreOrdini.getMerceOrdine(1).getStato());
//        assertEquals(StatoOrdine.AFFIDATO_AL_CORRIERE, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
//    }
//
//    @Test
//    void setStatoOrdine() throws SQLException {
//        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
//        SimpleCliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
//        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(), negozio);
//        assertEquals(StatoOrdine.DA_PAGARE, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
//        gestoreOrdini.setStatoOrdine(Integer.parseInt(ordine.get(0)), StatoOrdine.IN_DEPOSITO);
//        assertEquals(StatoOrdine.IN_DEPOSITO, gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getStato());
//    }
//
//    @Test
//    void terminaOrdine() throws SQLException {
//        Negozio negozio = new Negozio("merceria", "oggettistica", null, null, "via roma", null);
//        SimpleMerce simpleMerce = new SimpleMerce(negozio.getID(), 52, "gomma", 10);
//        SimpleMerce simpleMerce1 = new SimpleMerce(negozio.getID(), 10, "matita", 20);
//        SimpleCliente simpleCliente = new SimpleCliente("Maria", "Giuseppa");
//        ArrayList<String> ordine = gestoreOrdini.registraOrdine(simpleCliente.getID(), simpleCliente.getNome(), simpleCliente.getCognome(),negozio);
//
//        gestoreOrdini.registraMerce(simpleMerce.getID(), 5, Integer.parseInt(ordine.get(0)), negozio);
//        gestoreOrdini.registraMerce(simpleMerce1.getID(), 10, Integer.parseInt(ordine.get(0)),negozio);
//        ordine = gestoreOrdini.terminaOrdine(Integer.parseInt(ordine.get(0)));
//        assertSame(StatoOrdine.PAGATO.toString(), ordine.get(5));
//        gestoreOrdini.getMerceOrdine(simpleMerce.getID()).setStato(StatoOrdine.DA_PAGARE);
//        gestoreOrdini.getOrdine(Integer.parseInt(ordine.get(0))).getMerci().get(0).setStato(StatoOrdine.DA_PAGARE);
//        int idOrdine = Integer.parseInt(ordine.get(0));
//        assertThrows(IllegalArgumentException.class, () -> gestoreOrdini.terminaOrdine(idOrdine));
//    }
}