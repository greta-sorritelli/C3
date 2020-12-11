package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class IMagazziniere {

    public Cliente cercaCliente(int IDCliente) {
        try {
            ResultSet rs = executeQuery("SELECT * from clienti\n" +
                    "WHERE ID = " + IDCliente + ";");
            if (rs.next()) {
                String nome = rs.getString(2);
                String cognome = rs.getString(3);
                String codice = rs.getString(4);
                String data = rs.getString(5);
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


    public void verificaCodice(int codiceRitiro) {
    }
}