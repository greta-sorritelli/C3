package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;

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

    private Ordine addOrdine(ResultSet rs) throws SQLException {
        for (Ordine ordine : ordini)
            if (ordine.getID() == rs.getInt("ID"))
                return ordine;
        Ordine toReturn = new Ordine(rs.getInt("ID"), rs.getInt("IDCliente"),
                rs.getString("nomeCliente"), rs.getString("cognomeCliente"),
                rs.getDouble("totalePrezzo"), StatoOrdine.valueOf(rs.getString("stato")),
                rs.getInt("IDPuntoPrelievo"));
        addOrdineToList(toReturn);
        return toReturn;
    }

    private boolean addOrdineToList(Ordine ordine) {
        if (!ordini.contains(ordine))
            return ordini.add(ordine);
        else
            return false;
    }

    /**
     * @param IDOrdine
     * @param indirizzo
     */
    //todo
    public void addResidenza(int IDOrdine, String indirizzo) {

    }

    /**
     * Controlla se il {@link Cliente} è già presente nel database.
     *
     * @param IDCliente ID del cliente
     * @param nome      Nome del cliente
     * @param cognome   Cognome del cliente
     * @throws IllegalArgumentException Se il cliente non è presente nel database
     */
    private void controllaCliente(int IDCliente, String nome, String cognome) {
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.clienti where ID = '" + IDCliente + "' and nome = '" + nome + "' and cognome = '" + cognome + "';");
            if (!rs.next())
                //todo eccezione
                throw new IllegalArgumentException();
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    private MerceOrdine creaMerceFromDB(ResultSet rs, Ordine ordine) throws SQLException {
        MerceOrdine toReturn = new MerceOrdine(rs.getInt("ID"), rs.getInt("IDOrdine"),
                rs.getDouble("prezzo"), rs.getString("descrizione"),
                rs.getInt("quantita"), StatoOrdine.valueOf(rs.getString("stato")));
        ordine.addMerce(toReturn);
        return toReturn;
    }

    /**
     * @return ArrayList<ArrayList < String>> dei dettagli della merce con un certo stato.
     */
    public ArrayList<ArrayList<String>> getDettagliMerce(StatoOrdine statoOrdine) {
        try {
            ArrayList<MerceOrdine> merce = new ArrayList<>();
            ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * FROM sys.merci WHERE (`stato` =  '" + statoOrdine + "');");
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * @param IDOrdine
     * @return
     */
    //todo
    public ArrayList<String> getDettagliMerceTotale(int IDOrdine) {

        return null;
    }

    /**
     * @param IDCliente
     * @return
     */
    //todo
    public ArrayList<String> getDettagliOrdine(int IDCliente) {

        return null;
    }

    //todo fare test
    public MerceOrdine getMerceOrdine(int ID) {
        try {
            ResultSet rs = executeQuery("SELECT * from merci where ID ='" + ID + "';");
            if (rs.next()) {
                Ordine ordine = getOrdine(rs.getInt("IDOrdine"));
                for (MerceOrdine merceOrdine : ordine.getMerci())
                    if (merceOrdine.getID() == ID)
                        return merceOrdine;
                    else
                        return creaMerceFromDB(rs, ordine);
            }
        } catch (SQLException throwables) {
            //todo
            throwables.printStackTrace();
        }
        return null;
    }

    public Ordine getOrdine(int ID) {
        try {
            ResultSet rs = executeQuery("SELECT * FROM sys.ordini where ID='" + ID + "' ;");
            if (rs.next())
                return addOrdine(rs);
            else
                //todo eccezione
                throw new IllegalArgumentException();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Registra la {@link Merce} nell'{@link Ordine} creato.
     *
     * @param IDMerce  ID della merce
     * @param quantita Quantita della merce
     * @param ordine   Ordine in cui registrare la merce
     */
    //todo
    public void registraMerce(int IDMerce, int quantita, Ordine ordine) {
        Merce merce = negozio.getMerce(IDMerce);
        if (merce.getQuantita() == 0 || merce.getQuantita() < quantita)
            throw new IllegalArgumentException();

        merce.setQuantita(merce.getQuantita() - quantita);
        MerceOrdine merceOrdine = new MerceOrdine(merce.getPrezzo(), merce.getDescrizione(), StatoOrdine.PAGATO, ordine.getID());
        ordine.aggiungiMerce(merceOrdine, quantita);
    }

    /**
     * Registra l'{@link Ordine} con i dati del {@link Cliente} e cambia il suo {@code stato} in "PAGATO.
     *
     * @param IDCliente ID del cliente a cui appartiene l'ordine
     * @param nome      Nome del cliente a cui appartiene l'ordine
     * @param cognome   Cognome del cliente a cui appartiene l'ordine
     * @return L'ordine registrato
     */
    public Ordine registraOrdine(int IDCliente, String nome, String cognome) {
        controllaCliente(IDCliente, nome, cognome);
        return new Ordine(IDCliente, nome, cognome);
    }

    /**
     * @param IDOrdine
     * @param magazzino
     */
    //todo
    public void setPuntoPrelievo(int IDOrdine, PuntoPrelievo magazzino) {

    }

    /**
     * @param IDMerce
     * @param statoOrdine
     */
    //todo
    public void setStatoMerce(int IDMerce, StatoOrdine statoOrdine) {

    }

    /**
     * @param IDOrdine
     * @param statoOrdine
     */
    //todo
    public void setStatoOrdine(int IDOrdine, StatoOrdine statoOrdine) {

    }

    /**
     * Imposta lo stato dell'{@link Ordine} come pagato se tutta la {@link MerceOrdine} in esso è pagata.
     *
     * @param ordine Ordine da controllare
     */
    //todo
    public void terminaOrdine(Ordine ordine) {
        ArrayList<MerceOrdine> mercePagata = new ArrayList<>();
        for (MerceOrdine m : ordine.getMerci()) {
            if (m.getStato() == StatoOrdine.PAGATO)
                mercePagata.add(m);
        }
        if (mercePagata.equals(ordine.getMerci()))
            ordine.setStato(StatoOrdine.PAGATO);
        else
            //todo eccezione
            throw new IllegalArgumentException();
    }

    public ArrayList<ArrayList<String>> visualizzaMerce() {
        //todo arraylist di arraylist (con stato pagato e che rientra nella capienza)
        return null;
    }
}