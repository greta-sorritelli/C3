package it.unicam.cs.ids.C3.TeamMGC.javaModel;

public class Merce {
    private int ID;
    private int IDOrdine;
    private double prezzo;
    private String descrizione;
    private int quantita;
    private StatoOrdine stato;

    /**
     * Costruttore per importare i dati dal DB.
     */
    public Merce(int ID, int IDOrdine, double prezzo, String descrizione, int quantita, StatoOrdine stato) {
        this.ID = ID;
        this.IDOrdine = IDOrdine;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.stato = stato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getID() {
        return ID;
    }

    public int getIDOrdine() {
        return IDOrdine;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        //todo fare la modifica anche sul DB
        this.quantita = quantita;
    }

    public StatoOrdine getStato() {
        return stato;
    }

//todo implementare DatiMerce
//	public Collection<DatiMerce> getDettagli() {
//		// TODO - implement Merce.getDettagli
//		throw new UnsupportedOperationException();
//	}

    /**
     * @param riRITIRATO
     */
    public void setStato(int riRITIRATO) {
        // TODO - implement Merce.setStato
        throw new UnsupportedOperationException();
    }

    /**
     * @param IDOrdine
     */
    public void setOrdine(int IDOrdine) {
        // TODO - implement Merce.setOrdine
        throw new UnsupportedOperationException();
    }

}