package it.unicam.cs.ids.C3.TeamMGC.cliente;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class GestoreClienti implements Gestore<Cliente> {

    private final ArrayList<Cliente> clienti = new ArrayList<>();

    //todo
    public String generaCodiceRitiro() {

        return null;
    }

    /**
     * Aggiunge un {@link Cliente} alla lista di clienti.
     *
     * @param cliente Cliente da aggiungere
     * @return {@code true} se il Cliente viene inserito correttamente, {@code false} altrimenti
     */
    private boolean addClienteToList(Cliente cliente) {
        if (!clienti.contains(cliente))
            return clienti.add(cliente);
        else
            return false;
    }

    /**
     * Controlla se il {@link Cliente} che si vuole creare e' gia' presente nella lista dei clienti. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return Il Cliente
     * @throws SQLException
     */
    private Cliente addCliente(ResultSet rs) throws SQLException {
        for (Cliente cliente : clienti)
            if (cliente.getID() == rs.getInt("ID"))
                return cliente;
        Cliente toReturn = new Cliente(rs.getInt("ID"), rs.getString("nome"),
                rs.getString("cognome"), rs.getString("codiceRitiro"), rs.getString("dataCreazioneCodice"));
        addClienteToList(toReturn);
        return toReturn;
    }

    /**
     * Ritorna il {@link Cliente} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Cliente
     * @return Il Cliente desiderato
     */
    //todo test
    public Cliente getItem(int ID){
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.clienti where ID='" + ID + "' ;");
            if (rs.next())
                return addCliente(rs);
            else
                //todo eccezione
                throw new IllegalArgumentException();
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }
}
