package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Merce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

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
   //todo test
    public void addResidenza(int IDOrdine, String indirizzo) {
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

    /**
     *
     * @param rs
     * @param ordine
     * @return
     * @throws SQLException
     */
    //todo test
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
    //todo test
    public ArrayList<ArrayList<String>> getDettagliMerce(StatoOrdine statoOrdine) {
        try {
            ArrayList<MerceOrdine> merce = new ArrayList<>();
            ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * FROM sys.merci WHERE (`stato` =  '" + statoOrdine + "');");
            return getArrayLists(merce, dettagli, rs);
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * @param IDOrdine
     * @return
     */
    //todo
    public ArrayList<ArrayList<String>> getDettagliMerceTotale(int IDOrdine) {
        try {
            ArrayList<MerceOrdine> merce = new ArrayList<>();
            ArrayList<ArrayList<String>> dettagli = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * FROM sys.merci WHERE (`IDOrdine` =  '" + IDOrdine + "');");
            return getArrayLists(merce, dettagli, rs);
        } catch (SQLException exception) {
        //todo
        exception.printStackTrace();
    }
        return null;
}

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
     * @param IDCliente
     * @return
     */
    //todo test
    public ArrayList<String> getDettagliOrdine(int IDCliente) {
        try {
            ResultSet rs = executeQuery("SELECT ID from ordini where IDCliente ='" + IDCliente + "';");
            if (rs.next())
               return getOrdine(rs.getInt("ID")).getDettagli();
            } catch (SQLException exception) {
                //todo
                exception.printStackTrace();
            }
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
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
        return null;
    }

    //todo test
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
     * @param IDOrdine   Ordine in cui registrare la merce
     */
    public void registraMerce(int IDMerce, int quantita, int IDOrdine) {
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
     * @return          L'ordine registrato
     */
    public Ordine registraOrdine(int IDCliente, String nome, String cognome) {
        controllaCliente(IDCliente, nome, cognome);
        Ordine ordine = new Ordine(IDCliente, nome, cognome);
        addOrdineToList(ordine);
        return ordine;
    }

    //todo test
    public void setPuntoPrelievo(int IDOrdine, int IDPuntoPrelievo) {
        try {
            getOrdine(IDOrdine).setPuntoPrelievo(IDPuntoPrelievo);
            updateData("UPDATE `sys`.`ordini` SET `puntoPrelievo` = '" + IDPuntoPrelievo + "' WHERE (`ID` = '" + IDOrdine + "');");
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    //todo test
    public void setStatoMerce(int IDMerce, StatoOrdine statoOrdine) {
        getMerceOrdine(IDMerce).setStato(statoOrdine);
    }

    //todo test
    public void setStatoOrdine(int IDOrdine, StatoOrdine statoOrdine) {
        getOrdine(IDOrdine).setStato(statoOrdine);
    }

    /**
     * Imposta lo stato dell'{@link Ordine} come pagato se tutta la {@link MerceOrdine} in esso è pagata.
     *
     * @param IDOrdine Ordine da controllare
     */
    //todo
    public void terminaOrdine(int IDOrdine) {
        ArrayList<MerceOrdine> mercePagata = new ArrayList<>();
        for (MerceOrdine m : getOrdine(IDOrdine).getMerci()) {
            if (m.getStato() == StatoOrdine.PAGATO)
                mercePagata.add(m);
        }
        if (mercePagata.equals(getOrdine(IDOrdine).getMerci()))
            getOrdine(IDOrdine).setStato(StatoOrdine.PAGATO);
        else
            //todo eccezione
            throw new IllegalArgumentException();
    }

    public ArrayList<ArrayList<String>> visualizzaMerce(int capienza) {
        //todo arraylist di arraylist (con stato pagato e che rientra nella capienza)
        return null;
    }
}