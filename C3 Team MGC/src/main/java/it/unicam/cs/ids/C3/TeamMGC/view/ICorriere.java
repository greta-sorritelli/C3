package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.*;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXConsegnareMerceAlPuntoPrelievo;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXModificareDisponibilita;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXTrasportareMerce;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class ICorriere implements JavaFXController {
    //todo
    private final Negozio negozio = new Negozio(1);

    private final GestoreOrdini gestoreOrdini = new GestoreOrdini();
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
    private final GestoreCorrieri gestoreCorrieri= new GestoreCorrieri();
    private int IDCorriere;

    public ICorriere(int ID) throws SQLException {
        this.IDCorriere = ID;
    }

//    /**
//     * Apre una nuova finestra
//     *
//     * @param a Fxml path
//     * @param b Titolo della finestra.
//     */
//    @FXML
//    public void openWindow(String a, String b, Object controller) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(a));
//            fxmlLoader.setController(controller);
//            Parent root = fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.setTitle(b);
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setScene(new Scene(root));
////            Image icon = new Image("/icon.png");
////            stage.getIcons().add(icon);
//            stage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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
    public void modificaDisponibilita() {
        openWindow("/ModificareDisponibilita.fxml", "Modificare Disponibilita", new JavaFXModificareDisponibilita(gestoreCorrieri, IDCorriere));
    }

    /**
     * Apre la finestra registrarsi sulla piattaforma.
     */
    @FXML
    //todo creare fxml
    public void avviaRegistrazione(){
        openWindow("/RegistrazionePiattaforma.fxml", "Registrazione Piattaforma", new JavaFXRegistrazionePiattaforma(gestoreCorrieri));
    }

    /**
     * Apre la finestra registrarsi sulla piattaforma.
     */
    @FXML
    //todo creare fxml
    public void trasportoMerce(){
        openWindow("/TrasportareMerce.fxml", "TrasportareMerce", new JavaFXTrasportareMerce(gestoreOrdini));
    }

}
