package it.unicam.cs.ids.C3.TeamMGC.corriere;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleNegozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleOrdine;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GestoreCorrieriTest {

    static GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();

    @BeforeAll
    static void preparaDB() throws SQLException {
        updateData("DELETE from sys.corrieri;");
        updateData("DELETE from sys.alert_corrieri;");
        updateData("alter table corrieri AUTO_INCREMENT = 1;");
        updateData("alter table sys.alert_corrieri AUTO_INCREMENT = 1;");
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato) VALUES ('Clarissa', 'Albanese', 'true');");
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato) VALUES ('Matteo', 'Rondini', 'false');");
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato) VALUES ('Greta', 'Sorritelli', 'true');");
    }

    @AfterAll
    static void setDisponibilita() throws SQLException {
        gestoreCorrieri.setDisponibilita(1, false);
        gestoreCorrieri.setDisponibilita(3, true);
        assertFalse(gestoreCorrieri.getDisponibilita(1));
        assertTrue(gestoreCorrieri.getDisponibilita(3));
    }

    @Test
    void addCorriere() throws SQLException {
        ArrayList<String> dettagli = gestoreCorrieri.inserisciDati("Luigi", "Bianchi");

        assertEquals("Luigi", dettagli.get(1));
        assertEquals("Bianchi", dettagli.get(2));
        assertEquals("true", dettagli.get(3));
    }

    @Test
    @Order(2)
    void getCorriere() throws SQLException {
        assertEquals("Matteo", gestoreCorrieri.getItem(2).getNome());
        assertEquals("false", String.valueOf(gestoreCorrieri.getDisponibilita(2)));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> gestoreCorrieri.getItem(1000));
        assertEquals("ID non valido.", e1.getMessage());
    }

    @Test
    @Order(1)
    void getCorrieri() throws SQLException {
        ArrayList<Corriere> test = gestoreCorrieri.getItems();
        assertEquals(1, test.get(0).getID());
        assertEquals(2, test.get(1).getID());
        assertEquals(3, test.get(2).getID());
        assertEquals("Clarissa", test.get(0).getNome());
        assertEquals("Matteo", test.get(1).getNome());
        assertEquals("Greta", test.get(2).getNome());
    }

    @Test
    @Order(6)
    void getCorrieriDisponibili() throws SQLException {
        Corriere simpleCorriere = new SimpleCorriere("Matteo", "Rondini", false);

        gestoreCorrieri.getItems().get(0).setDisponibilita(true);
        gestoreCorrieri.getItems().get(2).setDisponibilita(true);

        ArrayList<Corriere> test = gestoreCorrieri.getCorrieriDisponibili();

        assertEquals(1, test.get(0).getID());
        assertEquals(3, test.get(1).getID());
        assertTrue(gestoreCorrieri.getDisponibilita(test.get(0).getID()));
        assertTrue(gestoreCorrieri.getDisponibilita(test.get(1).getID()));
        assertFalse(test.contains(simpleCorriere));

        gestoreCorrieri.setDisponibilita(test.get(0).getID(), false);
        gestoreCorrieri.setDisponibilita(test.get(1).getID(), false);

        Exception e1 = assertThrows(IllegalArgumentException.class, gestoreCorrieri::getCorrieriDisponibili);
        assertEquals("Corrieri disponibili non presenti.", e1.getMessage());
    }

    @Test
    @Order(4)
    void getDettagliCorrieri() throws SQLException {
        ArrayList<ArrayList<String>> test = gestoreCorrieri.getDettagliItems();
        assertEquals("Clarissa", test.get(0).get(1));
        assertEquals("Matteo", test.get(1).get(1));
        assertEquals("Greta", test.get(2).get(1));
        assertEquals("Albanese", test.get(0).get(2));
        assertEquals("Rondini", test.get(1).get(2));
        assertEquals("Sorritelli", test.get(2).get(2));
        assertEquals("true", test.get(0).get(3));
        assertEquals("false", test.get(1).get(3));
        assertEquals("true", test.get(2).get(3));
    }

    @Test
    @Order(5)
    void getDettagliCorrieriDisponibili() throws SQLException {
        ArrayList<ArrayList<String>> test = gestoreCorrieri.getDettagliCorrieriDisponibili();
        assertEquals("Clarissa", test.get(0).get(1));
        assertEquals("Albanese", test.get(0).get(2));
        assertEquals("true", test.get(0).get(3));
        assertEquals("Greta", test.get(1).get(1));
        assertEquals("Sorritelli", test.get(1).get(2));
        assertEquals("true", test.get(1).get(3));
        assertEquals(2, test.size());

        gestoreCorrieri.setDisponibilita(Integer.parseInt(test.get(0).get(0)), false);
        gestoreCorrieri.setDisponibilita(Integer.parseInt(test.get(1).get(0)), false);

        Exception e1 = assertThrows(IllegalArgumentException.class, gestoreCorrieri::getDettagliCorrieriDisponibili);
        assertEquals("Corrieri disponibili non presenti.", e1.getMessage());
    }

    @Test
    void inserisciDati() throws SQLException {
        ArrayList<String> test = gestoreCorrieri.inserisciDati("Giuseppe", "Bianchi");
        assertTrue(gestoreCorrieri.getItems().contains(gestoreCorrieri.getItem(Integer.parseInt(test.get(0)))));
        assertEquals("Giuseppe", gestoreCorrieri.getItem(Integer.parseInt(test.get(0))).getNome());
        assertEquals("Bianchi", gestoreCorrieri.getItem(Integer.parseInt(test.get(0))).getCognome());
    }

    @Test
    void mandaAlert() throws SQLException {
        ArrayList<ArrayList<String>> test = gestoreCorrieri.getDettagliCorrieriDisponibili();
        Negozio negozio = new SimpleNegozio("Trinkets", "Cleptomania", null, null, "Via delle Trombette", null);
        Negozio negozio1 = new SimpleNegozio("Sportland", "Sport", null, null, "Via delle Trombe", null);
        Negozio negozio2 = new SimpleNegozio("King", "Sport", null, null, "Via delle Cascate", null);
        ArrayList<Negozio> negozi = new ArrayList<>();
        negozi.add(negozio);
        negozi.add(negozio1);
        negozi.add(negozio2);

        Cliente simpleCliente1 = new SimpleCliente("Mario", "Rossi");
        Ordine simpleOrdine1 = new SimpleOrdine(simpleCliente1.getID(), simpleCliente1.getNome(), simpleCliente1.getCognome(), negozio.getID());

        simpleOrdine1.addResidenza("Via Giuseppe Verdi, 2");
        gestoreCorrieri.mandaAlert(Integer.parseInt(test.get(0).get(0)), negozio, simpleOrdine1.getResidenza());

        ResultSet rs = executeQuery("Select * from sys.alert_corrieri WHERE ID = 1;");
        rs.next();
        assertEquals(Integer.parseInt(test.get(0).get(0)), rs.getInt("IDCorriere"));
        assertEquals(1, rs.getInt("ID"));
        assertEquals("Andare al Negozio: Trinkets, indirizzo: Via delle Trombette, per ritirare le merci dei cliente alla residenza: Via " +
                "Giuseppe Verdi, 2.", rs.getString("messaggio"));

        gestoreCorrieri.mandaAlert(Integer.parseInt(test.get(0).get(0)), negozi);

        rs = executeQuery("Select * from sys.alert_corrieri WHERE ID = 2;");
        rs.next();
        assertEquals(Integer.parseInt(test.get(0).get(0)), rs.getInt("IDCorriere"));
        assertEquals(2, rs.getInt("ID"));
        assertEquals("Andare al Negozio: Trinkets, indirizzo: Via delle Trombette, per ritirare le merci dei clienti.", rs.getString("messaggio"));

        rs = executeQuery("Select * from sys.alert_corrieri WHERE ID = 3;");
        rs.next();
        assertEquals(Integer.parseInt(test.get(0).get(0)), rs.getInt("IDCorriere"));
        assertEquals(3, rs.getInt("ID"));
        assertEquals("Andare al Negozio: Sportland, indirizzo: Via delle Trombe, per ritirare le merci dei clienti.", rs.getString("messaggio"));

        rs = executeQuery("Select * from sys.alert_corrieri WHERE ID = 4;");
        rs.next();
        assertEquals(Integer.parseInt(test.get(0).get(0)), rs.getInt("IDCorriere"));
        assertEquals(4, rs.getInt("ID"));
        assertEquals("Andare al Negozio: King, indirizzo: Via delle Cascate, per ritirare le merci dei clienti.", rs.getString("messaggio"));

        disconnectToDB(rs);
    }
}