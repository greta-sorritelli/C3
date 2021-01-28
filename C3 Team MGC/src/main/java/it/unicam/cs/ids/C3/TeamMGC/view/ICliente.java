package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXControllareAlert;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.cliente.JavaFXFiltrarePuntiVendita;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class ICliente implements JavaFXController {
    private final int IDCliente;
    private final String tipologiaUtente = "CLIENTE";

    public ICliente(int ID) throws SQLException {
        this.IDCliente = ID;
    }

    /**
     * Apre la finestra per controllare le notifiche.
     */
    @FXML
    public void controllaAlert() {
        openWindow("/ControllaAlert.fxml", "Visualizza le notifiche", new JavaFXControllareAlert(IDCliente, tipologiaUtente));
    }

    /**
     * Apre la finestra per filtrare i punti vendita.
     */
    @FXML
    public void filtrareNegozi() {
        openWindow("/cliente/FiltrarePuntiVendita.fxml", "Filtra i punti vendita", new JavaFXFiltrarePuntiVendita());
    }

    @FXML
    public void filtrarePromozioni() {
        //todo
    }
}
