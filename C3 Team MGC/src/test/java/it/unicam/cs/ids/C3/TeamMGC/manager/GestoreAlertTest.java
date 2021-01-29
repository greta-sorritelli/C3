package it.unicam.cs.ids.C3.TeamMGC.manager;

import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GestoreAlertTest {

    private GestoreAlert gestoreAlert = GestoreAlert.getInstance();

    @BeforeAll
    static void prepareDB() throws SQLException {
        //Clienti e alert Clienti
        updateData("delete from sys.alert_clienti;");
        updateData("alter table sys.alert_clienti AUTO_INCREMENT = 1;");
        updateData("delete from sys.clienti;");
        updateData("alter table clienti AUTO_INCREMENT = 1;");
        updateData("INSERT INTO sys.clienti (nome, cognome, codiceRitiro, dataCreazione, password) VALUES ('Mario', 'Rossi', '101010101010', '2021-01-01', '12345678');");

        //Punti prelievo e alert Magazzinieri
        updateData("delete from sys.alert_magazzinieri;");
        updateData("alter table alert_magazzinieri AUTO_INCREMENT = 1;");
        updateData("delete from sys.punti_prelievo;");
        updateData("alter table punti_prelievo AUTO_INCREMENT = 1;");
        updateData("INSERT INTO sys.punti_prelievo (nome, indirizzo) VALUES ('Magazzino Centrale', 'Camerino, via Madonna delle Carceri, 9');");

        //Corrieri e alert Corrieri
        updateData("delete from sys.alert_corrieri;");
        updateData("alter table alert_corrieri AUTO_INCREMENT = 1;");
        updateData("delete from sys.corrieri;");
        updateData("alter table corrieri AUTO_INCREMENT = 1;");
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato, password) VALUES ('Luigi', 'Bianchi', 'true', '12345678');");
    }

    @Test
    @Order(0)
    void getDettagliAlert() throws SQLException {
        updateData("INSERT INTO `sys`.`alert_clienti` (`ID`, `IDCliente`, `messaggio`) VALUES ('1', '1', 'Merce in arrivo!');");
        updateData("INSERT INTO `sys`.`alert_corrieri` (`ID`, `IDCorriere`, `messaggio`) VALUES ('1', '1', 'Merce da trasportare!');");
        updateData("INSERT INTO `sys`.`alert_magazzinieri` (`ID`, `IDPuntoPrelievo`, `messaggio`) VALUES ('1', '1', 'Merce in deposito!');");
        ArrayList<ArrayList<String>> tmp = gestoreAlert.getDettagliAlert(1, "CLIENTE");
        assertEquals("1", tmp.get(0).get(0));
        assertEquals("Merce in arrivo!", tmp.get(0).get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> tmp.get(1));
        ArrayList<ArrayList<String>> tmp2 = gestoreAlert.getDettagliAlert(1, "CORRIERE");
        assertEquals("1", tmp2.get(0).get(0));
        assertEquals("Merce da trasportare!", tmp2.get(0).get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> tmp2.get(1));
        ArrayList<ArrayList<String>> tmp3 = gestoreAlert.getDettagliAlert(1, "MAGAZZINIERE");
        assertEquals("1", tmp3.get(0).get(0));
        assertEquals("Merce in deposito!", tmp3.get(0).get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> tmp3.get(1));
    }

    @Test
    @Order(1)
    void deleteAlert() throws SQLException {
        ArrayList<ArrayList<String>> tmp = gestoreAlert.getDettagliAlert(1, "CLIENTE");
        ArrayList<ArrayList<String>> tmp2 = gestoreAlert.getDettagliAlert(1, "CORRIERE");
        ArrayList<ArrayList<String>> tmp3 = gestoreAlert.getDettagliAlert(1, "MAGAZZINIERE");
        assertFalse(tmp.isEmpty());
        assertFalse(tmp2.isEmpty());
        assertFalse(tmp3.isEmpty());
        assertEquals(1, tmp.size());
        assertEquals(1, tmp2.size());
        assertEquals(1, tmp3.size());
        gestoreAlert.deleteAlert(1, "CLIENTE");
        gestoreAlert.deleteAlert(1, "CORRIERE");
        gestoreAlert.deleteAlert(1, "MAGAZZINIERE");
        tmp = gestoreAlert.getDettagliAlert(1, "CLIENTE");
        tmp2 = gestoreAlert.getDettagliAlert(1, "CORRIERE");
        tmp3 = gestoreAlert.getDettagliAlert(1, "MAGAZZINIERE");
        assertEquals(0, tmp.size());
        assertEquals(0, tmp2.size());
        assertEquals(0, tmp3.size());
        assertTrue(tmp.isEmpty());
        assertTrue(tmp2.isEmpty());
        assertTrue(tmp3.isEmpty());
    }
}