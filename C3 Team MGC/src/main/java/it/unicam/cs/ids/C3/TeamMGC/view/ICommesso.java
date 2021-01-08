package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXComunicareCodiceRitiro;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXRicezionePagamento;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXSelezionaCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXSelezionaPuntoPrelievo;
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

public class ICommesso {
    //todo
    private Negozio negozio = new Negozio(1);

    private final GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
    private final GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
    private final GestoreClienti gestoreClienti = new GestoreClienti();
    private final GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();

    public ICommesso() throws SQLException {
    }

    /**
     * Apre una nuova finestra
     *
     * @param a Fxml path
     * @param b Titolo della finestra.
     */
    @FXML
    public void openWindow(String a, String b, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(a));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(b);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
//            Image icon = new Image("/icon.png");
//            stage.getIcons().add(icon);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //todo da finire
    public void comunicaCodiceRitiro() {
        openWindow("/ComunicareCodiceRitiro.fxml", "ComunicareCodiceRitiro", new JavaFXComunicareCodiceRitiro(gestoreClienti));
    }

    //todo creare fxml
    public void ricezionePagamento() {
        openWindow("/RicezionePagamento.fxml", "RicezionePagamento", new JavaFXRicezionePagamento(gestoreOrdini));
    }

    //todo creare fxml
    public void sceltaCorriere() {
        openWindow("/SceltaCorriere.fxml", "SceltaCorriere", new JavaFXSelezionaCorriere(gestoreCorrieri));
    }

    //todo creare fxml
    public void selezionaPuntoPrelievo() {
        openWindow("/SelezionaPuntoPrelievo.fxml", "SelezionaPuntoPrelievo", new JavaFXSelezionaPuntoPrelievo(gestoreOrdini,gestoreMagazzini));
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