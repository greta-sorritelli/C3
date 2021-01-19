package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXComunicareConCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXConsegnareMerceAlCliente;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.SimplePuntoPrelievo;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class IMagazziniere implements JavaFXController {

    //todo
    private final PuntoPrelievo simplePuntoPrelievo = new SimplePuntoPrelievo(1);
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();

    public IMagazziniere() throws SQLException {
    }

    /**
     * Apre la finestra per comunicare con il corriere.
     */
    @FXML
    //todo creare fxml
    public void avvisaCorriere() {
        try {
            if(!gestoreCorrieri.getCorrieriDisponibili().isEmpty())
            openWindow("/ComunicareConCorriere.fxml", "Comunicare Con il Corriere", new JavaFXComunicareConCorriere(simplePuntoPrelievo.getID()));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }catch (IllegalArgumentException exception){
            alertWindow("Riprovare piu' tardi.", "Non ci sono corrieri disponibili.");
        }
    }

    /**
     * Apre la finestra per consegnare la merce al cliente.
     */
    @FXML
    public void consegnaMerceCliente() {
        openWindow("/ConsegnareMerceCliente.fxml", "ConsegnareMerceCliente", new JavaFXConsegnareMerceAlCliente(simplePuntoPrelievo));
    }


}