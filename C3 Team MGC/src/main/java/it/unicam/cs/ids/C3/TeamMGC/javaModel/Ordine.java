package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static it.unicam.cs.ids.C3.TeamMGC.javaPercistence.DatabaseConnection.executeQuery;

public class Ordine {

    private int ID;
    private String IDCliente;
    private String nomeCliente;
    private String cognomeCliente;
    private double totalePrezzo;
    private StatoOrdine stato;
    private PuntoPrelievo puntoPrelievo = null;
    private String residenza = null;
    private ArrayList<Merce> merci = new ArrayList<>();

    public Ordine(int ID) {
        this.ID = ID;
    }

    /**
     * Aggiunge l'indirizzo della residenza all'ordine.
     * @param indirizzo Indirizzo residenza del cliente.
     *
     */
    public void addResidenza(String indirizzo) {
        puntoPrelievo = null;
        residenza = indirizzo;
    }

    /**
     * @param merce
     * @param quantita
     */
    public void aggiungiMerce(Merce merce, int quantita) {
        merce.setQuantita(quantita);
        merci.add(merce);
    }

    public int getID() {
        return ID;
    }

	public ArrayList<Ordine> getDettagli() {
		try{
		    ArrayList<Ordine> dettagliOrdini = new ArrayList<>();
            ResultSet rs = executeQuery("SELECT * FROM sys.ordini");
            while(rs.next()){
            }
        } catch (SQLException exception) {
		    //todo
            exception.printStackTrace();
        }
        return null;
    }

    public void riceviPagamento() {
        // TODO - implement Ordine.riceviPagamento
        throw new UnsupportedOperationException();
    }

    /**
     * @param magazzino
     */
    public void setPuntoPrelievo(PuntoPrelievo magazzino) {
        puntoPrelievo = magazzino;
    }

    /**
     * Imposta lo stato dell'ordine.
     * @param statoOrdine  Stato dell'ordine da impostare.
     */
    public void setStato(StatoOrdine statoOrdine) {
        stato = statoOrdine;
    }

    public StatoOrdine getStato() {
        return stato;
    }
}