package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class IMagazziniere {
    //todo essendo un' interfaccia forse il cerca cliente non deve creare un Cliente come oggetto ma deve
    //todo semplicemente visualizzare nella view le informazioni.
    public Cliente cercaCliente(int IDCliente) {
        try {
            ResultSet rs = executeQuery("SELECT * from clienti\n" +
                    "WHERE ID = " + IDCliente + ";");
            if (rs.next()) {
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String codice = rs.getString("codiceRitiro");
                String data = rs.getString("dataCreazione");
                return new Cliente(IDCliente, nome, cognome, codice, data);
            } else return null;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
            return null;
        }
    }

    public void mandaAlert() {
        // TODO - implement Magazziniere.mandaAlert
        throw new UnsupportedOperationException();
    }


    public boolean verificaCodice(int IDCliente, String codiceRitiro) {
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.codici_ritiro where codice = '" + codiceRitiro +
                    "' and IDCliente = '" + IDCliente + "';");
            return rs.next();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }
}