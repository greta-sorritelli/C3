package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.util.ArrayList;

public class GestoreOrdine {

    private final Negozio negozio;

    public GestoreOrdine(Negozio negozio) {
        this.negozio = negozio;
    }

    /**
     * Registra l'ordine con i dati del cliente e cambia il suo stato in "PAGATO.
     *
     * @param IDCliente ID del cliente a cui appartiene l'ordine
     * @param nome      Nome del cliente a cui appartiene l'ordine
     * @param cognome   Cognome del cliente a cui appartiene l'ordine
     */
    public Ordine registraOrdine(int IDCliente, String nome, String cognome) {
        Ordine ordine = new Ordine(IDCliente,nome,cognome);
        ordine.setStato(StatoOrdine.PAGATO);
        return ordine;
    }

    /**
     * Registra la merce
     *
     * @param descrizione  Descrizione della merce
     * @param quantita     Quantita della merce
     */
    public void registraMerce(String descrizione, int quantita) {
        ArrayList<Merce> array = new ArrayList<>();
        for (:
             ) {
            
        }}

}