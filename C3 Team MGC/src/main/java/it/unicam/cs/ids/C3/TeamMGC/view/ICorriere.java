package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.*;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXConsegnareMerceADestinazione;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXModificareDisponibilita;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXTrasportareMerce;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import java.sql.SQLException;

/**
 * Controller della Home del {@link Corriere}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class ICorriere implements JavaFXController {

    GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();

    private final int ID;
    private final String tipologiaUtente = "CORRIERE";

    public ICorriere(int ID) {
        this.ID = ID;
    }

    /**
     * Apre la finestra per consegnare la merce alla destinazione.
     */
    @FXML
    public void consegnaMerce() {
        try {
            informationWindow("Attendere...", "Controllo della merce da consegnare.", 3);
            if (gestoreOrdini.getDettagliMerciOfCorriere(ID, StatoOrdine.IN_TRANSITO).isEmpty()) {
                throw new IllegalArgumentException("Merci non presenti.");
            }
            openWindow("/corriere/ConsegnareMerceADestinazione.fxml", "Consegna Merce", new JavaFXConsegnareMerceADestinazione(ID));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Riprovare piu' tardi.", "Non ci sono merci da consegnare.");
        }
    }

    /**
     * Apre la finestra per modificare lo stato di disponibilità del corriere.
     */
    @FXML
    public void modificaDisponibilita() {
        openWindow("/corriere/ModificareDisponibilita.fxml", "Modificare Disponibilita", new JavaFXModificareDisponibilita(ID));
    }

    /**
     * Apre la finestra per registrare la merce come in transito.
     */
    @FXML
    public void trasportoMerce() {
        try {
            informationWindow("Attendere...", "Controllo della merce da trasportare.", 3);
            if (gestoreOrdini.getDettagliMerciOfCorriere(ID, StatoOrdine.AFFIDATO_AL_CORRIERE).isEmpty()) {
                throw new IllegalArgumentException("Merci non presenti.");
            }
            openWindow("/corriere/TrasportareMerce.fxml", "TrasportareMerce", new JavaFXTrasportareMerce(ID));
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
        openWindow("/ControllaAlert.fxml", "Visualizza le notifiche", new JavaFXControllareAlert(ID, tipologiaUtente));
    }

}
