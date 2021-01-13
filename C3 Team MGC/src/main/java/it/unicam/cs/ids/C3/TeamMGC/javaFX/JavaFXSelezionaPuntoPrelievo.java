
package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Objects;

public class JavaFXSelezionaPuntoPrelievo implements JavaFXController{
    private final GestoreOrdini gestoreOrdini;
    private final GestoreMagazzini gestoreMagazzini;
    private final GestoreCorrieri gestoreCorrieri;
    private int IDOrdine;

    public JavaFXSelezionaPuntoPrelievo(GestoreOrdini gestoreOrdini, GestoreMagazzini gestoreMagazzini, int IDOrdine, GestoreCorrieri gestoreCorrieri) {
        this.gestoreOrdini = gestoreOrdini;
        this.gestoreMagazzini = gestoreMagazzini;
        this.gestoreCorrieri = gestoreCorrieri;
        this.IDOrdine = IDOrdine;
    }

    /**
     * TabPane della finestra
     */
    @FXML
    TabPane tab;

    @FXML
    Tab consegnaPP;

    @FXML
    Tab consegnaResidenza;

    @FXML
    Tab ritiro;

    @FXML
    ChoiceBox<PuntoPrelievo> choicePuntoPrelievo = new ChoiceBox<>();

    @FXML
    TextField residenza;


    public void addResidenza(){
        try {
            gestoreOrdini.addResidenza(IDOrdine,residenza.getText());
            sceltaCorriere();
        } catch (SQLException exception) {
            //todo alert
            exception.printStackTrace();
        }
    }

    public void sceltaCorriere() {
        openWindow("/SelezionaCorriere.fxml", "SelezionaCorriere", new JavaFXSelezionaCorriere(gestoreCorrieri, residenza.getText()));
    }


    public void setPuntoPrelievo(){
        try {
            gestoreOrdini.setPuntoPrelievo(IDOrdine,choicePuntoPrelievo.getValue().getID());
            mandaAlert();
        } catch (SQLException exception) {
            //todo alert
            exception.printStackTrace();
        }
    }

    public void mandaAlert(){
//        gestoreMagazzini.mandaAlert();
        // todo alert al magazziniere
    }

    public void setStatoOrdine()  {
        try {
            gestoreOrdini.setStatoOrdine(IDOrdine, StatoOrdine.RITIRATO);
        } catch (SQLException exception) {
            //todo alert
            exception.printStackTrace();
        }
    }

    /**
     * todo
     */
    @FXML
    public void showMagazzini() {
        try {
            choicePuntoPrelievo.getItems().clear();
            choicePuntoPrelievo.setItems(FXCollections.observableArrayList(gestoreMagazzini.getItems()));
        } catch (Exception exception) {
            errorWindow("Errore!","Error.");
        }
    }

    @FXML
    public void updateMagazziniChoiceBox() {
        if (Objects.isNull(choicePuntoPrelievo.getValue())) {
            showMagazzini();
        }
    }
}
