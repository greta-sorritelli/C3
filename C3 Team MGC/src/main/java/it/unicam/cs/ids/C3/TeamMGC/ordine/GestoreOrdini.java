package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleMerce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la gestione di ogni {@link SimpleOrdine}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreOrdini {

    private static GestoreOrdini gestoreOrdini;
    private final ArrayList<SimpleOrdine> ordini = new ArrayList<>();

    private GestoreOrdini() {
    }

    public static GestoreOrdini getInstance() {
        if (gestoreOrdini == null)
            gestoreOrdini = new GestoreOrdini();
        return gestoreOrdini;
    }

//    public GestoreOrdini(Negozio negozio) {
//        this.negozio = negozio;
//    }

    /**
     * Controlla se l' {@link SimpleOrdine} che si vuole creare e' gia' presente nella lista degli ordini. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return L'ordine
     * @throws SQLException Errore causato da una query SQL
     */
    private SimpleOrdine addOrdine(ResultSet rs) throws SQLException {
        for (SimpleOrdine simpleOrdine : ordini)
            if (simpleOrdine.getID() == rs.getInt("ID"))
                return simpleOrdine;
        SimpleOrdine toReturn = new SimpleOrdine(rs.getInt("ID"));
        addOrdineToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link SimpleOrdine} alla lista di ordini.
     *
     * @param simpleOrdine Ordine da aggiungere
     */
    private void addOrdineToList(SimpleOrdine simpleOrdine) {
        if (!ordini.contains(simpleOrdine))
            ordini.add(simpleOrdine);
    }

    /**
     * Aggiunge l'{@code indirizzo} della residenza all'{@link SimpleOrdine} preso tramite l' {@code ID}.
     *
     * @param IDOrdine  ID dell' ordine a cui aggiungere la residenza
     * @param indirizzo Indirizzo della residenza da aggiungere all' ordine
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void addResidenza(int IDOrdine, String indirizzo) throws SQLException {
        getOrdine(IDOrdine).addResidenza(indirizzo);
    }

    //todo test
    public void associaMerceCorriere(int IDCorriere, int IDMerce) throws SQLException {
        updateData("UPDATE sys.merci SET IDCorriere = '" + IDCorriere + "' WHERE (ID = '" + IDMerce + "');");
        getMerceOrdine(IDMerce).setIDCorriere(IDCorriere);
    }

    /**
     * Controlla se il {@link SimpleCliente} è già presente nel database.
     *
     * @param IDCliente ID del cliente
     * @param nome      Nome del cliente
     * @param cognome   Cognome del cliente
     *
     * @throws IllegalArgumentException Se il cliente non è presente nel database
     * @throws SQLException             Errore causato da una query SQL
     */
    private void controllaCliente(int IDCliente, String nome, String cognome) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.clienti where ID = '" + IDCliente + "' and nome = '" + nome + "' and cognome = '" + cognome + "';");
        if (!rs.next()) {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
        disconnectToDB(rs);
    }

    /**
     * Crea una {@link SimpleMerceOrdine} e la aggiunge ad un {@link SimpleOrdine}.
     *
     * @param rs           ResultSet contenente la query per creare la Merce
     * @param simpleOrdine Ordine a cui aggiungere la Merce
     *
     * @return la MerceOrdine creata
     * @throws SQLException Errore causato da una query SQL
     */
    //todo forse non serve piu'
    private SimpleMerceOrdine creaMerceFromDB(ResultSet rs, SimpleOrdine simpleOrdine) throws SQLException {
        SimpleMerceOrdine toReturn = new SimpleMerceOrdine(rs.getInt("ID"));
        simpleOrdine.addMerce(toReturn);
        return toReturn;
    }

    /**
     * Ritorna la lista dei dettagli della {@link SimpleMerceOrdine};
     *
     * @param merce    Lista della merceOrdine
     * @param dettagli Lista dei dettagli
     * @param rs       ResultSet
     *
     * @return ArrayList<ArrayList < String>> dei dettagli della merceOrdine
     * @throws SQLException Errore causato da una query SQL
     */
    private ArrayList<ArrayList<String>> getArrayListDettagliMerce(ArrayList<SimpleMerceOrdine> merce, ArrayList<ArrayList<String>> dettagli, ResultSet rs) throws SQLException {
        while (rs.next()) {
            SimpleMerceOrdine tmp = new SimpleMerceOrdine(rs.getInt("ID"));
            merce.add(tmp);
        }
        for (SimpleMerceOrdine m : merce)
            dettagli.add(m.getDettagli());
        return dettagli;
    }

    /**
     * Ritorna la lista dei dettagli delle {@link SimpleMerceOrdine Merci} con un certo stato presenti nel DB.
     *
     * @param statoOrdine Stato della merce
     *
     * @return ArrayList<ArrayList < String>> dei dettagli della merce.
     * @throws SQLException Errore causato da una query SQL
     */
    //todo rivedere test
    public ArrayList<ArrayList<String>> getDettagliMerce(StatoOrdine statoOrdine) throws SQLException {
        update();
        ArrayList<SimpleMerceOrdine> merci = new ArrayList<>();

        for (SimpleOrdine simpleOrdine : ordini) {
            simpleOrdine.update();
            merci.addAll(simpleOrdine.getMerci());
        }
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();

        for (SimpleMerceOrdine simpleMerceOrdine : merci)
            if (simpleMerceOrdine.getStato().equals(statoOrdine))
                dettagli.add(simpleMerceOrdine.getDettagli());

        return dettagli;

//        ResultSet rs = executeQuery("SELECT * FROM sys.merci WHERE (stato =  '" + statoOrdine + "');");
//        return getArrayListDettagliMerce(merci, dettagli, rs);
    }

    /**
     * Ritorna la lista dei dettagli dell' {@link SimpleOrdine } preso tramite l' {@code ID}.
     *
     * @param IDOrdine Codice Identificativo dell' Ordine
     *
     * @return un ArrayList di String contenente i dettagli dell' Ordine
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> getDettagliOrdine(int IDOrdine) throws SQLException {
        return getOrdine(IDOrdine).getDettagli();
    }

    /**
     * Ritorna la lista dei dettagli degli {@link SimpleOrdine Ordini} di un cliente presenti nel DB.
     *
     * @param IDCliente Id del cliente a cui appartiene l' ordine
     *
     * @return un ArrayList di String contenente i  dell' ordine
     * @throws SQLException Errore causato da una query SQL
     */
    //todo test e forse levare
    public ArrayList<String> getDettagliOrdineCliente(int IDCliente) throws SQLException {
        ResultSet rs = executeQuery("SELECT ID from ordini where IDCliente ='" + IDCliente + "' and stato = '" + StatoOrdine.IN_DEPOSITO + "';");
        if (rs.next()) {
            ArrayList<String> tmp = getOrdine(rs.getInt("ID")).getDettagli();
            disconnectToDB(rs);
            return tmp;
        }
        return null;
    }

    /**
     * Ritorna la lista dei dettagli di tutte le {@link SimpleMerceOrdine Merci} con stato
     * "in deposito" di un insieme di {@link SimpleOrdine Ordini} .
     *
     * @param ordini Insieme di ordini
     *
     * @return ArrayList<ArrayList < String>> dei dettagli delle merci
     * @throws SQLException Errore causato da una query SQL
     */
    //todo finire test
    public ArrayList<ArrayList<String>> getInDepositMerci(ArrayList<SimpleOrdine> ordini) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        for (SimpleOrdine simpleOrdine : ordini) {
            for (SimpleMerceOrdine m : simpleOrdine.getMerci())
                if (m.getStato() == StatoOrdine.IN_DEPOSITO)
                    toReturn.add(m.getDettagli());
        }
        return toReturn;
    }

    /**
     * Ritorna la {@link SimpleMerceOrdine} collegata all' {@code ID}.
     *
     * @param ID Codice Identificativo della merce
     *
     * @return La merce desiderata
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleMerceOrdine getMerceOrdine(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * from merci where ID ='" + ID + "';");
        if (rs.next()) {
            SimpleOrdine simpleOrdine = getOrdine(rs.getInt("IDOrdine"));
            for (SimpleMerceOrdine simpleMerceOrdine : simpleOrdine.getMerci())
                if (simpleMerceOrdine.getID() == ID) {
                    disconnectToDB(rs);
                    return simpleMerceOrdine;
                } else {
                    SimpleMerceOrdine tmp = creaMerceFromDB(rs, simpleOrdine);
                    disconnectToDB(rs);
                    return tmp;
                }
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
        return null;
    }

    //todo test
    public ArrayList<ArrayList<String>> getMerciFromNegozioToMagazzino(int IDNegozio, int IDPuntoPrelievo) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        ResultSet rs = executeQuery("select ID from ordini where IDNegozio = '" + IDNegozio + "' and IDPuntoPrelievo ='"
                + IDPuntoPrelievo + "';");
        while (rs.next())
            addOrdine(rs);
        disconnectToDB(rs);

        for (SimpleOrdine simpleOrdine : ordini)
            if (simpleOrdine.getIDNegozio() == IDNegozio && simpleOrdine.getPuntoPrelievo() == IDPuntoPrelievo)
                for (SimpleMerceOrdine simpleMerceOrdine : simpleOrdine.getMerci())
                    if (simpleMerceOrdine.getStato() == StatoOrdine.PAGATO)
                        toReturn.add(simpleMerceOrdine.getDettagli());
        return toReturn;
    }

    //todo test
    public ArrayList<ArrayList<String>> getMerciMagazzino(int IDPuntoPrelievo) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        ResultSet rs = executeQuery("select ID from ordini where IDPuntoPrelievo = '" + IDPuntoPrelievo + "';");
        while (rs.next())
            addOrdine(rs);
        disconnectToDB(rs);

        for (SimpleOrdine simpleOrdine : ordini)
            if (simpleOrdine.getPuntoPrelievo() == IDPuntoPrelievo)
                for (SimpleMerceOrdine simpleMerceOrdine : simpleOrdine.getMerci())
                    if (simpleMerceOrdine.getStato() == StatoOrdine.PAGATO)
                        toReturn.add(simpleMerceOrdine.getDettagli());
        return toReturn;
    }

    //todo test
    public ArrayList<ArrayList<String>> getDettagliMerciOfCorriere(int IDCorriere, StatoOrdine statoOrdine) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        ResultSet rs = executeQuery("select IDOrdine as ID from merci where IDCorriere = '" + IDCorriere + "';");
        while (rs.next())
            addOrdine(rs).update();
        disconnectToDB(rs);

        for (SimpleOrdine simpleOrdine : ordini)
            for (SimpleMerceOrdine simpleMerceOrdine : simpleOrdine.getMerci())
                if (simpleMerceOrdine.getIDCorriere() == IDCorriere && simpleMerceOrdine.getStato().equals(statoOrdine))
                    toReturn.add(simpleMerceOrdine.getDettagli());
        return toReturn;
    }

    //todo test
    public ArrayList<ArrayList<String>> getDettagliMerciResidenza(String residenza) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        ResultSet rs = executeQuery("select ID from ordini where residenza = '" + residenza + "';");
        while (rs.next())
            addOrdine(rs);
        disconnectToDB(rs);

        for (SimpleOrdine simpleOrdine : ordini)
            if (simpleOrdine.getResidenza().equals(residenza))
                for (SimpleMerceOrdine simpleMerceOrdine : simpleOrdine.getMerci())
                    if (simpleMerceOrdine.getStato() == StatoOrdine.PAGATO)
                        toReturn.add(simpleMerceOrdine.getDettagli());
        return toReturn;
    }

    /**
     * Ritorna l' {@link SimpleOrdine} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo dell' Ordine
     *
     * @return L'ordine desiderato
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleOrdine getOrdine(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.ordini where ID='" + ID + "' ;");
        if (rs.next()) {
            SimpleOrdine tmp = addOrdine(rs);
            disconnectToDB(rs);
            return tmp;
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID ordine non valido.");
        }
    }

    /**
     * Registra la {@link SimpleMerce} nell'{@link SimpleOrdine} creato.
     *
     * @param IDMerce  ID della merce
     * @param quantita Quantita della merce
     * @param IDOrdine Ordine in cui registrare la merce
     * @param negozio
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void registraMerce(int IDMerce, int quantita, int IDOrdine, Negozio negozio) throws SQLException {
        SimpleMerce simpleMerce = negozio.getMerce(IDMerce);
        if (simpleMerce.getQuantita() == 0 || simpleMerce.getQuantita() < quantita)
            throw new IllegalArgumentException("Quantita non valida.");

        simpleMerce.setQuantita(simpleMerce.getQuantita() - quantita);
        SimpleMerceOrdine simpleMerceOrdine = new SimpleMerceOrdine(simpleMerce.getPrezzo(), simpleMerce.getDescrizione(), StatoOrdine.PAGATO, IDOrdine);
        getOrdine(IDOrdine).aggiungiMerce(simpleMerceOrdine, quantita);
    }

    /**
     * Registra l'{@link SimpleOrdine} con i dati del {@link SimpleCliente} e cambia il suo {@code stato} in "PAGATO".
     *
     * @param IDCliente ID del cliente a cui appartiene l' ordine
     * @param nome      Nome del cliente a cui appartiene l' ordine
     * @param cognome   Cognome del cliente a cui appartiene l' ordine
     * @param negozio
     *
     * @return un ArrayList di String contenente i dettagli dell' Ordine creato
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> registraOrdine(int IDCliente, String nome, String cognome, Negozio negozio) throws SQLException {
        controllaCliente(IDCliente, nome, cognome);
        SimpleOrdine simpleOrdine = new SimpleOrdine(IDCliente, nome, cognome, negozio.getID());
        addOrdineToList(simpleOrdine);
        return simpleOrdine.getDettagli();
    }

    /**
     * Seleziona la {@link SimpleMerceOrdine} desiderata.
     *
     * @param ID Codice Identificativo della merce
     *
     * @return Le informazioni della merce
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> selezionaMerce(int ID) throws SQLException {
        return getMerceOrdine(ID).getDettagli();
    }

    public void setPuntoPrelievo(int IDOrdine, int IDPuntoPrelievo) throws SQLException {
        getOrdine(IDOrdine).setPuntoPrelievo(IDPuntoPrelievo);
    }

    public void setStatoMerce(int IDMerce, StatoOrdine statoOrdine) throws SQLException {
        getMerceOrdine(IDMerce).setStato(statoOrdine);
        SimpleOrdine simpleOrdine = getOrdine(getMerceOrdine(IDMerce).getIDOrdine());
        boolean toControl = true;
        for (SimpleMerceOrdine m : simpleOrdine.getMerci()) {
            if (m.getStato() != statoOrdine) {
                toControl = false;
                break;
            }
        }
        if (toControl)
            simpleOrdine.setStato(statoOrdine);
    }

    //todo controllare test
    public void setStatoOrdine(int IDOrdine, StatoOrdine statoOrdine) throws SQLException {
        getOrdine(IDOrdine).setStato(statoOrdine);
        for (SimpleMerceOrdine simpleMerceOrdine : getOrdine(IDOrdine).getMerci()) {
            simpleMerceOrdine.setStato(statoOrdine);
        }

    }

    /**
     * Imposta lo stato dell'{@link SimpleOrdine} come pagato se tutta la {@link SimpleMerceOrdine} in esso
     * è pagata e ritorna l' arrayList dei dettagli dell' ordine.
     *
     * @param IDOrdine Ordine da controllare
     *
     * @return un ArrayList di String contenente i dettagli dell' Ordine
     * @throws SQLException Errore causato da una query SQL
     */
    public ArrayList<String> terminaOrdine(int IDOrdine) throws SQLException {
        ArrayList<SimpleMerceOrdine> mercePagata = new ArrayList<>();
        for (SimpleMerceOrdine m : getOrdine(IDOrdine).getMerci()) {
            if (m.getStato() == StatoOrdine.PAGATO)
                mercePagata.add(m);
        }
        if (mercePagata.equals(getOrdine(IDOrdine).getMerci()))
            getOrdine(IDOrdine).setStato(StatoOrdine.PAGATO);
        else
            throw new IllegalArgumentException("Merce non pagata.");

        return getOrdine(IDOrdine).getDettagli();
    }

    public void update() throws SQLException {
        ResultSet rs = executeQuery("SELECT ID from sys.ordini;");
        while (rs.next())
            addOrdine(rs);
        disconnectToDB(rs);
    }
}
