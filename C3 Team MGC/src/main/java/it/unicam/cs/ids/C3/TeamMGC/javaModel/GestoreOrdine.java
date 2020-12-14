package it.unicam.cs.ids.C3.TeamMGC.javaModel;

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
     * @param descrizione
     * @param quantita
     * @param prezzo
     */
    public void registraMerce(String descrizione, int quantita, double prezzo) {

    }

}