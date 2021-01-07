package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class PuntoPrelievoTest {

    static PuntoPrelievo puntoPrelievo;
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
        puntoPrelievo = new PuntoPrelievo("Castelraimondo", "Stazione");
        negozioTest = new Negozio("Trinkets", "Cleptomania", null, null, "Via delle Trombette", null);
    }

    //    @Test
//    static void setMagazziniere() {
//        IMagazziniere magazziniere = new IMagazziniere(puntoPrelievo, "Alejandro", "Roberto");
//        puntoPrelievo.setMagazziniere(magazziniere);
//    }

    @Test
    void creazionePuntoPrelievo() throws SQLException {
        PuntoPrelievo puntoPrelievo = new PuntoPrelievo("Cingoli", "Magazzino Centrale");
        assertEquals("Cingoli", puntoPrelievo.getIndirizzo());
        assertEquals("Magazzino Centrale", puntoPrelievo.getNome());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> new PuntoPrelievo(1000));
        assertEquals("ID non valido.", e1.getMessage());

        PuntoPrelievo puntoPrelievoCopia = new PuntoPrelievo(puntoPrelievo.getID());
        assertEquals("Cingoli", puntoPrelievoCopia.getIndirizzo());
        assertEquals("Magazzino Centrale", puntoPrelievoCopia.getNome());
    }

    @Test
    void getDettagli() throws SQLException {
        ArrayList<String> dettagliMagazzino1 = new ArrayList<>();
        ArrayList<String> dettagliMagazzino2 = new ArrayList<>();

        dettagliMagazzino1.add(String.valueOf(puntoPrelievo.getID()));
        dettagliMagazzino1.add("Stazione");
        dettagliMagazzino1.add("Castelraimondo");
        assertEquals(dettagliMagazzino1, puntoPrelievo.getDettagli());

        PuntoPrelievo magazzino2 = new PuntoPrelievo("Matelica", "Giardini");
        assertNotEquals(dettagliMagazzino1, magazzino2.getDettagli());

        dettagliMagazzino2.add(String.valueOf(magazzino2.getID()));
        dettagliMagazzino2.add("Giardini");
        dettagliMagazzino2.add("Matelica");
        assertEquals(dettagliMagazzino2, magazzino2.getDettagli());
        assertNotEquals(dettagliMagazzino2, puntoPrelievo.getDettagli());
    }

    @Test
    void getDettagliMerceMagazzino() throws SQLException {
        Cliente cliente = new Cliente("Peter", "Parker");
        Ordine ordine = new Ordine(cliente.getID(), cliente.getNome(), cliente.getCognome(), negozioTest.getIDNegozio());
        MerceOrdine merce = new MerceOrdine(1, "Ragnatele in scatola", StatoOrdine.IN_DEPOSITO, ordine.getID());
        ordine.aggiungiMerce(merce, 1);
        ordine.setPuntoPrelievo(puntoPrelievo.getID());

        ArrayList<ArrayList<String>> test = puntoPrelievo.getDettagliMerceMagazzino(ordine.getID());
        assertEquals(test.get(0).get(0), String.valueOf(merce.getID()));
        assertEquals(test.get(0).get(1), String.valueOf(ordine.getID()));
        assertEquals(test.get(0).get(2), String.valueOf(merce.getPrezzo()));
        assertEquals(test.get(0).get(3), String.valueOf(merce.getDescrizione()));
        assertEquals(test.get(0).get(4), String.valueOf(merce.getQuantita()));
        assertEquals(test.get(0).get(5), String.valueOf(merce.getStato()));
    }

    @Test
    void getMerceMagazzino() throws SQLException {
        ArrayList<MerceOrdine> listaMerciOrdine1 = new ArrayList<>();
        ArrayList<MerceOrdine> listaMerciOrdine2 = new ArrayList<>();
        Cliente cliente1 = new Cliente("Mario", "Rossi");
        Cliente cliente2 = new Cliente("Mario", "Verdi");

        Ordine ordine1 = new Ordine(cliente1.getID(), cliente1.getNome(), cliente1.getCognome(), negozioTest.getIDNegozio());
        MerceOrdine merce1_1 = new MerceOrdine(10, "matita", StatoOrdine.IN_DEPOSITO, ordine1.getID());
        MerceOrdine merce2_1 = new MerceOrdine(20, "gomma", StatoOrdine.RITIRATO, ordine1.getID());

        Ordine ordine2 = new Ordine(cliente2.getID(), cliente2.getNome(), cliente2.getCognome(), negozioTest.getIDNegozio());
        MerceOrdine merce1_2 = new MerceOrdine(30, "maglietta", StatoOrdine.IN_TRANSITO, ordine2.getID());
        MerceOrdine merce2_2 = new MerceOrdine(40, "pantalone", StatoOrdine.AFFIDATO_AL_CORRIERE, ordine2.getID());

        listaMerciOrdine1.add(merce1_1);
        ordine1.aggiungiMerce(merce1_1, 2);
        assertEquals(listaMerciOrdine1, puntoPrelievo.getMerceMagazzino(ordine1.getID()));

        listaMerciOrdine1.add(merce2_1);
        ordine1.aggiungiMerce(merce2_1, 1);
        assertNotEquals(listaMerciOrdine1, puntoPrelievo.getMerceMagazzino(ordine1.getID()));

        listaMerciOrdine2.add(merce1_2);
        ordine2.aggiungiMerce(merce1_2, 1);
        assertNotEquals(listaMerciOrdine2, puntoPrelievo.getMerceMagazzino(ordine2.getID()));

        listaMerciOrdine2.add(merce2_2);
        ordine2.aggiungiMerce(merce2_2, 3);
        assertNotEquals(listaMerciOrdine2, puntoPrelievo.getMerceMagazzino(ordine2.getID()));

        assertTrue(puntoPrelievo.getMerceMagazzino(ordine2.getID()).isEmpty());
    }

    @Test
    void getMerceTotale() throws SQLException {
        ArrayList<MerceOrdine> listaMerciOrdine1 = new ArrayList<>();
        ArrayList<MerceOrdine> listaMerciOrdine2 = new ArrayList<>();
        Cliente cliente1 = new Cliente("Ambrose", "Spellman");
        Cliente cliente2 = new Cliente("Zelda", "Spellman");

        Ordine ordine1 = new Ordine(cliente1.getID(), cliente1.getNome(), cliente1.getCognome(), negozioTest.getIDNegozio());
        MerceOrdine merce1_1 = new MerceOrdine(10, "matita", StatoOrdine.IN_DEPOSITO, ordine1.getID());
        MerceOrdine merce2_1 = new MerceOrdine(20, "gomma", StatoOrdine.RITIRATO, ordine1.getID());

        Ordine ordine2 = new Ordine(cliente2.getID(), cliente2.getNome(), cliente2.getCognome(), negozioTest.getIDNegozio());
        MerceOrdine merce1_2 = new MerceOrdine(30, "maglietta", StatoOrdine.IN_TRANSITO, ordine2.getID());
        MerceOrdine merce2_2 = new MerceOrdine(40, "pantalone", StatoOrdine.AFFIDATO_AL_CORRIERE, ordine2.getID());

        listaMerciOrdine1.add(merce1_1);
        listaMerciOrdine1.add(merce2_1);
        ordine1.aggiungiMerce(merce1_1, 2);
        ordine1.aggiungiMerce(merce2_1, 4);
        assertEquals(listaMerciOrdine1, puntoPrelievo.getMerceTotale(ordine1.getID()));

        listaMerciOrdine2.add(merce1_2);
        listaMerciOrdine2.add(merce2_2);
        ordine2.aggiungiMerce(merce1_2, 2);
        ordine2.aggiungiMerce(merce2_2, 4);
        assertEquals(listaMerciOrdine2, puntoPrelievo.getMerceTotale(ordine2.getID()));
    }

    @Test
    void getOrdini() throws SQLException {
        ArrayList<Ordine> lista1 = new ArrayList<>();
        ArrayList<Ordine> lista2 = new ArrayList<>();
        Cliente cliente1 = new Cliente("Joel", "Barish");
        Cliente cliente2 = new Cliente("Clementine", "Kruczynski");

        assertTrue(puntoPrelievo.getOrdini(cliente1.getID()).isEmpty());
        assertTrue(puntoPrelievo.getOrdini(cliente2.getID()).isEmpty());

        Ordine ordine1 = new Ordine(cliente1.getID(), cliente1.getNome(), cliente1.getCognome(), negozioTest.getIDNegozio());
        ordine1.setPuntoPrelievo(puntoPrelievo.getID());
        ordine1.setStato(StatoOrdine.RITIRATO);
        lista1.add(ordine1);

        assertEquals(puntoPrelievo.getOrdini(cliente1.getID()).get(0), lista1.get(0));
        assertEquals(puntoPrelievo.getOrdini(cliente1.getID()), lista1);

        Ordine ordine2 = new Ordine(cliente2.getID(), cliente2.getNome(), cliente2.getCognome(), negozioTest.getIDNegozio());
        ordine2.setPuntoPrelievo(puntoPrelievo.getID());
        ordine2.setStato(StatoOrdine.RITIRATO);
        lista2.add(ordine2);

        assertNotEquals(puntoPrelievo.getOrdini(cliente2.getID()), lista1);
        assertEquals(puntoPrelievo.getOrdini(cliente2.getID()).get(0), lista2.get(0));
        assertEquals(puntoPrelievo.getOrdini(cliente2.getID()), lista2);
    }

    @Test
    void testEquals() throws SQLException {
        PuntoPrelievo puntoPrelievo = new PuntoPrelievo("Roma", "Magazzino Centrale Lazio");
        PuntoPrelievo puntoPrelievoCopia = new PuntoPrelievo(puntoPrelievo.getID());
        PuntoPrelievo puntoPrelievo2 = new PuntoPrelievo("Milano", "Magazzino Centrale Lombardia");
        assertEquals(puntoPrelievo, puntoPrelievoCopia);
        assertNotEquals(puntoPrelievo, puntoPrelievo2);
    }
}