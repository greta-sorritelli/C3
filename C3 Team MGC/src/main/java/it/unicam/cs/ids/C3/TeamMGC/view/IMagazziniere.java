package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXComunicareConCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXConsegnareMerceAlCliente;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class IMagazziniere implements JavaFXController {

    //todo
    private final SimplePuntoPrelievo simplePuntoPrelievo = new SimplePuntoPrelievo(1);

    public IMagazziniere() throws SQLException {
    }

    /**
     * Apre la finestra per comunicare con il corriere.
     */
    @FXML
    //todo creare fxml
    public void avvisaCorriere() {
        openWindow("/ComunicareConCorriere.fxml", "Comunicare Con il Corriere", new JavaFXComunicareConCorriere(simplePuntoPrelievo.getID()));
    }

    /**
     * Apre la finestra per consegnare la merce al cliente.
     */
    @FXML
    public void consegnaMerceCliente() {
        openWindow("/ConsegnareMerceCliente.fxml", "ConsegnareMerceCliente", new JavaFXConsegnareMerceAlCliente(simplePuntoPrelievo));
    }


}