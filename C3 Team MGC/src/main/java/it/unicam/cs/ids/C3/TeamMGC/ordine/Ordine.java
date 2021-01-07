package it.unicam.cs.ids.C3.TeamMGC.ordine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Ordine {

    private int ID;
    private int IDCliente;
    private String nomeCliente;
    private String cognomeCliente;
    private double totalePrezzo;
    private StatoOrdine stato;
    private int IDPuntoPrelievo = -1;
    private String residenza;
    private int IDNegozio;
    private ArrayList<MerceOrdine> merci = new ArrayList<>();

    public Ordine(int ID) throws SQLException {
        ResultSet rs = executeQuery("select * from ordini where ID ='" + ID + "';");
        if (rs.next()) {
            this.ID = ID;
            this.IDCliente = rs.getInt("IDCliente");
            this.nomeCliente = rs.getString("nomeCliente");
            this.cognomeCliente = rs.getString("cognomeCliente");
            this.totalePrezzo = rs.getDouble("totalePrezzo");
            this.stato = StatoOrdine.valueOf("stato");
            this.IDPuntoPrelievo = rs.getInt("IDPuntoPrelievo");
            this.residenza = rs.getString("residenza");
            this.IDNegozio = rs.getInt("IDNegozio");
        } else
            throw new IllegalArgumentException("ID non valido.");
    }

    public Ordine(int IDCliente, String nomeCliente, String cognomeCliente, int IDNegozio) throws SQLException {
        updateData("INSERT INTO sys.ordini (IDCliente, nomeCliente, cognomeCliente, IDNegozio) " +
                "VALUES ('" + IDCliente + "', '" + nomeCliente + "', '" + cognomeCliente + "', '" + IDNegozio + "');");
        ResultSet rs = executeQuery("SELECT MAX(ID) as ID from ordini;");
        rs.next();
        ID = rs.getInt("ID");
        this.IDCliente = IDCliente;
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        this.IDNegozio = IDNegozio;
    }

    /**
     * Aggiunge la {@link MerceOrdine} all'{@link Ordine}, prendendo i dati dal Database.
     *
     * @param merce Merce da aggiungere
     */
    public void addMerce(MerceOrdine merce) {
        merci.add(merce);
    }

    /**
     * Aggiunge l'{@code indirizzo} della residenza all'{@link Ordine}.
     *
     * @param indirizzo Indirizzo residenza del cliente
     */
    public void addResidenza(String indirizzo) throws SQLException {
        updateData("UPDATE sys.ordini SET IDPuntoPrelievo = 0 WHERE (ID = '" + this.ID + "');");
        updateData("UPDATE sys.ordini SET residenza = '" + indirizzo + "' WHERE (ID = '" + this.ID + "');");
        IDPuntoPrelievo = -1;
        residenza = indirizzo;
    }

    /**
     * Aggiunge la {@link MerceOrdine} all'{@link Ordine} del cliente.
     *
     * @param merce    Merce da aggiungere
     * @param quantita Quantita della merce da aggiungere
     */
    //todo
    public void aggiungiMerce(MerceOrdine merce, int quantita) throws SQLException {
        merce.setQuantita(quantita);
        merci.add(merce);
        this.totalePrezzo += (merce.getPrezzo() * quantita);
        updateData("UPDATE sys.ordini SET totalePrezzo = '" + this.totalePrezzo + "' WHERE (ID = '" + this.ID + "');");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordine ordine = (Ordine) o;
        return ID == ordine.ID;
    }

    public String getCognomeCliente() {
        return cognomeCliente;
    }

    /**
     * Ritorna un arraylist con i dettagli dell'{@link Ordine} in stringa.
     *
     * @return ArrayList dei dettagli
     */
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

        for (MerceOrdine merce : merci)
            merce.update();

        ordini.add(String.valueOf(getMerci()));
        return ordini;
    }

    public int getID() {
        return ID;
    }

    public int getIDCliente() {
        return IDCliente;
    }

    public ArrayList<MerceOrdine> getMerci() {
        return merci;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public int getPuntoPrelievo() {
        return IDPuntoPrelievo;
    }

    public void setPuntoPrelievo(int IDPuntoPrelievo) throws SQLException {
        updateData("UPDATE sys.ordini SET IDPuntoPrelievo = " + IDPuntoPrelievo + " WHERE ID = " + this.ID + ";");
        this.IDPuntoPrelievo = IDPuntoPrelievo;
    }

    public String getResidenza() {
        return residenza;
    }

    public StatoOrdine getStato() {
        return stato;
    }

    public void setStato(StatoOrdine statoOrdine) throws SQLException {
        updateData("UPDATE sys.ordini SET stato = '" + statoOrdine + "' WHERE (ID = '" + this.ID + "');");
        this.stato = statoOrdine;
    }

    public double getTotalePrezzo() {
        return totalePrezzo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    /**
     * @throws SQLException
     */
    public void update() throws SQLException {
        ResultSet rs = executeQuery("select * from sys.ordini where ID= '" + this.ID + "';");
        if (rs.next()) {
            this.nomeCliente = rs.getString("nomeCliente");
            this.cognomeCliente = rs.getString("cognomeCliente");
            this.totalePrezzo = rs.getInt("totalePrezzo");
            this.stato = StatoOrdine.valueOf(rs.getString("stato"));
            this.IDPuntoPrelievo = rs.getInt("IDPuntoPrelievo");
            this.residenza = rs.getString("residenza");
        }
    }
}
