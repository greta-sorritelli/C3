package it.unicam.cs.ids.C3.TeamMGC.javaModel;

public class Ordine {

    private String ID;
    private String IDCliente;
    private String nomeCliente;
    private String cognomeCliente;
    private double totalePrezzo;
    private StatoOrdine stato;
    private PuntoPrelievo puntoPrelievo;

    /**
     * @param via
     * @param NCivico
     */
    public void addResidenza(int via, int NCivico) {
        // TODO - implement Ordine.addResidenza
        throw new UnsupportedOperationException();
    }

    /**
     * @param merce
     * @param quantita
     */
    public void aggiungiMerce(int merce, int quantita) {
        // TODO - implement Ordine.aggiungiMerce
        throw new UnsupportedOperationException();
    }

    public String getID() {
        return ID;
    }

//todo implementare DatiOrdine
//	public Collection<DatiOrdine> getDettagli() {
//		// TODO - implement Ordine.getDettagli
//		throw new UnsupportedOperationException();
//	}

    public void riceviPagamento() {
        // TODO - implement Ordine.riceviPagamento
        throw new UnsupportedOperationException();
    }

    /**
     * @param magazzino
     */
    public void setPuntodiPrelievo(int magazzino) {
        // TODO - implement Ordine.setPuntodiPrelievo
        throw new UnsupportedOperationException();
    }

    /**
     * @param RITIRATO
     */
    public void setStato(int RITIRATO) {
        // TODO - implement Ordine.setStato
        throw new UnsupportedOperationException();
    }

}