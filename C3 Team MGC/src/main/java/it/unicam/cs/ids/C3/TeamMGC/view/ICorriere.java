package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXConsegnareMerceAlPuntoPrelievo;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXModicareDisponibilita;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXRegistrazionePiattaforma;
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

public class ICorriere {
    //todo
    private Negozio negozio = new Negozio(1);

    private final GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);
    private final GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
    private final GestoreCorrieri gestoreCorrieri= new GestoreCorrieri();

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
     * Apre la finestra per consegnare la merce al punto di prelievo.
     */
    @FXML
    //todo creare fxml
    private void consegnaMercePP() {
        openWindow("/ConsegnaMercePP.fxml", "ConsegnaMercePP", new JavaFXConsegnareMerceAlPuntoPrelievo(gestoreOrdini,
                gestoreMagazzini, gestoreCorrieri));
    }

    /**
     * Apre la finestra per modificare lo stato di disponibilita del corriere.
     */
    @FXML
    //todo creare fxml
    public void modificaDisponibilita(){
        openWindow("/ModificareDisponibilita.fxml", "ModificareDisponibilita", new JavaFXModicareDisponibilita(gestoreCorrieri));
    }

    /**
     * Apre la finestra registrarsi sulla piattaforma.
     */
    @FXML
    //todo creare fxml
    public void avviaRegistrazione(){
        openWindow("/RegistrazionePiattaforma.fxml", "RegistrazionePiattaforma", new JavaFXRegistrazionePiattaforma(gestoreCorrieri));
    }

//    public void trasportoMerce(){
//        //todo
//    }

}
