package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXControllareAlert;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXComunicareConCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXConsegnareMerceAlCliente;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.fxml.FXML;

import java.sql.SQLException;
/**
 * Controller della Home del Magazziniere.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class IMagazziniere implements JavaFXController {

    private final PuntoPrelievo puntoPrelievo;
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
    private final String tipologiaUtente = "MAGAZZINIERE";

    public IMagazziniere(int IDMagazzino) throws SQLException {
        this.puntoPrelievo = gestoreMagazzini.getItem(IDMagazzino);
    }

    /**
     * Apre la finestra per comunicare con il corriere.
     */
    @FXML
    public void avvisaCorriere() {
        try {
            gestoreCorrieri.getCorrieriDisponibili();
            openWindow("/magazziniere/ComunicareConCorriere.fxml", "Comunicare Con il Corriere", new JavaFXComunicareConCorriere(puntoPrelievo.getID()));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.", 2);
        }catch (IllegalArgumentException exception){
            alertWindow("Riprovare piu' tardi.", "Non ci sono corrieri disponibili.", 2);
        }
    }

    /**
     * Apre la finestra per consegnare la merce al cliente.
     */
    @FXML
    public void consegnaMerceCliente() {
        openWindow("/magazziniere/ConsegnareMerceCliente.fxml", "ConsegnareMerceCliente", new JavaFXConsegnareMerceAlCliente(puntoPrelievo));
    }

    /**
     * Apre la finestra per controllare le notifiche.
     */
    @FXML
    public void controllaAlert() {
        openWindow("/ControllaAlert.fxml", "Visualizza le notifiche", new JavaFXControllareAlert(puntoPrelievo.getID(), tipologiaUtente));
    }


}