package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXComunicareCodiceRitiro;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXRicezionePagamento;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;

import java.sql.SQLException;

public class ICommesso implements JavaFXController {
    //todo
    private final Negozio negozio = new Negozio(1);

    public ICommesso() throws SQLException {
    }

    public void comunicaCodiceRitiro() {
        openWindow("/ComunicareCodiceRitiro.fxml", "ComunicareCodiceRitiro", new JavaFXComunicareCodiceRitiro());
    }

    public void ricezionePagamento() {
        openWindow("/RicezionePagamento.fxml", "RicezionePagamento", new JavaFXRicezionePagamento(negozio));
    }


//    /**
//     * Imposta il negozio collegato all' interfaccia.
//     *
//     * @param negozio Negozio da impostare
//     */
//    public void setNegozio(Negozio negozio) {
//
//        this.negozio = negozio;
//    }

}