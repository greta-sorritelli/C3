package it.unicam.cs.ids.C3.TeamMGC.javaModel;

public interface ICommesso {

    void comunicazioneCodice();

    void comunicazioneCodiceCliente(String idCliente);

    void getCodice(String idCliente);

    void createCodice();

    void setCodice(String codice);

    void compilazioneOrdine();

    void getMerceDisponibile();

    void registraOrdine(String IDCliente, String Nome, String Cognome);

    void registraMerce(String descrizione, int quantita, double prezzo);

    void riceviPagamento();

    void sceltaCorriere();

    int getCorrieriDisponibili();

    Corriere selezionaCorriere();

    void addResidenza(String via, int NCivico);

    void getMagazziniDisponibili();

    void setPuntodiPrelievo(PuntoDiPrelievo magazzino);

    Magazziniere getMagazziniere(PuntoDiPrelievo magazzino);

    void setStatoOrdine(StatoOrdine RITIRATO);

}