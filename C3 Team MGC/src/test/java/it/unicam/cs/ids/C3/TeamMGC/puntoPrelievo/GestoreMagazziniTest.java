package it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo;

import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleNegozio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GestoreMagazziniTest {

    static final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();

    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("delete from sys.punti_prelievo;");
        updateData("alter table punti_prelievo AUTO_INCREMENT = 1;");
        updateData("INSERT INTO `sys`.`punti_prelievo` (`nome`, `indirizzo`) VALUES ('B1', 'via Giacinto');");
        updateData("INSERT INTO `sys`.`punti_prelievo` (`nome`, `indirizzo`) VALUES ('B2', 'via Giuseppe');");
        updateData("INSERT INTO `sys`.`punti_prelievo` (`nome`, `indirizzo`) VALUES ('B3', 'via Paolo');");
    }

    @Test
    void ricercaMagazziniVicini() throws SQLException {
        ArrayList<PuntoPrelievo> test = gestoreMagazzini.ricercaMagazziniVicini();
        assertEquals(test.get(0).getNome(), "B1");
        assertEquals(test.get(1).getIndirizzo(), "via Giuseppe");
        assertEquals(test.get(2).getNome(), "B3");
    }

    @Test
    void mandaAlert() throws SQLException {
        PuntoPrelievo puntoPrelievo = gestoreMagazzini.getItem(1);
        Negozio negozio = new SimpleNegozio("Trinkets", "Cleptomania", null, null, "Via delle Trombette", null);

        gestoreMagazzini.mandaAlert(puntoPrelievo.getID(), negozio);

        ResultSet rs = executeQuery("Select * from sys.alert_magazzinieri WHERE ID = 1;");
        rs.next();
        assertEquals(puntoPrelievo.getID(), rs.getInt("IDPuntoPrelievo"));
        assertEquals(1, rs.getInt("ID"));
        assertEquals("Mandare un corriere al negozio: Trinkets, indirizzo: Via delle Trombette, " +
                "per prelevare la merce.", rs.getString("messaggio"));
    }

    @Test
    void getDettagliMagazziniDisponibili() throws SQLException {
        ArrayList<ArrayList<String>> test = gestoreMagazzini.getDettagliItems();
        assertEquals(test.get(0).get(0), "1");
        assertEquals(test.get(0).get(1), "B1");
        assertEquals(test.get(0).get(2), "via Giacinto");
        assertEquals(test.get(1).get(0), "2");
        assertEquals(test.get(1).get(1), "B2");
        assertEquals(test.get(1).get(2), "via Giuseppe");
        assertEquals(test.get(2).get(0), "3");
        assertEquals(test.get(2).get(1), "B3");
        assertEquals(test.get(2).get(2), "via Paolo");
    }

    @Test
    void getMagazziniDisponibili() throws SQLException {
        ArrayList<PuntoPrelievo> test = gestoreMagazzini.getItems();
        assertEquals(test.get(0).getNome(), "B1");
        assertEquals(test.get(1).getIndirizzo(), "via Giuseppe");
        assertEquals(test.get(2).getNome(), "B3");
    }

    @Test
    void getPuntoPrelievo() throws SQLException {
        assertEquals("B1", gestoreMagazzini.getItem(1).getNome());
        assertEquals("via Giuseppe", gestoreMagazzini.getItem(2).getIndirizzo());
        assertEquals("via Paolo", gestoreMagazzini.getItem(3).getIndirizzo());
        assertThrows(IllegalArgumentException.class, () -> gestoreMagazzini.getItem(1000));
    }

    @Test
    void sceltaPuntoPrelievo() throws SQLException {
        ArrayList<String> prova = new ArrayList<>();
        prova.add("1");
        prova.add("B1");
        prova.add("via Giacinto");
        assertEquals(prova, gestoreMagazzini.sceltaPuntoPrelievo(1));
        assertEquals("B1", gestoreMagazzini.sceltaPuntoPrelievo(1).get(1));
        assertEquals("via Giuseppe", gestoreMagazzini.sceltaPuntoPrelievo(2).get(2));
        assertEquals("via Paolo", gestoreMagazzini.sceltaPuntoPrelievo(3).get(2));
    }

}