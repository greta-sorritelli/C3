package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.cliente.Cliente;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXControllareAlert;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.cliente.JavaFXFiltrarePromozioni;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.cliente.JavaFXFiltrarePuntiVendita;
import javafx.fxml.FXML;

import java.util.concurrent.TimeUnit;

/**
 * Controller della Home del {@link Cliente}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class ICliente implements JavaFXController {
    private final int ID;
    private final String tipologiaUtente = "CLIENTE";

    public ICliente(int ID) {
        this.ID = ID;
    }

    /**
     * Apre la finestra per controllare le notifiche.
     */
    @FXML
    public void controllaAlert() {
        openWindow("/ControllaAlert.fxml", "Visualizza le notifiche", new JavaFXControllareAlert(ID, tipologiaUtente));
    }

    /**
     * Apre la finestra per filtrare i punti vendita.
     */
    @FXML
    public void filtrareNegozi() {
        openWindow("/cliente/FiltrarePuntiVendita.fxml", "Filtra i punti vendita", new JavaFXFiltrarePuntiVendita());
    }

    /**
     * Apre la finestra per filtrare le promozioni.
     */
    @FXML
    public void filtrarePromozioni() {
        openWindow("/cliente/FiltrarePromozioni.fxml", "Filtra le promozioni", new JavaFXFiltrarePromozioni());
    }
}
