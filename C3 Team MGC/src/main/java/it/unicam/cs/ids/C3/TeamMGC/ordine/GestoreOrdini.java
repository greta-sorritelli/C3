package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class GestoreOrdini {
    private final Negozio negozio;
    private final ArrayList<Ordine> ordini = new ArrayList<>();

    public GestoreOrdini(Negozio negozio) {
        this.negozio = negozio;
    }

    /**
     * Controlla se l' {@link Ordine} che si vuole creare e' gia' presente nella lista degli ordini. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return L'ordine
     * @throws SQLException
     */
    private Ordine addOrdine(ResultSet rs) throws SQLException {
        for (Ordine ordine : ordini)
            if (ordine.getID() == rs.getInt("ID"))
                return ordine;
        Ordine toReturn = new Ordine(rs.getInt("ID"));
        addOrdineToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link Ordine} alla lista di ordini.
     *
     * @param ordine Ordine da aggiungere
     * @return {@code true} se l'Ordine viene inserito correttamente, {@code false} altrimenti
     */
    private void addOrdineToList(Ordine ordine) {
        if (!ordini.contains(ordine))
             ordini.add(ordine);
    }

    /**
     * Aggiunge l'{@code indirizzo} della residenza all'{@link Ordine} preso tramite l' {@code ID}.
     *
     * @param IDOrdine    ID dell'ordine a cui aggiungere la residenza
     * @param indirizzo   Indirizzo della residenza da ggiungere all'ordine
     */
    public void addResidenza(int IDOrdine, String indirizzo) throws SQLException {
        getOrdine(IDOrdine).addResidenza(indirizzo);
    }

    /**
     * Controlla se il {@link Cliente} è già presente nel database.
     *
     * @param IDCliente ID del cliente
     * @param nome      Nome del cliente
     * @param cognome   Cognome del cliente
     * @throws IllegalArgumentException Se il cliente non è presente nel database
     */
    private void controllaCliente(int IDCliente, String nome, String cognome) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.clienti where ID = '" + IDCliente + "' and nome = '" + nome + "' and cognome = '" + cognome + "';");
        if (!rs.next())
            throw new IllegalArgumentException("ID non valido.");
    }

    /**
     * todo
     *
     * @param rs
     * @param ordine
     * @return
     * @throws SQLException
     */
    private MerceOrdine creaMerceFromDB(ResultSet rs, Ordine ordine) throws SQLException {
        MerceOrdine toReturn = new MerceOrdine(rs.getInt("ID"), rs.getInt("IDOrdine"),
                rs.getDouble("prezzo"), rs.getString("descrizione"),
                rs.getInt("quantita"), StatoOrdine.valueOf(rs.getString("stato")));
        ordine.addMerce(toReturn);
        return toReturn;
    }

    /**
     * Ritorna la lista dei dettagli delle {@link MerceOrdine Merci} con un certo stato presenti nel DB.
     *
     *  @param statoOrdine   Stato della merce
     * @return               ArrayList<ArrayList<String>> dei dettagli della merce.
     * @throws SQLException  Errore causato da una query SQL
     */
    //todo test
    public ArrayList<ArrayList<String>> getDettagliMerce(StatoOrdine statoOrdine) throws SQLException {
        ArrayList<MerceOrdine> merce = new ArrayList<>();
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.merci WHERE (`stato` =  '" + statoOrdine + "');");
        return getArrayLists(merce, dettagli, rs);
    }

    /**
     * Ritorna la lista dei dettagli di tutte le {@link MerceOrdine Merci} di un ordine presenti nel DB.
     *
     * @param IDOrdine       Id dell'ordine a cui appartiene la merce
     * @return               ArrayList<ArrayList<String>> dei dettagli di tutta la merce di un ordine
     * @throws SQLException  Errore causato da una query SQL
     */
    //todo test
    //tolto da visual paradigm
    public ArrayList<ArrayList<String>> getDettagliMerceTotale(int IDOrdine) throws SQLException {
        ArrayList<MerceOrdine> merce = new ArrayList<>();
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.merci WHERE (`IDOrdine` =  '" + IDOrdine + "');");
        return getArrayLists(merce, dettagli, rs);
    }

    /**
     *
     * @param merce
     * @param dettagli
     * @param rs
     * @return
     * @throws SQLException
     */
    //todo rename
    private ArrayList<ArrayList<String>> getArrayLists(ArrayList<MerceOrdine> merce, ArrayList<ArrayList<String>> dettagli, ResultSet rs) throws SQLException {
        while (rs.next()) {
            MerceOrdine tmp = new MerceOrdine(rs.getInt("ID"), rs.getInt("IDOrdine"),
                    rs.getDouble("prezzo"), rs.getString("descrizione"), rs.getInt("quantita"),
                    StatoOrdine.valueOf(rs.getString("stato")));
            merce.add(tmp);
        }
        for (MerceOrdine m : merce) {
            dettagli.add(m.getDettagli());
        }
        return dettagli;
    }

    /**
     * Ritorna la lista dei dettagli degli {@link Ordine Ordini} di un cliente presenti nel DB.
     *
     * @param IDCliente      Id del cliente a cui appartiene l'ordine
     * @return               ArrayList<String> dei dettagli dell'ordine
     * @throws SQLException  Errore causato da una query SQL
     */
    //todo test
    public ArrayList<String> getDettagliOrdineCliente(int IDCliente) throws SQLException {
        ResultSet rs = executeQuery("SELECT ID from ordini where IDCliente ='" + IDCliente + "' and stato = '" + StatoOrdine.IN_DEPOSITO + "';");
        if (rs.next())
            return getOrdine(rs.getInt("ID")).getDettagli();
        return null;
    }

    /**
     * Ritorna la lista dei dettagli dell' {@link Ordine } preso tramite l' {@code ID}.
     *
     * @param IDOrdine
     * @return
     * @throws SQLException
     */
    //todo test
    public ArrayList<String> getDettagliOrdine(int IDOrdine) throws SQLException {
        return getOrdine(IDOrdine).getDettagli();
    }

    /**
     * Ritorna la {@link MerceOrdine} collegata all' {@code ID}.
     *
     * @param ID Codice Identificativo della merce
     * @return La merce desiderata
     * @throws SQLException
     */
    //todo fare test
    public MerceOrdine getMerceOrdine(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * from merci where ID ='" + ID + "';");
        if (rs.next()) {
            Ordine ordine = getOrdine(rs.getInt("IDOrdine"));
            for (MerceOrdine merceOrdine : ordine.getMerci())
                if (merceOrdine.getID() == ID)
                    return merceOrdine;
                else
                    return creaMerceFromDB(rs, ordine);
        }
        return null;
    }

    /**
     * Ritorna l' {@link Ordine} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo dell'Ordine
     * @return L'ordine desiderato
     * @throws SQLException
     */
    //todo test
    public Ordine getOrdine(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.ordini where ID='" + ID + "' ;");
        if (rs.next())
            return addOrdine(rs);
        else
            throw new IllegalArgumentException("ID non valido.");
    }

    /**
     * Registra la {@link Merce} nell'{@link Ordine} creato.
     *
     * @param IDMerce  ID della merce
     * @param quantita Quantita della merce
     * @param IDOrdine Ordine in cui registrare la merce
     */
    public void registraMerce(int IDMerce, int quantita, int IDOrdine) throws SQLException {
        Merce merce = negozio.getMerce(IDMerce);
        if (merce.getQuantita() == 0 || merce.getQuantita() < quantita)
            throw new IllegalArgumentException();

        merce.setQuantita(merce.getQuantita() - quantita);
        MerceOrdine merceOrdine = new MerceOrdine(merce.getPrezzo(), merce.getDescrizione(), StatoOrdine.PAGATO, IDOrdine);
        getOrdine(IDOrdine).aggiungiMerce(merceOrdine, quantita);
    }

    /**
     * Registra l'{@link Ordine} con i dati del {@link Cliente} e cambia il suo {@code stato} in "PAGATO".
     *
     * @param IDCliente ID del cliente a cui appartiene l'ordine
     * @param nome      Nome del cliente a cui appartiene l'ordine
     * @param cognome   Cognome del cliente a cui appartiene l'ordine
     * @return L'ordine registrato
     */
    public ArrayList<String> registraOrdine(int IDCliente, String nome, String cognome) throws SQLException {
        controllaCliente(IDCliente, nome, cognome);
        Ordine ordine = new Ordine(IDCliente, nome, cognome, negozio.getIDNegozio());
        addOrdineToList(ordine);
        return ordine.getDettagli();
    }

    public void setPuntoPrelievo(int IDOrdine, int IDPuntoPrelievo) throws SQLException {
        getOrdine(IDOrdine).setPuntoPrelievo(IDPuntoPrelievo);
    }


    //todo controllare test
    public void setStatoMerce(int IDMerce, StatoOrdine statoOrdine) throws SQLException {
        getMerceOrdine(IDMerce).setStato(statoOrdine);
        Ordine ordine = getOrdine(getMerceOrdine(IDMerce).getIDOrdine());
        boolean toControl = true;
        for ( MerceOrdine m: ordine.getMerci()) {
            if(m.getStato() != statoOrdine)
                toControl = false;
        }
        if(toControl)
            ordine.setStato(statoOrdine);
    }

    public void setStatoOrdine(int IDOrdine, StatoOrdine statoOrdine) throws SQLException {
        getOrdine(IDOrdine).setStato(statoOrdine);
    }

    /**
     * Imposta lo stato dell'{@link Ordine} come pagato se tutta la {@link MerceOrdine} in esso è pagata.
     *
     * @param IDOrdine Ordine da controllare
     */
    public ArrayList<String> terminaOrdine(int IDOrdine) throws SQLException {
        ArrayList<MerceOrdine> mercePagata = new ArrayList<>();
        for (MerceOrdine m : getOrdine(IDOrdine).getMerci()) {
            if (m.getStato() == StatoOrdine.PAGATO)
                mercePagata.add(m);
        }
        if (mercePagata.equals(getOrdine(IDOrdine).getMerci()))
            getOrdine(IDOrdine).setStato(StatoOrdine.PAGATO);
        else
            throw new IllegalArgumentException("Merce non pagata.");

        return getOrdine(IDOrdine).getDettagli();
    }

    /**
     * @param capienza Capienza entro la quale deve rientrare la merce
     * @return ArrayList<ArrayList < String>> dei dettagli della merce con stato "PAGATO"
     * e che rientra nella capienza del corriere a cui deve essere assegnata.
     */
    //todo test
    public ArrayList<ArrayList<String>> visualizzaMerce(int capienza) throws SQLException {
        ArrayList<MerceOrdine> merce = new ArrayList<>();
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM sys.merci WHERE (`stato` =  '" + StatoOrdine.PAGATO + "' and quantita <= '" + capienza + "');");
        return getArrayLists(merce, dettagli, rs);
    }
}