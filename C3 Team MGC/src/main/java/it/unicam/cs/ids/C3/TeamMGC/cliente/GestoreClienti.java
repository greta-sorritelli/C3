package it.unicam.cs.ids.C3.TeamMGC.cliente;

import it.unicam.cs.ids.C3.TeamMGC.Gestore;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleMerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.SimpleOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la gestione di ogni {@link SimpleCliente}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreClienti implements Gestore<Cliente> {

    private static GestoreClienti gestoreClienti;
    private final ArrayList<Cliente> clienti = new ArrayList<>();

    private GestoreClienti() {
    }

    public static GestoreClienti getInstance() {
        if (gestoreClienti == null)
            gestoreClienti = new GestoreClienti();
        return gestoreClienti;
    }

    /**
     * Controlla se il {@link SimpleCliente} che si vuole creare e' gia' presente nella lista dei clienti. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return Il Cliente
     * @throws SQLException Errore causato da una query SQL
     */
    private Cliente addCliente(ResultSet rs) throws SQLException {
        for (Cliente simpleCliente : clienti)
            if (simpleCliente.getID() == rs.getInt("ID"))
                return simpleCliente;
        Cliente toReturn = new SimpleCliente(rs.getInt("ID"));
        addClienteToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link SimpleCliente} alla lista di clienti.
     *
     * @param simpleCliente Cliente da aggiungere
     */
    private void addClienteToList(Cliente simpleCliente) {
        if (!clienti.contains(simpleCliente))
            clienti.add(simpleCliente);
    }

    /**
     * Crea il nuovo {@code Codice di Ritiro} e lo associa all' {@link SimpleOrdine} ed al {@link SimpleCliente} .
     *
     * @param IDCliente Codice Identificativo del Cliente
     * @param IDOrdine  Codice Identificativo dell' Ordine
     *
     * @throws SQLException Errore causato da una query SQL
     */
    private void creazioneCodice(int IDCliente, int IDOrdine) throws SQLException {
        getItem(IDCliente).setCodiceRitiro(generaCodiceRitiro());
        updateData("INSERT INTO sys.codici_ritiro (codice, IDCliente, IDOrdine, dataCreazione) \n" +
                "VALUES ('" + getItem(IDCliente).getCodiceRitiro() + "', '" + IDCliente + "', '" + IDOrdine + "', '" + getItem(IDCliente).getDataCreazioneCodice() + "');");
    }

    /**
     * Genera il nuovo {@code Codice di Ritiro} del {@link SimpleCliente}.
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
     * Ritorna la lista dei dettagli dei {@link SimpleCliente Clienti} presenti nel DB.
     *
     * @return ArrayList<ArrayList < String>> dei dettagli dei Clienti.
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<ArrayList<String>> getDettagliItems() throws SQLException {
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.clienti;");
        while (rs.next())
            addCliente(rs);
        for (Cliente simpleCliente : clienti)
            dettagli.add(simpleCliente.getDettagli());
        disconnectToDB(rs);
        return dettagli;
    }

    /**
     * Ritorna il {@link SimpleCliente} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Cliente
     *
     * @return Il Cliente desiderato
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public Cliente getItem(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT ID FROM sys.clienti where ID='" + ID + "' ;");
        if (rs.next()) {
            return addCliente(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Ritorna la lista dei {@link SimpleCliente Clienti} presenti nel DB.
     *
     * @return ArrayList<Cliente> dei Clienti.
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<Cliente> getItems() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.clienti;");
        while (rs.next())
            addCliente(rs);
        disconnectToDB(rs);
        return new ArrayList<>(clienti);
    }

    /**
     * Crea e inserisce un nuovo {@link SimpleCliente} nella lista.
     *
     * @param nome    Nome del cliente da inserire
     * @param cognome Cognome del cliente da inserire
     *
     * @return ArrayList<String> dei dettagli del cliente creato
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> inserisciDati(String nome, String cognome) throws SQLException {
        Cliente simpleCliente = new SimpleCliente(nome, cognome);
        addClienteToList(simpleCliente);
        return simpleCliente.getDettagli();
    }

    /**
     * Verifica il codice di ritiro del cliente, cioè controlla se ha già un codice
     * creato nella data odierna. In caso negativo viene generato un nuovo codice per il cliente.
     *
     * @param IDCliente ID del Cliente a cui appartiene il codice di ritiro
     * @param IDOrdine  ID dell' Ordine del cliente
     *
     * @return Il codice di ritiro
     * @throws SQLException Errore causato da una query SQL
     */
    public String verificaEsistenzaCodice(int IDCliente, int IDOrdine) throws SQLException {
        ResultSet rs = executeQuery("select dataCreazione from sys.clienti where ID = " + IDCliente + ";");
        if (rs.next()) {
            String date = rs.getString("dataCreazione");
            String dataOdierna = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));
            if (Objects.isNull(date) || !date.equals(dataOdierna)) {
                creazioneCodice(IDCliente, IDOrdine);
                disconnectToDB(rs);
                return getItem(IDCliente).getCodiceRitiro();
            }
            disconnectToDB(rs);
            return getItem(IDCliente).getCodiceRitiro();
        }
        disconnectToDB(rs);
        return getItem(IDCliente).getCodiceRitiro();
    }

    /**
     * Verifica se il codice di ritiro è quello associato al {@link SimpleCliente}
     *
     * @param IDCliente    Codice Identificativo del Cliente
     * @param codiceRitiro Codice di Ritiro comunicato dal Cliente
     *
     * @return true se il codice è giusto, false altrimenti
     * @throws SQLException Errore causato da una query SQL
     */
    public boolean verificaCodice(int IDCliente, String codiceRitiro) throws SQLException {
        Cliente simpleCliente = getItem(IDCliente);
        simpleCliente.update();
        return verificaCodice(codiceRitiro, simpleCliente.getCodiceRitiro());
    }

    /**
     * Verifica che il codice di ritiro comunicato dal cliente sia uguale
     * a quello presente nel database.
     *
     * @param codiceRitiroComunicato Codice comunicato dal cliente
     * @param codiceRitiroDB         Codice presente nel DB
     *
     * @return true se il codice è giusto, false altrimenti
     */
    private boolean verificaCodice(String codiceRitiroComunicato, String codiceRitiroDB) {
        return codiceRitiroComunicato.equals(codiceRitiroDB);
    }

    /**
     * Manda un alert al cliente per avvisarlo che non è stato trovato dal corriere
     * nella sua residenza, e quindi la merce verrà consegnata al punto di prelievo.
     * @param IDCliente          ID del cliente da avvisare
     * @param puntoPrelievo      Punto di prelievo dove verrà portata la merce
     * @throws SQLException      Errore causato da una query SQL
     */
    public void mandaAlertResidenza(int IDCliente, SimplePuntoPrelievo puntoPrelievo, SimpleMerceOrdine merceOrdine) throws SQLException {
            updateData("INSERT INTO sys.alert_clienti (IDCliente, messaggio) VALUES ('" + IDCliente +
                    "', 'Cliente non trovato alla residenza. Andare al Punto di Prelievo: " + puntoPrelievo.getNome()
                    + ", indirizzo: " + puntoPrelievo.getIndirizzo()
                    + ", per ritirare la merce: " + merceOrdine.getDescrizione() + ".');");
    }

    /**
     * Manda un alert al cliente per avvisarlo che la merce è arrivata al punto di prelievo.
     * @param IDCliente          ID del cliente da avvisare
     * @param puntoPrelievo      Punto di prelievo dove verrà portata la merce
     * @throws SQLException      Errore causato da una query SQL
     */
    public void mandaAlertPuntoPrelievo(int IDCliente, SimplePuntoPrelievo puntoPrelievo, SimpleMerceOrdine merceOrdine) throws SQLException {
        updateData("INSERT INTO sys.alert_clienti (IDCliente, messaggio) VALUES ('" + IDCliente +
                "', 'Merce arrivata a destinazione. Andare al Punto di Prelievo: " + puntoPrelievo.getNome()
                + ", indirizzo: " + puntoPrelievo.getIndirizzo()
                + ", per ritirare la merce: " + merceOrdine.getDescrizione() + ".');");
    }
}
