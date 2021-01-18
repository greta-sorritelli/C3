package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.*;
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

    public ICorriere(int ID) {
        this.IDCorriere = ID;
    }

    /**
     * Apre la finestra per consegnare la merce al punto di prelievo.
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
            openWindow("/ConsegnareMerceADestinazione.fxml", "Consegna Merce", new JavaFXConsegnareMerceADestinazione(IDCorriere));
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
        openWindow("/ModificareDisponibilita.fxml", "Modificare Disponibilita", new JavaFXModificareDisponibilita(IDCorriere));
    }

    /**
     * Apre la finestra registrarsi sulla piattaforma.
     */
    @FXML
    //todo creare fxml
    public void avviaRegistrazione() {
        openWindow("/RegistrazionePiattaforma.fxml", "Registrazione Piattaforma", new JavaFXRegistrazionePiattaforma());
    }

    /**
     * Apre la finestra registrarsi sulla piattaforma.
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
            openWindow("/TrasportareMerce.fxml", "TrasportareMerce", new JavaFXTrasportareMerce(IDCorriere));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Riprovare piu' tardi.", "Non ci sono merci da trasportare.");
        }
    }

}
