package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.util.ArrayList;

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
        puntoPrelievo = magazzino;
    }

    public void setStato(StatoOrdine statoOrdine) {
        stato = statoOrdine;
    }


}