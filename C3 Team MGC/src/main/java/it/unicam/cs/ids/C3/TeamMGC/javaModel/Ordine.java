package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;
import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.updateData;

public class Ordine {

    private int ID;
    private int IDCliente;
    private String nomeCliente;
    private String cognomeCliente;
    private double totalePrezzo;
    private StatoOrdine stato;
    private PuntoPrelievo puntoPrelievo = null;
    private String residenza = null;
    private ArrayList<Merce> merci = new ArrayList<>();

    public Ordine(int ID, int IDCliente, String nomeCliente, String cognomeCliente, double totalePrezzo, StatoOrdine stato, PuntoPrelievo puntoPrelievo) {
        this.ID = ID;
        this.IDCliente = IDCliente;
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        this.totalePrezzo = totalePrezzo;
        this.stato = stato;
        this.puntoPrelievo = puntoPrelievo;
    }

    public Ordine(int IDCliente, String nomeCliente, String cognomeCliente, double totalePrezzo, StatoOrdine stato, PuntoPrelievo puntoPrelievo){
        try {
            updateData("INSERT INTO `sys`.`ordini` (`IDCliente`, `nomeCliente`,`cognomeCliente`,`totalePrezzo`,`stato`,`puntoPrelievo`,`residenza`) " +
                    "VALUES ('" + IDCliente + "', '" + nomeCliente + "', '" + cognomeCliente + "', '" + totalePrezzo + "', '"+ stato + "'," +
                    "'" + puntoPrelievo.getNome() + "', \"null\");");
            ResultSet rs = executeQuery("SELECT MAX(ID) as ID from ordini;");
            rs.next();
            ID = rs.getInt("ID");
            this.IDCliente = IDCliente;
            this.nomeCliente= nomeCliente;
            this.cognomeCliente = cognomeCliente;
            this.totalePrezzo = totalePrezzo;
            this.residenza = null;
        } catch (SQLException exception) {
            //todo
            exception.printStackTrace();
        }
    }

    public int getID() {

        return ID;
    }
    public int getIDCliente() {
        return IDCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getCognomeCliente() {
        return cognomeCliente;
    }

    public double getTotalePrezzo() {
        return totalePrezzo;
    }
    public StatoOrdine getStato() {
        return stato;
    }

    public PuntoPrelievo getPuntoPrelievo() {
        return puntoPrelievo;
    }

    public String getResidenza() {
        return residenza;
    }

    public ArrayList<Merce> getMerci() {
        return merci;
    }

    /**
     * Aggiunge l'indirizzo della residenza all'ordine.
     *
     * @param indirizzo Indirizzo residenza del cliente
     */
    public void addResidenza(String indirizzo) {
        puntoPrelievo = null;
        residenza = indirizzo;
    }

    /**
     * Aggiunge la merce all'ordine del cliente.
     *
     * @param merce    Merce da aggiungere
     * @param quantita Quantita della merce da aggiungere
     */
    public void aggiungiMerce(Merce merce, int quantita) {
        merce.setQuantita(quantita);
        merci.add(merce);
        merce.setIDOrdine(this.getID());
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getDettagli() {
        ArrayList<String> ord = new ArrayList<>();
        ord.add(String.valueOf(getID()));
        ord.add(String.valueOf(getIDCliente()));
        ord.add(getNomeCliente());
        ord.add(getCognomeCliente());
        ord.add(String.valueOf(getTotalePrezzo()));
        ord.add(getStato().toString());

        if (puntoPrelievo != null)
            ord.add(String.valueOf(getPuntoPrelievo()));
        else
            ord.add(String.valueOf(getResidenza()));

        ord.add(String.valueOf(getMerci()));
        return ord;
    }

    public void riceviPagamento() {
        // TODO - implement Ordine.riceviPagamento
        throw new UnsupportedOperationException();
    }

    public void setPuntoPrelievo(PuntoPrelievo magazzino) {
        try {
            updateData("UPDATE `sys`.`ordini` SET `puntoPrelievo` = '" + magazzino.getNome() + "' WHERE (`ID` = '" + this.ID + "');");
            this.puntoPrelievo = magazzino;
        } catch (SQLException exception) {
            //TODO
            exception.printStackTrace();
        }
    }

    public void setStato(StatoOrdine statoOrdine) {
        try{
        stato = statoOrdine;
        updateData("UPDATE `sys`.`ordini` SET `stato` = '" + statoOrdine + "' WHERE (`ID` = '" + this.ID + "');");
        this.stato = statoOrdine;
    } catch (SQLException exception) {
        //TODO
        exception.printStackTrace();
    }
    }
}

