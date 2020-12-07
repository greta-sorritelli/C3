package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.util.Random;

public class ICommesso {

    void compilazioneOrdine() {
    }

    /**
     * @param IDCliente
     * @param Nome
     * @param Cognome
     */
    void registraOrdine(String IDCliente, String Nome, String Cognome) {
    }

    /**
     * @param descrizione
     * @param quantita
     * @param prezzo
     */
    void registraMerce(String descrizione, int quantita, double prezzo) {
    }

    void riceviPagamento() {
    }

    void sceltaCorriere() {
    }

    Corriere selezionaCorriere() {
        return null;
    }

    /**
     * @param via
     * @param NCivico
     */
    void addResidenza(String via, int NCivico) {
    }

    void getMagazziniDisponibili() {
    }

    /**
     * @param magazzino
     */
    void setPuntodiPrelievo(PuntoPrelievo magazzino) {
    }

    /**
     * @param RITIRATO
     */
    void setStatoOrdine(StatoOrdine RITIRATO) {
    }

    /**
     * @param IDCliente
     */
    void verificaValiditaCodice(int IDCliente) {
    }

    /**
     * @return
     */
    String generaCodiceRitiro() {
        Random rand = new Random();
        String tmp = "";
        for (int i = 0; i < 8; i++)
            tmp = tmp.concat(String.valueOf(rand.nextInt(10)));
        return tmp;
    }

}