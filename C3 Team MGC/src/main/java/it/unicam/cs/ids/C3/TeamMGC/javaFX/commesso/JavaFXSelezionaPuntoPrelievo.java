
package it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXSelezionaCorriere;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
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
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class JavaFXSelezionaPuntoPrelievo implements JavaFXController {
    private final GestoreOrdini gestoreOrdini;
    private final GestoreMagazzini gestoreMagazzini;
    private final GestoreCorrieri gestoreCorrieri;
    private int IDOrdine;
    private final Negozio negozio;

    public JavaFXSelezionaPuntoPrelievo(GestoreOrdini gestoreOrdini, GestoreMagazzini gestoreMagazzini, int IDOrdine, GestoreCorrieri gestoreCorrieri, Negozio negozio) {
        this.gestoreOrdini = gestoreOrdini;
        this.gestoreMagazzini = gestoreMagazzini;
        this.gestoreCorrieri = gestoreCorrieri;
        this.IDOrdine = IDOrdine;
        this.negozio = negozio;
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


    public void addResidenza() {
        try {
            if (!residenza.getText().isEmpty()) {
                gestoreCorrieri.getCorrieriDisponibili();
                gestoreOrdini.addResidenza(IDOrdine, residenza.getText());
                successWindow("Residenza salvata con successo!", "Ora potrai scegliere il corriere da avvisare.");
                sceltaCorriere();
                closeWindow((Stage) residenza.getScene().getWindow());
            } else
                throw new NullPointerException("Dati non presenti.");
        } catch (NullPointerException exception) {
            errorWindow("Errore!", "Inserire la residenza.");
        } catch (IllegalArgumentException exception) {
            errorWindow("Non ci sono corrieri disponibili.", "Scegliere di nuovo una modalita' di consegna.");
            residenza.clear();
        } catch (SQLException exception) {
            errorWindow("Errore!", "Error DB.");
        }
    }

    public void sceltaCorriere() {
        openWindow("/SelezionaCorriere.fxml", "SelezionaCorriere", new JavaFXSelezionaCorriere(gestoreCorrieri, residenza.getText(), negozio));
    }


    public void setPuntoPrelievo() {
        try {
            if (!choicePuntoPrelievo.getItems().isEmpty()) {
                gestoreOrdini.setPuntoPrelievo(IDOrdine, choicePuntoPrelievo.getValue().getID());
                mandaAlert();
                closeWindow((Stage) residenza.getScene().getWindow());
            } else
                throw new IllegalArgumentException("Dati non presenti.");
        } catch (Exception exception) {
            errorWindow("Errore!", "Inserire i dati richiesti.");
        }
    }

    public void mandaAlert() {
        try {
            gestoreMagazzini.mandaAlert(choicePuntoPrelievo.getValue().getID(), negozio);
            successWindow("Alert mandato con successo!", "L' alert e' stato inviato al magazziniere.");
        } catch (Exception exception) {
            errorWindow("Errore!", "Error.");
            exception.printStackTrace();
        }
    }

    public void setStatoOrdine() {
        try {
            gestoreOrdini.setStatoOrdine(IDOrdine, StatoOrdine.RITIRATO);
            successWindow("Ordine ritirato con successo!", "Lo stato dell' ordine e' stato impostato a ritirato.");
            closeWindow((Stage) residenza.getScene().getWindow());
        } catch (Exception exception) {
            errorWindow("Errore!", "Error.");
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
            errorWindow("Errore!", "Error.");
        }
    }

    @FXML
    public void updateMagazziniChoiceBox() {
        if (Objects.isNull(choicePuntoPrelievo.getValue())) {
            showMagazzini();
        }
    }
}
