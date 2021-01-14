package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXComunicareCodiceRitiro;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXRicezionePagamento;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ICommesso implements JavaFXController {
    //todo
    private final Negozio negozio = new Negozio(1);

    private final GestoreOrdini gestoreOrdini = new GestoreOrdini();
    private final GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
    private final GestoreClienti gestoreClienti = new GestoreClienti();
    private final GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();

    public ICommesso() throws SQLException {
    }

    public void comunicaCodiceRitiro() {
        openWindow("/ComunicareCodiceRitiro.fxml", "ComunicareCodiceRitiro", new JavaFXComunicareCodiceRitiro(gestoreClienti, gestoreOrdini));
    }

    public void ricezionePagamento() {
        openWindow("/RicezionePagamento.fxml", "RicezionePagamento", new JavaFXRicezionePagamento(negozio,gestoreOrdini, gestoreClienti, gestoreMagazzini, gestoreCorrieri));
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