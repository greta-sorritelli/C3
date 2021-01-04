package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;

import java.util.ArrayList;

public class ICommesso {
    Negozio negozio;
    GestoreOrdini gestoreOrdini;
    GestoreMagazzini gestoreMagazzini;
    GestoreClienti gestoreClienti;

    /**
     * @param IDOrdine
     * @param indirizzo
     */
    public void addResidenza(int IDOrdine, String indirizzo) {
        gestoreOrdini.addResidenza(IDOrdine,indirizzo);
    }


    /**
     * @return ArrayList<String> dei dettagli dei magazzini.
     */
    public ArrayList<String> getDettagliItems() {
        gestoreMagazzini.getDettagliItems();
        //todo
        return null;
    }

    /**
     * @param ID       Descrizione della merce
     * @param quantita Quantita della merce
     * @param IDOrdine   Ordine in cui registrare la merce
     */
    //todo IDOrdine
    public void registraMerce(int ID, int quantita, int IDOrdine) {
        gestoreOrdini.registraMerce(ID, quantita, IDOrdine);
    }

    /**
     * @param IDCliente ID del Cliente
     * @param Nome      Nome del Cliente
     * @param Cognome   Cognome del Cliente
     */
    void registraOrdine(int IDCliente, String Nome, String Cognome) {
        gestoreOrdini.registraOrdine(IDCliente, Nome, Cognome);
    }

    public void riceviPagamento() {
        //todo
    }

    public void sceltaCorriere() {
        //todo
    }

    public void sceltaPuntoPrelievo(int ID) {
        //todo
    }

    public void selezionaCorriere(int ID) {
        //todo
    }

    public void selezionaPuntoPrelievo(int IDOrdine) {
        //todo
    }

    /**
     * Imposta il negozio collegato all' interfaccia.
     *
     * @param negozio Negozio da impostare
     */
    public void setNegozio(Negozio negozio) {

        this.negozio = negozio;
    }

    /**
     * @param IDOrdine
     * @param IDPuntoPrelievo
     */
    //todo test
    public void setPuntoPrelievo(int IDOrdine, int IDPuntoPrelievo) {
        gestoreOrdini.setPuntoPrelievo(IDOrdine,IDPuntoPrelievo);
    }

    /**
     * @param IDOrdine
     * @param statoOrdine
     */
    //todo test
    public void setStatoOrdine(int IDOrdine, StatoOrdine statoOrdine) {
        gestoreOrdini.setStatoOrdine(IDOrdine,statoOrdine);
    }

    /**
     * @param IDOrdine
     */
    //todo test
    public void terminaOrdine(int IDOrdine) {
        gestoreOrdini.terminaOrdine(IDOrdine);
    }

    /**
     *
     * @param IDCliente
     * @param IDOrdine
     * @return
     */
    //todo test
    public String verificaEsistenzaCodice(int IDCliente, int IDOrdine) {
        return gestoreClienti.verificaEsistenzaCodice(IDCliente,IDOrdine);
    }


}