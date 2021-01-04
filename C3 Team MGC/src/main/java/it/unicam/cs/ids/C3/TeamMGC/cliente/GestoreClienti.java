package it.unicam.cs.ids.C3.TeamMGC.cliente;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class GestoreClienti implements Gestore<Cliente> {

    private final ArrayList<Cliente> clienti = new ArrayList<>();

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
                rs.getString("cognome"), rs.getString("codiceRitiro"), rs.getString("dataCreazione"));
        addClienteToList(toReturn);
        return toReturn;
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
     *
     * @param IDCliente
     * @param IDOrdine
     */
    //todo test
    private void creazioneCodice(int IDCliente, int IDOrdine) {
        try {
            updateData("INSERT INTO `sys`.`codici_ritiro` (`codice`,`IDCliente`,`IDOrdine`,`dataCreazione`) \n" +
                    "VALUES ('" + getItem(IDCliente).getCodiceRitiro() + "', '" + IDCliente + "', '" + IDOrdine + "', '" + getItem(IDCliente).getDataCreazioneCodice() + "');");
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    /**
     * Genera il nuovo {@code Codice di Ritiro} del {@link Cliente}.
     *
     * @return il Codice generato
     */
    private String generaCodiceRitiro() {
        Random rand = new Random();
        String tmp = "";
        for (int i = 0; i < 12; i++)
            tmp = tmp.concat(String.valueOf(rand.nextInt(10)));
        return tmp;
    }

    /**
     *
     */
    //todo test
    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() {
        try {
            ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * FROM sys.clienti;");
            while (rs.next())
                addCliente(rs);
            for (Cliente cliente : clienti)
                dettagli.add(cliente.getDettagli());
            return dettagli;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Ritorna il {@link Cliente} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Cliente
     * @return Il Cliente desiderato
     */
    //todo test
    @Override
    public Cliente getItem(int ID) {
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

    /**
     * @return ArrayList<Cliente> dei clienti.
     */
    @Override
    //todo test
    public ArrayList<Cliente> getItems() {
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.clienti;");
            while (rs.next())
                addCliente(rs);
            return new ArrayList<>(clienti);
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * @param nome
     * @param cognome
     * @return
     */
    //todo controllare
    public ArrayList<String> inserisciDati(String nome, String cognome) {
        Cliente cliente = new Cliente(nome, cognome);
        return cliente.getDettagli();
    }

    /**
     * @param IDCliente
     * @param IDOrdine
     * @return
     */
    public String verificaEsistenzaCodice(int IDCliente, int IDOrdine) {
        try {
            ResultSet rs = executeQuery("select dataCreazione from sys.clienti where ID = " + IDCliente + ";");
            if (rs.next()) {
                String date = rs.getString("dataCreazione");
                String dataOdierna = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
                if (Objects.isNull(date) || !date.equals(dataOdierna)) {
                    getItem(IDCliente).setCodiceRitiro(generaCodiceRitiro());
                    creazioneCodice(IDCliente, IDOrdine);
                    return getItem(IDCliente).getCodiceRitiro();
                }
                return getItem(IDCliente).getCodiceRitiro();
            }
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return getItem(IDCliente).getCodiceRitiro();
    }

    /**
     * @param IDCliente
     * @param codiceRitiro
     * @return
     */
    //todo test
    public boolean verificaCodice(int IDCliente, String codiceRitiro) {
      Cliente cliente = getItem(IDCliente);
      cliente.update();
      return verificaCodice(codiceRitiro,cliente.getCodiceRitiro());
    }

    /**
     *
     * @param codiceRitiroComunicato
     * @param codiceRitiroDB
     * @return
     */
    private boolean verificaCodice(String codiceRitiroComunicato, String codiceRitiroDB){
        return codiceRitiroComunicato.equals(codiceRitiroDB);
    }
}
