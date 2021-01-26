package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.*;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXControllareAlert;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXConsegnareMerceADestinazione;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXModificareDisponibilita;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXTrasportareMerce;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class ICorriere implements JavaFXController {

    GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();

    private final int IDCorriere;
    private final String tipologiaUtente = "CORRIERE";

    public ICorriere(int ID) {
        this.IDCorriere = ID;
    }

    /**
     * Apre la finestra per consegnare la merce alla destinazione.
     */
    @FXML
    public void consegnaMerce() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Attendere...");
            alert.setContentText("Controllo della merce da consegnare.");
            alert.show();
            if (gestoreOrdini.getDettagliMerciOfCorriere(IDCorriere, StatoOrdine.IN_TRANSITO).isEmpty()) {
                alert.close();
                throw new IllegalArgumentException("Merci non presenti.");
            }
            alert.close();
            openWindow("/corriere/ConsegnareMerceADestinazione.fxml", "Consegna Merce", new JavaFXConsegnareMerceADestinazione(IDCorriere));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Riprovare piu' tardi.", "Non ci sono merci da consegnare.");
        }
    }

    /**
     * Apre la finestra per modificare lo stato di disponibilita del corriere.
     */
    @FXML
    public void modificaDisponibilita() {
        openWindow("/corriere/ModificareDisponibilita.fxml", "Modificare Disponibilita", new JavaFXModificareDisponibilita(IDCorriere));
    }

    /**
     * Apre la finestra per registrare la merce come in transito.
     */
    @FXML
    public void trasportoMerce() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Attendere...");
            alert.setContentText("Controllo della merce da trasportare.");
            alert.show();
            if (gestoreOrdini.getDettagliMerciOfCorriere(IDCorriere, StatoOrdine.AFFIDATO_AL_CORRIERE).isEmpty()) {
                alert.close();
                throw new IllegalArgumentException("Merci non presenti.");
            }
            alert.close();
            openWindow("/corriere/TrasportareMerce.fxml", "TrasportareMerce", new JavaFXTrasportareMerce(IDCorriere));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Riprovare piu' tardi.", "Non ci sono merci da trasportare.");
        }
    }

    /**
     * Apre la finestra per controllare le notifiche.
     */
    @FXML
    public void controllaAlert() {
        openWindow("/ControllaAlert.fxml", "Visualizza le notifiche", new JavaFXControllareAlert(IDCorriere,tipologiaUtente));
    }

}
