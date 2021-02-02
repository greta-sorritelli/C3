package it.unicam.cs.ids.C3.TeamMGC.cliente;

import it.unicam.cs.ids.C3.TeamMGC.manager.Gestore;
import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;
import it.unicam.cs.ids.C3.TeamMGC.manager.GestoreLogin;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.MerceOrdine;
import it.unicam.cs.ids.C3.TeamMGC.ordine.Ordine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la gestione di ogni {@link Cliente}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreClienti extends GestoreLogin implements Gestore<Cliente> {

    private static GestoreClienti gestoreClienti;
    private final ArrayList<Cliente> clienti = new ArrayList<>();

    private GestoreClienti() {
    }

    /**
     * Metodo per ottenere l' istanza singleton del {@link GestoreClienti}.
     *
     * @return l'unica istanza presente o una nuova se non è già esistente
     */
    public static GestoreClienti getInstance() {
        if (gestoreClienti == null)
            gestoreClienti = new GestoreClienti();
        return gestoreClienti;
    }

    /**
     * Controlla se il {@link Cliente} che si vuole creare e' gia' presente nella lista dei clienti.
     * Se non e' presente viene creato e aggiunto alla lista.
     *
     * @return Il Cliente
     *
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
     * Aggiunge un {@link Cliente} alla lista di clienti.
     *
     * @param cliente Cliente da aggiungere
     */
    private void addClienteToList(Cliente cliente) {
        if (!clienti.contains(cliente)) {
            clienti.add(cliente);
            Collections.sort(clienti);
        }
    }

    /**
     * Crea il nuovo {@code Codice di Ritiro} e lo associa all' {@link Ordine} ed al {@link Cliente} .
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
     * Ritorna il Codice di Ritiro collegato al {@link Cliente}.
     *
     * @param IDCliente Codice Identificativo del Cliente
     *
     * @return Il codice di ritiro del Cliente
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public String getCodiceRitiroCliente(int IDCliente) throws SQLException {
        return getItem(IDCliente).getCodiceRitiro();
    }

    /**
     * Ritorna il Cognome collegato al {@link Cliente}.
     *
     * @param IDCliente Codice Identificativo del Cliente
     *
     * @return Il cognome del Cliente
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public String getCognomeCliente(int IDCliente) throws SQLException {
        return getItem(IDCliente).getCognome();
    }

    /**
     * Ritorna la lista dei dettagli dei {@link Cliente Clienti} presenti nel DB.
     *
     * @return ArrayList di ArrayList dei dettagli dei Clienti.
     *
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
     * Svuota la lista dei {@link Cliente Clienti}.
     */
    @Override
    public void reset() {
        clienti.clear();
    }

    /**
     * Ritorna il {@link Cliente} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo del Cliente
     *
     * @return Il Cliente desiderato
     *
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
     * Ritorna la lista dei {@link Cliente Clienti} presenti nel DB.
     *
     * @return ArrayList dei Clienti.
     *
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
     * Ritorna il Nome collegato al {@link Cliente}.
     *
     * @param IDCliente Codice Identificativo del Cliente
     *
     * @return Il nome del Cliente
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public String getNomeCliente(int IDCliente) throws SQLException {
        return getItem(IDCliente).getNome();
    }

    /**
     * Crea un nuovo {@link Cliente} e ne salva le informazioni.
     *
     * @param nome     Nome del cliente da inserire
     * @param cognome  Cognome del cliente da inserire
     * @param password Password del cliente da inserire
     *
     * @return ArrayList dei dettagli del cliente creato
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> inserisciDati(String nome, String cognome, String password) throws SQLException {
        Cliente simpleCliente = new SimpleCliente(nome, cognome);
        updateData("UPDATE sys.clienti SET password = '" + password + "' WHERE (ID =" + simpleCliente.getID() + ");");
        addClienteToList(simpleCliente);
        return simpleCliente.getDettagli();
    }

    /**
     * Manda un alert al {@link Cliente} per avvisarlo che la {@link MerceOrdine} è arrivata al punto di prelievo.
     *
     * @param IDCliente     ID del cliente da avvisare
     * @param puntoPrelievo Punto di prelievo dove verrà portata la merce
     * @param merceOrdine   Merce arrivata al destinazione
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void mandaAlertPuntoPrelievo(int IDCliente, PuntoPrelievo puntoPrelievo, MerceOrdine merceOrdine) throws SQLException {
        updateData("INSERT INTO sys.alert_clienti (IDCliente, messaggio) VALUES ('" + IDCliente +
                "', 'Merce arrivata a destinazione. Andare al Punto di Prelievo: " + puntoPrelievo.getNome()
                + ", indirizzo: " + puntoPrelievo.getIndirizzo()
                + ", per ritirare la merce: " + merceOrdine.getDescrizione() + ".');");
    }

    /**
     * Manda un alert al {@link Cliente} per avvisarlo che non è stato trovato dal {@link Corriere}
     * nella sua residenza, e quindi la merce verrà consegnata al punto di prelievo.
     *
     * @param IDCliente     ID del cliente da avvisare
     * @param puntoPrelievo Punto di prelievo dove verrà portata la merce
     * @param merceOrdine   Merce da consegnare
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void mandaAlertResidenza(int IDCliente, PuntoPrelievo puntoPrelievo, MerceOrdine merceOrdine) throws SQLException {
        updateData("INSERT INTO sys.alert_clienti (IDCliente, messaggio) VALUES ('" + IDCliente +
                "', 'Cliente non trovato alla residenza. Andare al Punto di Prelievo: " + puntoPrelievo.getNome()
                + ", indirizzo: " + puntoPrelievo.getIndirizzo()
                + ", per ritirare la merce: " + merceOrdine.getDescrizione() + ".');");
    }

    /**
     * Verifica se il codice di ritiro è quello associato al {@link Cliente}
     *
     * @param IDCliente    Codice Identificativo del Cliente
     * @param codiceRitiro Codice di Ritiro comunicato dal Cliente
     *
     * @return true se il codice è giusto, false altrimenti
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public boolean verificaCodice(int IDCliente, String codiceRitiro) throws SQLException {
        Cliente simpleCliente = getItem(IDCliente);
        simpleCliente.update();
        return verificaCodice(codiceRitiro, simpleCliente.getCodiceRitiro());
    }

    /**
     * Verifica che il codice di ritiro comunicato dal {@link Cliente} sia uguale
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
     * Verifica il codice di ritiro del {@link Cliente}, cioè controlla se ha già un codice
     * creato nella data odierna. In caso negativo viene generato un nuovo codice per il cliente.
     *
     * @param IDCliente ID del Cliente a cui appartiene il codice di ritiro
     * @param IDOrdine  ID dell' Ordine del cliente
     *
     * @return Il codice di ritiro
     *
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
        throw new IllegalArgumentException("ID non valido.");
    }
}