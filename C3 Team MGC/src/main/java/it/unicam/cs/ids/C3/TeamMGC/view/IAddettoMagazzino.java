package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXAssegnaMerceCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXGestioneInventario;
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

public class IAddettoMagazzino {

    //todo
    private Negozio negozio = new Negozio(1);

    private final GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
    private final GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
    private final GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);

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

    /**
     * Apre la finestra per assegnare la merce al corriere.
     */
    @FXML
    private void assegnaMerceCorriere() {
        openWindow("/AssegnaMerceCorriere.fxml", "AssegnaMerce", new JavaFXAssegnaMerceCorriere(gestoreCorrieri,
                gestoreMagazzini, gestoreOrdini));
    }

    /**
     * Apre la finestra per la gestione dell'inventario.
     */
    @FXML
    //todo creare fxml
    private void modificaInventario() {
        openWindow("/GestioneInventario.fxml", "GestioneInventario", new JavaFXGestioneInventario(negozio));
    }

}
