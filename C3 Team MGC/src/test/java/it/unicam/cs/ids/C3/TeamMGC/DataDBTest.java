package it.unicam.cs.ids.C3.TeamMGC;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        updateData("delete from sys.punti_prelievo;");

        updateData("alter table clienti AUTO_INCREMENT = 1;");
        updateData("alter table codici_ritiro AUTO_INCREMENT = 1;");
        updateData("alter table corrieri AUTO_INCREMENT = 1;");
        updateData("alter table inventario AUTO_INCREMENT = 1;");
        updateData("alter table magazzinieri AUTO_INCREMENT = 1;");
        updateData("alter table merci AUTO_INCREMENT = 1;");
        updateData("alter table negozi AUTO_INCREMENT = 1;");
        updateData("alter table ordini AUTO_INCREMENT = 1;");
        updateData("alter table punti_prelievo AUTO_INCREMENT = 1;");
    }

    @Test
    void inserisciDatiDB() throws SQLException {
        //Clienti
        updateData("INSERT INTO sys.clienti (nome, cognome, codiceRitiro, dataCreazione) VALUES ('Mario', 'Rossi', '101010101010', '2021-01-01');");

        //Negozi
        updateData("INSERT INTO sys.negozi (nome, categoria, orarioApertura, orarioChiusura, indirizzo, telefono) VALUES ('Sportland', 'Sport', '09:00', '20:00', 'via Giuseppe Garibaldi 1', '0733 809070');");
        
        //Punti di Prelievo
        updateData("INSERT INTO sys.punti_prelievo (nome, indirizzo) VALUES ('Magazzino Centrale', 'Camerino, via Madonna delle Carceri, 9');");

        //Ordini
        updateData("INSERT INTO sys.ordini (IDCliente, nomeCliente, cognomeCliente, totalePrezzo, stato, IDPuntoPrelievo, IDNegozio) VALUES ('1', 'Mario', 'Rossi', '0', 'IN_DEPOSITO', '1', '1');");

        //Merci
        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('1', '60.0', 'Felpa', '2', 'IN_DEPOSITO');");

        updateData("INSERT INTO sys.merci (IDOrdine, prezzo, descrizione, quantita, stato) VALUES ('1', '59.0', 'Matita', '3', 'PAGATO');");

        //Codici di Ritiro
        updateData("INSERT INTO sys.codici_ritiro (codice, IDCliente, IDOrdine, dataCreazione) VALUES ('101010101010', '1', '1', '2021-01-01');");

        //Inventario
        updateData("INSERT INTO sys.inventario (IDNegozio, prezzo, descrizione, quantita) VALUES ('1', '60.0', 'Felpa', '10');");

        //Corrieri
        updateData("INSERT INTO sys.corrieri (nome, cognome, stato) VALUES ('Luigi', 'Bianchi', 'true');");

        //Magazzinieri
        updateData("INSERT INTO sys.magazzinieri (IDPuntoPrelievo, nome, cognome) VALUES ('1', 'Silvio', 'Marzocco');");

    }
}
