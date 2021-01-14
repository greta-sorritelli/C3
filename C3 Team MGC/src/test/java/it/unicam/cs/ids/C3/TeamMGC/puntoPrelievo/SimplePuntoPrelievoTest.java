package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleMerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class SimplePuntoPrelievoTest {

    static SimplePuntoPrelievo simplePuntoPrelievo;
    static Negozio negozioTest;

    @BeforeAll
    static void prepareDB() throws SQLException {
        updateData("delete from sys.ordini;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        updateData("delete from sys.merci;");
        updateData("alter table merci AUTO_INCREMENT = 1;");
        updateData("delete from sys.punti_prelievo;");
        updateData("alter table punti_prelievo AUTO_INCREMENT = 1;");
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
        simplePuntoPrelievo = new SimplePuntoPrelievo("Castelraimondo", "Stazione");
        negozioTest = new Negozio("Trinkets", "Cleptomania", null, null, "Via delle Trombette", null);
    }

    //    @Test
//    static void setMagazziniere() {
//        IMagazziniere magazziniere = new IMagazziniere(puntoPrelievo, "Alejandro", "Roberto");
//        puntoPrelievo.setMagazziniere(magazziniere);
//    }

    @Test
    void creazionePuntoPrelievo() throws SQLException {
        SimplePuntoPrelievo simplePuntoPrelievo = new SimplePuntoPrelievo("Cingoli", "Magazzino Centrale");
        assertEquals("Cingoli", simplePuntoPrelievo.getIndirizzo());
        assertEquals("Magazzino Centrale", simplePuntoPrelievo.getNome());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new SimplePuntoPrelievo(1000));
        assertEquals("ID non valido.", e1.getMessage());

        SimplePuntoPrelievo simplePuntoPrelievoCopia = new SimplePuntoPrelievo(simplePuntoPrelievo.getID());
        assertEquals("Cingoli", simplePuntoPrelievoCopia.getIndirizzo());
        assertEquals("Magazzino Centrale", simplePuntoPrelievoCopia.getNome());
    }

    @Test
    void update() throws SQLException {
        SimplePuntoPrelievo simplePuntoPrelievo = new SimplePuntoPrelievo("Love Museum", "Bugs Bunny");
        assertEquals(simplePuntoPrelievo.getIndirizzo(), "Love Museum");
        assertEquals(simplePuntoPrelievo.getNome(), "Bugs Bunny");
        updateData("UPDATE sys.punti_prelievo SET indirizzo = 'Love Court' where ID = '" + simplePuntoPrelievo.getID() + "';");
        assertEquals(simplePuntoPrelievo.getIndirizzo(), "Love Museum");
        assertEquals(simplePuntoPrelievo.getNome(), "Bugs Bunny");
        simplePuntoPrelievo.update();
        assertEquals(simplePuntoPrelievo.getIndirizzo(), "Love Court");
        assertEquals(simplePuntoPrelievo.getNome(), "Bugs Bunny");
    }

    @Test
    void getDettagli() throws SQLException {
        ArrayList<String> dettagliMagazzino1 = new ArrayList<>();
        ArrayList<String> dettagliMagazzino2 = new ArrayList<>();

        dettagliMagazzino1.add(String.valueOf(simplePuntoPrelievo.getID()));
        dettagliMagazzino1.add("Stazione");
        dettagliMagazzino1.add("Castelraimondo");
        assertEquals(dettagliMagazzino1, simplePuntoPrelievo.getDettagli());

        SimplePuntoPrelievo magazzino2 = new SimplePuntoPrelievo("Matelica", "Giardini");
        assertNotEquals(dettagliMagazzino1, magazzino2.getDettagli());

        dettagliMagazzino2.add(String.valueOf(magazzino2.getID()));
        dettagliMagazzino2.add("Giardini");
        dettagliMagazzino2.add("Matelica");
        assertEquals(dettagliMagazzino2, magazzino2.getDettagli());
        assertNotEquals(dettagliMagazzino2, simplePuntoPrelievo.getDettagli());
    }

    @Test
    void getMerceMagazzino() throws SQLException {
        ArrayList<SimpleMerceOrdine> listaMerciOrdine1 = new ArrayList<>();
        ArrayList<SimpleMerceOrdine> listaMerciOrdine2 = new ArrayList<>();
        SimpleCliente simpleCliente1 = new SimpleCliente("Mario", "Rossi");
        SimpleCliente simpleCliente2 = new SimpleCliente("Mario", "Verdi");

        SimpleOrdine simpleOrdine1 = new SimpleOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozioTest.getID());
        SimpleMerceOrdine merce1_1 = new SimpleMerceOrdine(10, "matita", StatoOrdine.IN_DEPOSITO, simpleOrdine1.getID());
        SimpleMerceOrdine merce2_1 = new SimpleMerceOrdine(20, "gomma", StatoOrdine.RITIRATO, simpleOrdine1.getID());

        SimpleOrdine simpleOrdine2 = new SimpleOrdine(simpleCliente2.getID(), simpleCliente2.getNome(), simpleCliente2.getCognome(), negozioTest.getID());
        SimpleMerceOrdine merce1_2 = new SimpleMerceOrdine(30, "maglietta", StatoOrdine.IN_TRANSITO, simpleOrdine2.getID());
        SimpleMerceOrdine merce2_2 = new SimpleMerceOrdine(40, "pantalone", StatoOrdine.AFFIDATO_AL_CORRIERE, simpleOrdine2.getID());

        listaMerciOrdine1.add(merce1_1);
        simpleOrdine1.aggiungiMerce(merce1_1, 2);
        assertEquals(listaMerciOrdine1, simplePuntoPrelievo.getMerceMagazzino(simpleOrdine1.getID()));

        listaMerciOrdine1.add(merce2_1);
        simpleOrdine1.aggiungiMerce(merce2_1, 1);
        assertNotEquals(listaMerciOrdine1, simplePuntoPrelievo.getMerceMagazzino(simpleOrdine1.getID()));

        listaMerciOrdine2.add(merce1_2);
        simpleOrdine2.aggiungiMerce(merce1_2, 1);
        assertNotEquals(listaMerciOrdine2, simplePuntoPrelievo.getMerceMagazzino(simpleOrdine2.getID()));

        listaMerciOrdine2.add(merce2_2);
        simpleOrdine2.aggiungiMerce(merce2_2, 3);
        assertNotEquals(listaMerciOrdine2, simplePuntoPrelievo.getMerceMagazzino(simpleOrdine2.getID()));

        assertTrue(simplePuntoPrelievo.getMerceMagazzino(simpleOrdine2.getID()).isEmpty());
    }

    @Test
    void getOrdini() throws SQLException {
        ArrayList<SimpleOrdine> lista1 = new ArrayList<>();
        ArrayList<SimpleOrdine> lista2 = new ArrayList<>();
        SimpleCliente simpleCliente1 = new SimpleCliente("Joel", "Barish");
        SimpleCliente simpleCliente2 = new SimpleCliente("Clementine", "Kruczynski");

        assertTrue(simplePuntoPrelievo.getOrdini(simpleCliente1.getID()).isEmpty());
        assertTrue(simplePuntoPrelievo.getOrdini(simpleCliente2.getID()).isEmpty());

        SimpleOrdine simpleOrdine1 = new SimpleOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozioTest.getID());
        simpleOrdine1.setPuntoPrelievo(simplePuntoPrelievo.getID());
        simpleOrdine1.setStato(StatoOrdine.IN_DEPOSITO);
        lista1.add(simpleOrdine1);

        assertEquals(simplePuntoPrelievo.getOrdini(simpleCliente1.getID()).get(0), lista1.get(0));
        assertEquals(simplePuntoPrelievo.getOrdini(simpleCliente1.getID()), lista1);

        SimpleOrdine simpleOrdine2 = new SimpleOrdine(simpleCliente2.getID(), simpleCliente2.getNome(), simpleCliente2.getCognome(), negozioTest.getID());
        simpleOrdine2.setPuntoPrelievo(simplePuntoPrelievo.getID());
        simpleOrdine2.setStato(StatoOrdine.IN_DEPOSITO);
        lista2.add(simpleOrdine2);

        assertNotEquals(simplePuntoPrelievo.getOrdini(simpleCliente2.getID()), lista1);
        assertEquals(simplePuntoPrelievo.getOrdini(simpleCliente2.getID()).get(0), lista2.get(0));
        assertEquals(simplePuntoPrelievo.getOrdini(simpleCliente2.getID()), lista2);
    }

    @Test
    void testEquals() throws SQLException {
        SimplePuntoPrelievo simplePuntoPrelievo = new SimplePuntoPrelievo("Roma", "Magazzino Centrale Lazio");
        SimplePuntoPrelievo simplePuntoPrelievoCopia = new SimplePuntoPrelievo(simplePuntoPrelievo.getID());
        SimplePuntoPrelievo simplePuntoPrelievo2 = new SimplePuntoPrelievo("Milano", "Magazzino Centrale Lombardia");
        assertEquals(simplePuntoPrelievo, simplePuntoPrelievoCopia);
        assertNotEquals(simplePuntoPrelievo, simplePuntoPrelievo2);
    }
}