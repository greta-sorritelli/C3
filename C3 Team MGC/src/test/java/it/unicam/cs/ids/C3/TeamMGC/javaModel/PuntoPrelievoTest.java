package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class PuntoPrelievoTest {

    static PuntoPrelievo puntoPrelievo;

    @BeforeEach
    void prepareDB() throws SQLException {
        updateData("TRUNCATE `sys`.`ordini`;");
        updateData("TRUNCATE `sys`.`merci`;");
        puntoPrelievo = new PuntoPrelievo("Castelraimondo", "Stazione");
        setMagazziniere();
    }

    @Test
    static void setMagazziniere() {
        IMagazziniere magazziniere = new IMagazziniere();
        puntoPrelievo.setMagazziniere(magazziniere);
    }

    @Test
    void getOrdini() {
        assertTrue(puntoPrelievo.getOrdini(1, 100).isEmpty());
        ArrayList<Ordine> lista1 = new ArrayList<>();
        ArrayList<Ordine> lista2 = new ArrayList<>();
        Ordine ordine1 = new Ordine(1, 1, "Mario", "Rossi", 100, StatoOrdine.RITIRATO , puntoPrelievo);
        lista1.add(ordine1);
        Ordine ordineTest1 = new Ordine(1, "Mario", "Rossi", 100, StatoOrdine.RITIRATO, puntoPrelievo);
        assertEquals(puntoPrelievo.getOrdini(1, 100).get(0), lista1.get(0));
        assertEquals(puntoPrelievo.getOrdini(1, 100), lista1);
        Ordine ordine2 = new Ordine(2, 1, "Mario", "Verdi", 100, StatoOrdine.RITIRATO , puntoPrelievo);
        lista2.add(ordine2);
        assertNotEquals(puntoPrelievo.getOrdini(2, 100), lista1);
        Ordine ordineTest2 = new Ordine(2, "Mario", "Verdi", 100, StatoOrdine.RITIRATO, puntoPrelievo);
        assertEquals(puntoPrelievo.getOrdini(2, 100), lista2);
    }

    @Test
    void getMerceMagazzino() {
        ArrayList<MerceOrdine> listaMerciOrdine1 = new ArrayList<>();
        ArrayList<MerceOrdine> listaMerciOrdine2 = new ArrayList<>();
        Ordine ordineTest1 = new Ordine(1, 1, "Mario", "Rossi", 100, StatoOrdine.IN_DEPOSITO , puntoPrelievo);
        Ordine ordineTest2 = new Ordine(2, 1, "Mario", "Verdi", 100, StatoOrdine.AFFIDATO_AL_CORRIERE , puntoPrelievo);
        Ordine ordine1 = new Ordine(1, "Mario", "Rossi", 100, StatoOrdine.IN_DEPOSITO , puntoPrelievo);
        Ordine ordine2 = new Ordine(1, "Mario", "Verdi", 100, StatoOrdine.AFFIDATO_AL_CORRIERE , puntoPrelievo);

        MerceOrdine merce1_1 = new MerceOrdine(1, 1, 10, "matita", 2, StatoOrdine.IN_DEPOSITO);
        MerceOrdine merce2_1 = new MerceOrdine(2,1, 20, "gomma", 1, StatoOrdine.RITIRATO);
        MerceOrdine merce1_2 = new MerceOrdine(3, 2, 30, "maglietta", 1, StatoOrdine.IN_TRANSITO);
        MerceOrdine merce2_2 = new MerceOrdine(4, 2, 40, "pantalone", 3, StatoOrdine.AFFIDATO_AL_CORRIERE);

        listaMerciOrdine1.add(merce1_1);
        MerceOrdine merce1_1Test = new MerceOrdine(10, "matita", StatoOrdine.IN_DEPOSITO);
        ordine1.aggiungiMerce(merce1_1Test,2);
        assertEquals(listaMerciOrdine1, puntoPrelievo.getMerceMagazzino(1));

        listaMerciOrdine1.add(merce2_1);
        MerceOrdine merce2_1Test = new MerceOrdine(20, "gomma", StatoOrdine.RITIRATO);
        ordine1.aggiungiMerce(merce2_1Test,1);
        assertNotEquals(listaMerciOrdine1, puntoPrelievo.getMerceMagazzino(1));

        listaMerciOrdine2.add(merce1_2);
        MerceOrdine merce1_2Test = new MerceOrdine(30, "maglietta", StatoOrdine.IN_TRANSITO);
        ordine2.aggiungiMerce(merce1_2Test, 1);
        assertNotEquals(listaMerciOrdine2, puntoPrelievo.getMerceMagazzino(2));

        listaMerciOrdine2.add(merce2_2);
        MerceOrdine merce2_2Test = new MerceOrdine(40, "pantalone", StatoOrdine.AFFIDATO_AL_CORRIERE);
        ordine2.aggiungiMerce(merce2_2Test, 3);
        assertNotEquals(listaMerciOrdine2, puntoPrelievo.getMerceMagazzino(2));
    }

    @Test
    void getMerceTotale() {
        ArrayList<MerceOrdine> listaMerciOrdine1 = new ArrayList<>();
        ArrayList<MerceOrdine> listaMerciOrdine2 = new ArrayList<>();
        Ordine ordineTest1 = new Ordine(1, 1, "Mario", "Rossi", 100, StatoOrdine.IN_DEPOSITO , puntoPrelievo);
        Ordine ordineTest2 = new Ordine(2, 1, "Mario", "Verdi", 100, StatoOrdine.AFFIDATO_AL_CORRIERE , puntoPrelievo);
        Ordine ordine1 = new Ordine(1, "Mario", "Rossi", 100, StatoOrdine.IN_DEPOSITO , puntoPrelievo);
        Ordine ordine2 = new Ordine(1, "Mario", "Verdi", 100, StatoOrdine.AFFIDATO_AL_CORRIERE , puntoPrelievo);

        MerceOrdine merce1_1 = new MerceOrdine(1, 1, 10, "matita", 2, StatoOrdine.IN_DEPOSITO);
        MerceOrdine merce2_1 = new MerceOrdine(2,1, 20, "gomma", 1, StatoOrdine.RITIRATO);
        MerceOrdine merce1_2 = new MerceOrdine(3, 2, 30, "maglietta", 1, StatoOrdine.IN_TRANSITO);
        MerceOrdine merce2_2 = new MerceOrdine(4, 2, 40, "pantalone", 3, StatoOrdine.AFFIDATO_AL_CORRIERE);

        listaMerciOrdine1.add(merce1_1);
        MerceOrdine merce1_1Test = new MerceOrdine(10, "matita", StatoOrdine.IN_DEPOSITO);
        ordine1.aggiungiMerce(merce1_1Test,2);
        assertEquals(listaMerciOrdine1, puntoPrelievo.getMerceTotale(1));

        listaMerciOrdine1.add(merce2_1);
        MerceOrdine merce2_1Test = new MerceOrdine(20, "gomma", StatoOrdine.RITIRATO);
        ordine1.aggiungiMerce(merce2_1Test,1);
        assertEquals(listaMerciOrdine1, puntoPrelievo.getMerceTotale(1));

        listaMerciOrdine2.add(merce1_2);
        MerceOrdine merce1_2Test = new MerceOrdine(30, "maglietta", StatoOrdine.IN_TRANSITO);
        ordine2.aggiungiMerce(merce1_2Test, 1);
        assertEquals(listaMerciOrdine2, puntoPrelievo.getMerceTotale(2));

        listaMerciOrdine2.add(merce2_2);
        MerceOrdine merce2_2Test = new MerceOrdine(40, "pantalone", StatoOrdine.AFFIDATO_AL_CORRIERE);
        ordine2.aggiungiMerce(merce2_2Test, 3);
        assertEquals(listaMerciOrdine2, puntoPrelievo.getMerceTotale(2));

        assertNotEquals(listaMerciOrdine1, listaMerciOrdine2);
    }

    @Test
    void getDettagli() {
        ArrayList<String> prova = new ArrayList<>();
        prova.add("Stazione");
        prova.add("Castelraimondo");
        assertEquals(prova, puntoPrelievo.getDettagli());
        PuntoPrelievo punto2 = new PuntoPrelievo("Matelica", "Giardini");
        assertNotEquals(prova, punto2.getDettagli());
        ArrayList<String> prova2 = new ArrayList<>();
        prova2.add("Giardini");
        prova2.add("Matelica");
        assertEquals(prova2, punto2.getDettagli());
        assertNotEquals(prova2, puntoPrelievo.getDettagli());
    }
}