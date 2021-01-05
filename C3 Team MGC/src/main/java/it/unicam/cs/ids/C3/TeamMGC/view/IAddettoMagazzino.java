package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXAssegnaMerceCorriere;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class IAddettoMagazzino {

    //todo
    private Negozio negozio = new Negozio(1);

    private final GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
    private final GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
    private final GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);

    /**
     * This method open a new window.
     *
     * @param a Fxml path
     * @param b Title of window.
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
     * This method open the window for adding account and updates the account table.
     */
    @FXML
    private void assegnaMerceCorriere() {
        openWindow("/AssegnaMerceCorriere.fxml", "AssegnaMerce", new JavaFXAssegnaMerceCorriere(gestoreCorrieri,
                gestoreMagazzini, gestoreOrdini));
    }

//    public void assegnaMerceCorriere() {
//        //todo
//    }

    public void selezionaCorriere(int ID) throws SQLException {
        gestoreCorrieri.selezionaCorriere(ID);
    }

    public void getDettagliMagazziniDisponibili() throws SQLException {
        gestoreMagazzini.getDettagliItems();
    }

    public void sceltaPuntoPrelievo(int ID) throws SQLException {
        gestoreMagazzini.sceltaPuntoPrelievo(ID);
    }

    public void visualizzaMerce(int capienza) throws SQLException {
        gestoreOrdini.visualizzaMerce(capienza);
    }

    public void confermaAssegnazioneMerce(int IDCorriere, int IDMerce, int capienza) throws SQLException {
        gestoreCorrieri.setCapienza(IDCorriere, capienza);
        gestoreOrdini.setStatoMerce(IDMerce, StatoOrdine.AFFIDATO_AL_CORRIERE);
    }
}
