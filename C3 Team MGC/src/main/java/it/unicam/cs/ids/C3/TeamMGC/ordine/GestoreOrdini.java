package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.SimpleCliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleMerce;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * Classe per la gestione di ogni {@link Ordine}
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class GestoreOrdini {
    private static GestoreOrdini gestoreOrdini;
    private final ArrayList<Ordine> ordini = new ArrayList<>();

    private GestoreOrdini() {
    }

    public static GestoreOrdini getInstance() {
        if (gestoreOrdini == null)
            gestoreOrdini = new GestoreOrdini();
        return gestoreOrdini;
    }

    /**
     * Controlla se l' {@link Ordine} che si vuole creare e' gia' presente nella lista degli ordini. Se non e' presente
     * viene creato e aggiunto alla lista.
     *
     * @return L'ordine
     * @throws SQLException Errore causato da una query SQL
     */
    private Ordine addOrdine(ResultSet rs) throws SQLException {
        for (Ordine ordine : ordini)
            if (ordine.getID() == rs.getInt("ID"))
                return ordine;
        Ordine toReturn = new SimpleOrdine(rs.getInt("ID"));
        addOrdineToList(toReturn);
        return toReturn;
    }

    /**
     * Aggiunge un {@link Ordine} alla lista di ordini.
     *
     * @param ordine Ordine da aggiungere
     */
    private void addOrdineToList(Ordine ordine) {
        if (!ordini.contains(ordine))
            ordini.add(ordine);
    }

    /**
     * Aggiunge l'{@code indirizzo} della residenza all'{@link Ordine} preso tramite l' {@code ID}.
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
        MerceOrdine tmp = getMerceOrdine(IDMerce);
        tmp.setIDCorriere(IDCorriere);
        tmp.setStato(StatoOrdine.AFFIDATO_AL_CORRIERE);
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
     * Crea una {@link SimpleMerceOrdine} e la aggiunge ad un {@link Ordine}.
     *
     * @param rs     ResultSet contenente la query per creare la Merce
     * @param ordine Ordine a cui aggiungere la Merce
     *
     * @return la MerceOrdine creata
     * @throws SQLException Errore causato da una query SQL
     */
    //todo forse non serve piu'
    private SimpleMerceOrdine creaMerceFromDB(ResultSet rs, Ordine ordine) throws SQLException {
        SimpleMerceOrdine toReturn = new SimpleMerceOrdine(rs.getInt("ID"));
        ordine.addMerce(toReturn);
        return toReturn;
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
        ArrayList<MerceOrdine> merci = new ArrayList<>();

        for (Ordine ordine : ordini) {
            ordine.update();
            merci.addAll(ordine.getMerci());
        }
        ArrayList<ArrayList<String>> dettagli = new ArrayList<>();

        for (MerceOrdine merceOrdine : merci)
            if (merceOrdine.getStato().equals(statoOrdine))
                dettagli.add(merceOrdine.getDettagli());

        return dettagli;
    }

    //todo test
    public ArrayList<ArrayList<String>> getDettagliMerciOfCorriere(int IDCorriere, StatoOrdine statoOrdine) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        ResultSet rs = executeQuery("select IDOrdine as ID from merci where IDCorriere = '" + IDCorriere + "';");
        while (rs.next())
            addOrdine(rs).update();
        disconnectToDB(rs);

        for (Ordine ordine : ordini)
            for (MerceOrdine merceOrdine : ordine.getMerci())
                if (merceOrdine.getIDCorriere() == IDCorriere && merceOrdine.getStato().equals(statoOrdine))
                    toReturn.add(merceOrdine.getDettagli());
        return toReturn;
    }

    //todo test
    public ArrayList<ArrayList<String>> getDettagliMerciResidenza(String residenza) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        ResultSet rs = executeQuery("select ID from ordini where residenza = '" + residenza + "';");
        while (rs.next())
            addOrdine(rs);
        disconnectToDB(rs);

        for (Ordine ordine : ordini)
            if (ordine.getResidenza().equals(residenza))
                for (MerceOrdine merceOrdine : ordine.getMerci())
                    if (merceOrdine.getStato() == StatoOrdine.PAGATO)
                        toReturn.add(merceOrdine.getDettagli());
        return toReturn;
    }

    /**
     * Ritorna la lista dei dettagli dell' {@link Ordine} preso tramite l' {@code ID}.
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
     * Ritorna la lista dei dettagli degli {@link Ordine Ordini} di un cliente presenti nel DB.
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
     * "in deposito" di un insieme di {@link Ordine Ordini} .
     *
     * @param ordini Insieme di ordini
     *
     * @return ArrayList<ArrayList < String>> dei dettagli delle merci
     * @throws SQLException Errore causato da una query SQL
     */
    //todo finire test
    public ArrayList<ArrayList<String>> getInDepositMerci(ArrayList<Ordine> ordini) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        for (Ordine ordine : ordini)
            for (MerceOrdine m : ordine.getMerci())
                if (m.getStato() == StatoOrdine.IN_DEPOSITO)
                    toReturn.add(m.getDettagli());
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
    public MerceOrdine getMerceOrdine(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * from merci where ID ='" + ID + "';");
        if (rs.next()) {
            Ordine ordine = getOrdine(rs.getInt("IDOrdine"));
            for (MerceOrdine merceOrdine : ordine.getMerci())
                if (merceOrdine.getID() == ID) {
                    disconnectToDB(rs);
                    return merceOrdine;
                } else {
                    SimpleMerceOrdine tmp = creaMerceFromDB(rs, ordine);
                    disconnectToDB(rs);
                    return tmp;
                }
        }
        disconnectToDB(rs);
        throw new IllegalArgumentException("ID non valido.");
    }

    //todo commento
    public ArrayList<ArrayList<String>> getMerciFromNegozioToMagazzino(int IDNegozio, int IDPuntoPrelievo) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        ResultSet rs = executeQuery("select ID from ordini where IDNegozio = '" + IDNegozio + "' and IDPuntoPrelievo ='"
                + IDPuntoPrelievo + "';");
        while (rs.next())
            addOrdine(rs);
        disconnectToDB(rs);

        for (Ordine ordine : ordini)
            if (ordine.getIDNegozio() == IDNegozio && ordine.getPuntoPrelievo() == IDPuntoPrelievo)
                for (MerceOrdine merceOrdine : ordine.getMerci())
                    if (merceOrdine.getStato() == StatoOrdine.PAGATO)
                        toReturn.add(merceOrdine.getDettagli());
        return toReturn;
    }

    //todo commento
    public ArrayList<ArrayList<String>> getMerciMagazzino(int IDPuntoPrelievo) throws SQLException {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
        ResultSet rs = executeQuery("select ID from ordini where IDPuntoPrelievo = '" + IDPuntoPrelievo + "';");
        while (rs.next())
            addOrdine(rs);
        disconnectToDB(rs);

        for (Ordine ordine : ordini)
            if (ordine.getPuntoPrelievo() == IDPuntoPrelievo)
                for (MerceOrdine merceOrdine : ordine.getMerci())
                    if (merceOrdine.getStato() == StatoOrdine.PAGATO)
                        toReturn.add(merceOrdine.getDettagli());
        return toReturn;
    }

    /**
     * Ritorna l' {@link Ordine} collegato all' {@code ID}.
     *
     * @param ID Codice Identificativo dell' Ordine
     *
     * @return L'ordine desiderato
     * @throws SQLException Errore causato da una query SQL
     */
    public Ordine getOrdine(int ID) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM sys.ordini where ID='" + ID + "' ;");
        if (rs.next()) {
            Ordine tmp = addOrdine(rs);
            disconnectToDB(rs);
            return tmp;
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID ordine non valido.");
        }
    }

    /**
     * Registra la {@link SimpleMerce} nell'{@link Ordine} creato.
     *
     * @param IDMerce  ID della merce
     * @param quantita Quantita della merce
     * @param IDOrdine Ordine in cui registrare la merce
     * @param negozio
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public void registraMerce(int IDMerce, int quantita, int IDOrdine, Negozio negozio) throws SQLException {
        Merce simpleMerce = negozio.getItem(IDMerce);
        if (simpleMerce.getQuantita() == 0 || simpleMerce.getQuantita() < quantita)
            throw new IllegalArgumentException("Quantita non valida.");

        simpleMerce.setQuantita(simpleMerce.getQuantita() - quantita);
        SimpleMerceOrdine simpleMerceOrdine = new SimpleMerceOrdine(simpleMerce.getPrezzo(), simpleMerce.getDescrizione(), StatoOrdine.PAGATO, IDOrdine);
        getOrdine(IDOrdine).aggiungiMerce(simpleMerceOrdine, quantita);
    }

    /**
     * Registra l'{@link Ordine} con i dati del {@link SimpleCliente} e cambia il suo {@code stato} in "PAGATO".
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
        Ordine ordine = new SimpleOrdine(IDCliente, nome, cognome, negozio.getID());
        addOrdineToList(ordine);
        return ordine.getDettagli();
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
        Ordine ordine = getOrdine(getMerceOrdine(IDMerce).getIDOrdine());
        boolean toControl = true;
        for (MerceOrdine m : ordine.getMerci()) {
            if (m.getStato() != statoOrdine) {
                toControl = false;
                break;
            }
        }
        if (toControl)
            ordine.setStato(statoOrdine);
    }

    //todo controllare test
    public void setStatoOrdine(int IDOrdine, StatoOrdine statoOrdine) throws SQLException {
        getOrdine(IDOrdine).setStato(statoOrdine);
        for (MerceOrdine merceOrdine : getOrdine(IDOrdine).getMerci())
            merceOrdine.setStato(statoOrdine);
    }

    /**
     * Imposta lo stato dell'{@link Ordine} come pagato se tutta la {@link SimpleMerceOrdine} in esso
     * è pagata e ritorna l' arrayList dei dettagli dell' ordine.
     *
     * @param IDOrdine Ordine da controllare
     *
     * @return un ArrayList di String contenente i dettagli dell' Ordine
     * @throws SQLException Errore causato da una query SQL
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

    public void update() throws SQLException {
        ResultSet rs = executeQuery("SELECT ID from sys.ordini;");
        while (rs.next())
            addOrdine(rs);
        disconnectToDB(rs);
    }
}
