package it.unicam.cs.ids.C3.TeamMGC.ultimateDB;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

/**
 * Test per inserire valori di riferimento per poter utilizzare la GUI.
 */
public class DataDBTest {

    @BeforeAll
    static void clearDB() throws SQLException {
        updateData("delete from sys.clienti;");
        updateData("delete from sys.codici_ritiro;");
        updateData("delete from sys.corrieri;");
        updateData("delete from sys.inventario;");
        updateData("delete from sys.magazzinieri;");
        updateData("delete from sys.merci;");
        updateData("delete from sys.negozi;");
        updateData("delete from sys.ordini;");
        updateData("delete from personale;");
        updateData("delete from sys.punti_prelievo;");
        updateData("delete from alert_corrieri;");
        updateData("delete from alert_magazzinieri;");
        updateData("delete from alert_clienti;");
        updateData("delete from amministratore;");
        updateData("delete from promozioni;");

        updateData("alter table clienti AUTO_INCREMENT = 1;");
        updateData("alter table codici_ritiro AUTO_INCREMENT = 1;");
        updateData("alter table corrieri AUTO_INCREMENT = 1;");
        updateData("alter table inventario AUTO_INCREMENT = 1;");
        updateData("alter table magazzinieri AUTO_INCREMENT = 1;");
        updateData("alter table merci AUTO_INCREMENT = 1;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        updateData("alter table punti_prelievo AUTO_INCREMENT = 1;");
        updateData("alter table alert_clienti AUTO_INCREMENT = 1;");
        updateData("alter table alert_corrieri AUTO_INCREMENT = 1;");
        updateData("alter table alert_magazzinieri AUTO_INCREMENT = 1;");
        updateData("alter table personale AUTO_INCREMENT = 1;");
        updateData("alter table amministratore AUTO_INCREMENT = 1;");
        updateData("alter table promozioni AUTO_INCREMENT = 1;");
    }

    @Test
    void inserisciDatiDB() throws SQLException {
        //Amministratore
        updateData("INSERT INTO sys.amministratore (ID, nomeUtente, password) VALUES ('1', 'admin', '12345678');");

        //Clienti
        updateData("INSERT INTO sys.clienti (nome, cognome, codiceRitiro, dataCreazione, password) VALUES ('Mario', 'Rossi', '101010101010', '2021-01-01', '12345678');");

        //Negozi
        updateData("INSERT INTO sys.negozi (nome, categoria, orarioApertura, orarioChiusura, indirizzo, telefono) VALUES ('Sportland', 'SPORT', '09:00', '20:00', 'via Giuseppe Garibaldi 1', '0733 809070');");
        updateData("INSERT INTO sys.negozi (nome, categoria, orarioApertura, orarioChiusura, indirizzo, telefono) VALUES ('CiaoCiao', 'ABBIGLIAMENTO', '09:00', '20:00', 'via Giuseppe Garibaldi 3', '0733 589644');");
        updateData("INSERT INTO sys.negozi (nome, categoria, orarioApertura, orarioChiusura, indirizzo, telefono) VALUES ('BurroBirra', 'ALIMENTARI', '09:00', '20:00', 'via Giuseppe Garibaldi 4', '0733 784521');");

        //Punti di Prelievo
        updateData("INSERT INTO sys.punti_prelievo (nome, indirizzo) VALUES ('Magazzino Centrale', 'Camerino, via Madonna delle Carceri, 9');");
        updateData("INSERT INTO sys.punti_prelievo (nome, indirizzo) VALUES ('Magazzino Secondario', 'Camerino, via Madonna delle Carceri, 18');");

        //Corrieri
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato, password) VALUES ('Luigi', 'Bianchi', 'true', '12345678');");
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato, password) VALUES ('Maurizio', 'Mauri', 'true', '12345678');");

        //Ordini
        updateData("INSERT INTO sys.ordini (IDCliente, nomeCliente, cognomeCliente, totalePrezzo, stato, IDPuntoPrelievo, IDNegozio) VALUES ('1', 'Mario', 'Rossi', '0', 'PAGATO', '1', '1');");
        updateData("INSERT INTO sys.ordini (IDCliente, nomeCliente, cognomeCliente, totalePrezzo, stato, IDPuntoPrelievo, IDNegozio) VALUES ('1', 'Mario', 'Rossi', '0', 'PAGATO', '1', '1');");
        updateData("INSERT INTO sys.ordini (IDCliente, nomeCliente, cognomeCliente, totalePrezzo, stato, IDPuntoPrelievo, IDNegozio) VALUES ('1', 'Mario', 'Rossi', '0', 'IN_DEPOSITO', '2', '1');");
        updateData("INSERT INTO sys.ordini (IDCliente, nomeCliente, cognomeCliente, totalePrezzo, stato, IDPuntoPrelievo, IDNegozio) VALUES ('1', 'Mario', 'Rossi', '0', 'AFFIDATO_AL_CORRIERE', '2', '1');");
        updateData("INSERT INTO sys.ordini (IDCliente, nomeCliente, cognomeCliente, totalePrezzo, stato, IDPuntoPrelievo, IDNegozio) VALUES ('1', 'Mario', 'Rossi', '0', 'IN_TRANSITO', '2', '1');");
        updateData("INSERT INTO sys.ordini (IDCliente, nomeCliente, cognomeCliente, totalePrezzo, stato, IDPuntoPrelievo, IDNegozio) VALUES ('1', 'Mario', 'Rossi', '0', 'CORRIERE_SCELTO', '2', '1');");

        //Merci
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('1', '60.0', 'Felpa', '2', 'PAGATO');");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('1', '1.5', 'Matita', '3', 'PAGATO');");

        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('2', '2.0', 'Penna', '5', 'PAGATO');");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('2', '1.0', 'Gomma', '2', 'PAGATO');");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('2', '5.5', 'Astuccio', '1', 'PAGATO');");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('2', '8.0', 'Colla', '1', 'PAGATO');");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('2', '1.0', 'Quaderno', '10', 'PAGATO');");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('2', '4.5', 'Forbici', '1', 'PAGATO');");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('2', '59.0', 'Penna', '3', 'PAGATO');");

        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('3', '59.0', 'Penna', '3', 'IN_DEPOSITO');");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('3', '20.0', 'Matita', '10', 'IN_DEPOSITO');");

        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato, IDCorriere) VALUES ('4', '58.0', 'Pennino', '10', 'AFFIDATO_AL_CORRIERE', '1');");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato, IDCorriere) VALUES ('3', '5.0', 'Inchiostro', '5', 'AFFIDATO_AL_CORRIERE', '1');");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato, IDCorriere) VALUES ('2', '15.0', 'Evidenziatore', '3', 'AFFIDATO_AL_CORRIERE', '1');");

        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato, IDCorriere) VALUES ('5', '5.0', 'T-shirt', '2', 'IN_TRANSITO', 1);");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato, IDCorriere) VALUES ('5', '7.5', 'Pantaloni', '5', 'IN_TRANSITO', 1);");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato, IDCorriere) VALUES ('5', '10.0', 'Pallone', '1', 'IN_TRANSITO', 1);");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato, IDCorriere) VALUES ('5', '1.0', 'Casco', '4', 'IN_TRANSITO', 1);");

        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato, IDCorriere) VALUES ('6', '24.5', 'Mouse', '1', 'CORRIERE_SCELTO', 1);");
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato, IDCorriere) VALUES ('6', '30.0', 'Lampada', '2', 'CORRIERE_SCELTO', 1);");

        //Codici di Ritiro
        updateData("INSERT INTO sys.codici_ritiro (codice, IDCliente, IDOrdine, dataCreazione) VALUES ('101010101010', '1', '1', '2021-01-01');");

        //Inventario
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('1', '60.0', 'Felpa', '10');");
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('1', '45.0', 'Jeans', '10');");

        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('2', '15.0', 'Gonna', '5');");
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('2', '50.0', 'Camicia', '20');");

        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('2', '35.0', 'Cappotto', '10');");

        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('3', '50.0', 'Prosciutto crudo', '3');");
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('3', '5.0', 'Pizza', '20');");

        //Magazzinieri
        updateData("INSERT INTO sys.magazzinieri (IDPuntoPrelievo, nome, cognome, password) VALUES ('1', 'Silvio', 'Marzocco', '12345678');");

        //Commesso
        updateData("INSERT INTO sys.personale (IDNegozio, ruolo, nome, cognome, password) VALUES ('1', 'COMMESSO', 'Silvio', 'Marzocco', '12345678');");

        //Commerciante
        updateData("INSERT INTO sys.personale (IDNegozio, ruolo, nome, cognome, password) VALUES ('1', 'COMMERCIANTE', 'Giulia', 'Culmone', '12345678');");

        //Addetto
        updateData("INSERT INTO sys.personale (IDNegozio, ruolo, nome, cognome, password) VALUES ('1', 'ADDETTO_MAGAZZINO', 'Lucia', 'Tiezzi', '12345678');");

        //Promozioni
        updateData("INSERT INTO `sys`.`promozioni` (`IDNegozio`, `IDMerce`, `messaggio`, `prezzoAttuale`, `prezzoPrecedente`) VALUES ('1', '1', 'Sconto del 30%', '42', '60');");
        updateData("INSERT INTO `sys`.`promozioni` (`IDNegozio`, `IDMerce`, `messaggio`, `prezzoAttuale`, `prezzoPrecedente`) VALUES ('2', '3', '10 euro di sconto', '5', '15');");
    }
}
