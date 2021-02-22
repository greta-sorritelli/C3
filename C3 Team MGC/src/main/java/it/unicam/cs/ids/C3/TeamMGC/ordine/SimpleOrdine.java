package it.unicam.cs.ids.C3.TeamMGC.ordine;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.*;

/**
 * La classe implementa l' interfaccia {@link Ordine} ed ha la responsabilità di gestire un Ordine.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class SimpleOrdine implements Ordine {
    private final int ID;
    private final int IDCliente;
    private final int IDNegozio;
    private final ArrayList<MerceOrdine> merci = new ArrayList<>();
    private String nomeCliente;
    private String cognomeCliente;
    private double totalePrezzo;
    private StatoOrdine stato = StatoOrdine.DA_PAGARE;
    private int IDPuntoPrelievo = -1;
    private String residenza = "";

    /**
     * Costruttore per importare i dati dal DB.
     *
     * @param ID ID dell' Ordine
     *
     * @throws SQLException Errore causato da una query SQL
     */
    public SimpleOrdine(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from ordini where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.IDCliente = rs.getInt("IDCliente");
            this.nomeCliente = rs.getString("nomeCliente");
            this.cognomeCliente = rs.getString("cognomeCliente");
            this.totalePrezzo = rs.getDouble("totalePrezzo");
            this.stato = StatoOrdine.valueOf(rs.getString("stato"));
            this.IDPuntoPrelievo = rs.getInt("IDPuntoPrelievo");
            this.residenza = rs.getString("residenza");
            this.IDNegozio = rs.getInt("IDNegozio");

            rs = executeQuery("select ID from merci where IDOrdine ='" + ID + "';");
            while (rs.next())
                addMerceToOrdine(new SimpleMerceOrdine(rs.getInt("ID")));
            disconnectToDB(rs);
        } else {
            disconnectToDB(rs);
            throw new IllegalArgumentException("ID non valido.");
        }
    }

    /**
     * Costruttore per inserire i dati nel DB.
     *
     * @param IDCliente      ID del {@link Cliente}
     * @param nomeCliente    Nome del {@link Cliente}
     * @param cognomeCliente Cognome del {@link Cliente}
     * @param IDNegozio      ID del {@link Negozio}
     *
     * @throws SQLException eccezione causata da una query SQL
     */
    public SimpleOrdine(int IDCliente, String nomeCliente, String cognomeCliente, int IDNegozio) throws SQLException {
        updateData("INSERT INTO sys.ordini (IDCliente, nomeCliente, cognomeCliente, stato, IDNegozio, IDPuntoPrelievo) " +
                "VALUES ('" + IDCliente + "', '" + nomeCliente + "', '" + cognomeCliente + "', '" + "DA_PAGARE', '" + IDNegozio + "', '" + IDPuntoPrelievo + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from ordini;");
        rs.next();
        ID = rs.getInt("ID");
        this.IDCliente = IDCliente;
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        this.IDNegozio = IDNegozio;
        disconnectToDB(rs);
    }

    /**
     * Aggiunge la {@link MerceOrdine} all' {@link SimpleOrdine Ordine}.
     *
     * @param merce Merce da aggiungere
     */
    @Override
    public void addMerce(MerceOrdine merce) throws SQLException {
        addMerceToOrdine(merce);
        update();
    }

    /**
     * Aggiunge la {@link MerceOrdine} all' {@link SimpleOrdine Ordine} prendendo i dati dal database.
     *
     * @param rs ResultSet
     *
     * @throws SQLException Errore causato da una query SQL
     */
    private void addMerce(ResultSet rs) throws SQLException {
        while (rs.next())
            addMerceToOrdine(new SimpleMerceOrdine(rs.getInt("ID")));
    }

    /**
     * Controlla se l' {@link SimpleOrdine Ordine} contiene la {@link MerceOrdine}.
     * Se non è presente la aggiunge.
     *
     * @param simpleMerceOrdine la Merce da aggiungere
     */
    private void addMerceToOrdine(MerceOrdine simpleMerceOrdine) {
        if (!merci.contains(simpleMerceOrdine)) {
            merci.add(simpleMerceOrdine);
            Collections.sort(merci);
        }
    }

    /**
     * Aggiunge l'{@code indirizzo} della residenza all'{@link SimpleOrdine Ordine}.
     *
     * @param indirizzo Indirizzo residenza del cliente
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void addResidenza(String indirizzo) throws SQLException {
        updateData("UPDATE sys.ordini SET IDPuntoPrelievo = -1 WHERE (ID = '" + this.ID + "');");
        updateData("UPDATE sys.ordini SET residenza = '" + indirizzo + "' WHERE (ID = '" + this.ID + "');");
        IDPuntoPrelievo = -1;
        residenza = indirizzo;
    }

    /**
     * Aggiunge la {@link SimpleMerceOrdine} all'{@link SimpleOrdine Ordine} del cliente.
     *
     * @param merce    Merce da aggiungere
     * @param quantita Quantità della merce da aggiungere
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void aggiungiMerce(MerceOrdine merce, int quantita) throws SQLException {
        merce.setQuantita(quantita);
        merci.add(merce);
        this.totalePrezzo += (merce.getPrezzo() * quantita);
        updateData("UPDATE sys.ordini SET totalePrezzo = '" + this.totalePrezzo + "' WHERE (ID = '" + this.ID + "');");
    }

    /**
     * Confronta 2 oggetti di tipo {@link Ordine} attraverso il loro {@code ID}.
     *
     * @param o Oggetto da confrontare
     *
     * @return <ul><li>0 se i due oggetti sono uguali,</li>
     * <li>1 se questo oggetto ha l'ID maggiore,</li>
     * <li>-1 se o ha l'ID maggiore.</li></ul>
     */
    @Override
    public int compareTo(@NonNull Ordine o) {
        if (this.equals(o))
            return 0;
        if (this.getID() > o.getID())
            return 1;
        else return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordine simpleOrdine = (SimpleOrdine) o;
        return ID == simpleOrdine.getID();
    }

    /**
     * Ritorna il Cognome del {@link Cliente} collegato all' {@link SimpleOrdine Ordine}.
     *
     * @return il cognome del Cliente
     */
    @Override
    public String getCognomeCliente() {
        return cognomeCliente;
    }

    /**
     * Ritorna un arraylist con i dettagli dell' {@link SimpleOrdine Ordine}.
     *
     * @return ArrayList dei dettagli dell' ordine
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public ArrayList<String> getDettagli() throws SQLException {
        update();
        ArrayList<String> ordini = new ArrayList<>();
        ordini.add(String.valueOf(getID()));
        ordini.add(String.valueOf(getIDCliente()));
        ordini.add(getNomeCliente());
        ordini.add(getCognomeCliente());
        ordini.add(String.valueOf(getTotalePrezzo()));
        ordini.add(getStato().toString());

        if (IDPuntoPrelievo != -1 && stato != StatoOrdine.RITIRATO)
            ordini.add(String.valueOf(getPuntoPrelievo()));
        else
            ordini.add(String.valueOf(getResidenza()));

        ordini.add(String.valueOf(IDNegozio));

        for (MerceOrdine merce : merci)
            merce.update();

        ordini.add(String.valueOf(getMerci()));
        return ordini;
    }

    /**
     * Ritorna il Codice Identificativo dell' {@link SimpleOrdine Ordine}.
     *
     * @return l'ID dell' ordine
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * Ritorna il Codice Identificativo del {@link Cliente} collegato all' {@link SimpleOrdine Ordine}.
     *
     * @return l'ID del Cliente
     */
    @Override
    public int getIDCliente() {
        return IDCliente;
    }

    /**
     * Ritorna il Codice Identificativo del {@link Negozio} collegato all' {@link SimpleOrdine Ordine}.
     *
     * @return l'ID del Negozio
     */
    @Override
    public int getIDNegozio() {
        return IDNegozio;
    }

    /**
     * Ritorna la lista delle {@link MerceOrdine Merci} dell' {@link SimpleOrdine Ordine}.
     *
     * @return la lista delle merci
     */
    @Override
    public ArrayList<MerceOrdine> getMerci() {
        return new ArrayList<>(merci);
    }

    /**
     * Ritorna il Nome del {@link Cliente} collegato all' {@link SimpleOrdine Ordine}.
     *
     * @return il Nome del Cliente
     */
    @Override
    public String getNomeCliente() {
        return nomeCliente;
    }

    /**
     * Ritorna l'ID del {@link PuntoPrelievo} collegato all' {@link SimpleOrdine Ordine}.
     *
     * @return l'ID del Punto di Prelievo
     */
    @Override
    public int getPuntoPrelievo() {
        return IDPuntoPrelievo;
    }

    /**
     * Imposta un {@link PuntoPrelievo} all' {@link SimpleOrdine Ordine}.
     *
     * @param IDPuntoPrelievo ID del Punto di Prelievo da impostare
     */
    @Override
    public void setPuntoPrelievo(int IDPuntoPrelievo) throws SQLException {
        updateData("UPDATE sys.ordini SET IDPuntoPrelievo = " + IDPuntoPrelievo + " WHERE ID = " + this.ID + ";");
        this.IDPuntoPrelievo = IDPuntoPrelievo;
    }

    /**
     * Ritorna la Residenza collegata all' {@link SimpleOrdine Ordine}.
     *
     * @return la residenza dell' ordine
     */
    @Override
    public String getResidenza() {
        return residenza;
    }

    /**
     * Imposta una Residenza all' {@link SimpleOrdine Ordine}.
     *
     * @param residenza Residenza da impostare
     */
    @Override
    public void setResidenza(String residenza) {
        this.residenza = residenza;
    }

    /**
     * Ritorna lo {@link StatoOrdine Stato} dell' {@link SimpleOrdine Ordine}.
     *
     * @return lo Stato dell' ordine
     */
    @Override
    public StatoOrdine getStato() {
        return stato;
    }

    /**
     * Imposta lo {@link StatoOrdine Stato} all' {@link SimpleOrdine Ordine}.
     *
     * @param statoOrdine Stato da impostare
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void setStato(StatoOrdine statoOrdine) throws SQLException {
        updateData("UPDATE sys.ordini SET stato = '" + statoOrdine + "' WHERE (ID = '" + this.ID + "');");
        this.stato = statoOrdine;
    }

    /**
     * Ritorna il Prezzo Totale dell' {@link SimpleOrdine Ordine}.
     *
     * @return il prezzo dell' ordine
     */
    @Override
    public double getTotalePrezzo() {
        return totalePrezzo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", IDCliente=" + IDCliente +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", cognomeCliente='" + cognomeCliente + '\'' +
                ", totalePrezzo=" + totalePrezzo +
                ", stato=" + stato +
                ", IDPuntoPrelievo=" + IDPuntoPrelievo +
                ", residenza='" + residenza + '\'' +
                ", IDNegozio=" + IDNegozio +
                ", merci=" + merci;
    }

    /**
     * Aggiorna i valori all' interno dell' oggetto prendendo i dati dal DB.
     *
     * @throws SQLException Errore causato da una query SQL
     */
    @Override
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.ordini where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.nomeCliente = rs.getString("nomeCliente");
            this.cognomeCliente = rs.getString("cognomeCliente");
            this.totalePrezzo = rs.getInt("totalePrezzo");
            if (!rs.getString("stato").isEmpty())
                this.stato = StatoOrdine.valueOf(rs.getString("stato"));
            this.IDPuntoPrelievo = rs.getInt("IDPuntoPrelievo");
            this.residenza = rs.getString("residenza");

            addMerce(executeQuery("select ID from merci where IDOrdine ='" + ID + "';"));
            for (MerceOrdine simpleMerceOrdine : merci)
                simpleMerceOrdine.update();
        }
        disconnectToDB(rs);
    }
}
