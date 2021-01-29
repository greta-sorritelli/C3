package it.unicam.cs.ids.C3.TeamMGC.manager;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.personale.GestorePersonale;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;
import static org.junit.jupiter.api.Assertions.*;

class GestoreLoginTest {

    @BeforeAll
    static void prepareDB() throws SQLException {
        updateData("delete from sys.clienti;");
        updateData("delete from sys.corrieri;");
        updateData("delete from sys.magazzinieri;");
        updateData("delete from sys.negozi;");
        updateData("delete from sys.punti_prelievo;");
        updateData("delete from sys.personale;");
        updateData("delete from sys.amministratore;");

        updateData("alter table clienti AUTO_INCREMENT = 1;");
        updateData("alter table corrieri AUTO_INCREMENT = 1;");
        updateData("alter table magazzinieri AUTO_INCREMENT = 1;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        updateData("alter table punti_prelievo AUTO_INCREMENT = 1;");
        updateData("alter table personale AUTO_INCREMENT = 1;");
        updateData("alter table amministratore AUTO_INCREMENT = 1;");
    }

    @Test
    void checkInfoUtente() throws SQLException {
        //Cliente
        updateData("INSERT INTO sys.clienti (nome, cognome, codiceRitiro, dataCreazione, password) VALUES ('Mario', 'Rossi', '101010101010', '2021-01-01', '12345678');");
        GestoreClienti gestoreClienti = GestoreClienti.getInstance();
        assertTrue(gestoreClienti.checkInfo("CLIENTE", 1, "12345678"));
        assertFalse(gestoreClienti.checkInfo("CLIENTE", 1, "sdfghnjm"));

        //Corriere
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato, password) VALUES ('Luigi', 'Bianchi', 'true', '12345678');");
        GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
        assertTrue(gestoreCorrieri.checkInfo("CORRIERE", 1, "12345678"));
        assertFalse(gestoreCorrieri.checkInfo("CORRIERE", 1, "sdfghnjm"));

        //Magazziniere
        updateData("INSERT INTO sys.punti_prelievo (nome, indirizzo) VALUES ('Magazzino Centrale', 'Camerino, via Madonna delle Carceri, 9');");
        updateData("INSERT INTO sys.magazzinieri (IDPuntoPrelievo, nome, cognome, password) VALUES ('1', 'Silvio', 'Marzocco', '12345678');");
        GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
        assertTrue(gestoreMagazzini.checkInfo("MAGAZZINIERE", 1, "12345678"));
        assertFalse(gestoreMagazzini.checkInfo("MAGAZZINIERE", 1, "sdfghnjm"));

        assertThrows(IllegalStateException.class, () -> gestoreClienti.checkInfo("Cliente", 1 , "12345678"));
        assertThrows(IllegalStateException.class, () -> gestoreCorrieri.checkInfo("CUOCO", 1 , "12345678"));
        assertThrows(IllegalStateException.class, () -> gestoreMagazzini.checkInfo("Magazziniere", 1 , "12345678"));
    }

    @Test
    void checkInfoPersonale() throws SQLException {
        updateData("INSERT INTO sys.negozi (nome, categoria, orarioApertura, orarioChiusura, indirizzo, telefono) VALUES ('Sportland', 'SPORT', '09:00', '20:00', 'via Giuseppe Garibaldi 1', '0733 809070');");

        //Commesso
        updateData("INSERT INTO sys.personale (IDNegozio, ruolo, nome, cognome, password) VALUES ('1', 'COMMESSO', 'Silvio', 'Marzocco', '12345678');");
        GestorePersonale gestorePersonale = new GestorePersonale(1);
        assertTrue(gestorePersonale.checkInfo("COMMESSO", 1, "12345678", 1));
        assertFalse(gestorePersonale.checkInfo("COMMESSO", 1, "asdfghjk", 1));

        //Commerciante
        updateData("INSERT INTO sys.personale (IDNegozio, ruolo, nome, cognome, password) VALUES ('1', 'COMMERCIANTE', 'Giulia', 'Culmone', '12345678');");
        assertTrue(gestorePersonale.checkInfo("COMMERCIANTE", 1, "12345678", 1));
        assertFalse(gestorePersonale.checkInfo("COMMERCIANTE", 1, "asdfghjk", 1));

        //Addetto
        updateData("INSERT INTO sys.personale (IDNegozio, ruolo, nome, cognome, password) VALUES ('1', 'ADDETTO_MAGAZZINO', 'Lucia', 'Tiezzi', '12345678');");
        assertTrue(gestorePersonale.checkInfo("ADDETTO_MAGAZZINO", 1, "12345678", 1));
        assertFalse(gestorePersonale.checkInfo("ADDETTO_MAGAZZINO", 1, "asdfghjk", 1));

        assertThrows(IllegalStateException.class, () -> gestorePersonale.checkInfo("Cliente", 1 ,"12345678", 1));
    }

    @Test
    void checkInfoAmministratore() throws SQLException {
        updateData("INSERT INTO `sys`.`amministratore` (`ID`, `nomeUtente`, `password`) VALUES ('1', 'admin', '12345678');");
        assertTrue(GestoreLogin.checkInfo("admin", "12345678"));
        assertFalse(GestoreLogin.checkInfo("admin", "asdfghjk"));
    }
}