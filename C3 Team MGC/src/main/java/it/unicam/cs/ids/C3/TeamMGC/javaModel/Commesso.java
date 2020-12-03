package it.unicam.cs.ids.C3.TeamMGC.javaModel;

import java.util.Random;

public class Commesso extends Ruolo implements ICommesso {
    @Override
    public void addResidenza(String via, int NCivico) {

    }

    @Override
    public void compilazioneOrdine() {

    }

    @Override
    public String generaCodiceRitiro() {
        Random rand = new Random();
        String tmp = "";
        for (int i = 0; i < 8; i++)
            tmp = tmp.concat(String.valueOf(rand.nextInt(10)));
        return tmp;
    }

    @Override
    public void getMagazziniDisponibili() {

    }

    @Override
    public void registraMerce(String descrizione, int quantita, double prezzo) {

    }

    @Override
    public void registraOrdine(String IDCliente, String Nome, String Cognome) {

    }

    @Override
    public void riceviPagamento() {

    }

    @Override
    public void sceltaCorriere() {

    }

    @Override
    public Corriere selezionaCorriere() {
        return null;
    }

    @Override
    public void setPuntodiPrelievo(PuntoPrelievo magazzino) {

    }

    @Override
    public void setStatoOrdine(StatoOrdine RITIRATO) {

    }

    @Override
    public void verificaValiditaCodice(int IDCliente) {

    }
}