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

    public Ordine(int ID, int IDCliente, String nomeCliente, String cognomeCliente, double totalePrezzo, StatoOrdine stato, PuntoPrelievo puntoPrelievo, String residenza, ArrayList<Merce> merci) {
        this.ID = ID;
        this.IDCliente = IDCliente;
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        this.totalePrezzo = totalePrezzo;
        this.stato = stato;
        this.puntoPrelievo = puntoPrelievo;
        this.residenza = residenza;
        this.merci = merci;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "ID=" + ID +
                ", IDCliente=" + IDCliente +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", cognomeCliente='" + cognomeCliente + '\'' +
                ", totalePrezzo=" + totalePrezzo +
                ", stato=" + stato +
                ", puntoPrelievo=" + puntoPrelievo +
                ", residenza='" + residenza + '\'' +
                ", merci=" + merci +
                '}';
    }

    /**
     * Aggiunge l'indirizzo della residenza all'ordine.
     * @param indirizzo Indirizzo residenza del cliente
     *
     */
    public void addResidenza(String indirizzo) {
        puntoPrelievo = null;
        residenza = indirizzo;
    }

    /**
     * Aggiunge la merce all'ordine del cliente.
     * @param merce       Merce da aggiungere
     * @param quantita    Quantita della merce da aggiungere
     */
    public void aggiungiMerce(Merce merce, int quantita) {
        merce.setQuantita(quantita);
        merci.add(merce);
    }

    public int getID() {
        return ID;
    }

	public String getDettagli() {
        return this.toString();
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

    public StatoOrdine getStato() {
        return stato;
    }

    public PuntoPrelievo getPuntoPrelievo() {
        return puntoPrelievo;
    }

    public String getResidenza() {
        return residenza;
    }
}