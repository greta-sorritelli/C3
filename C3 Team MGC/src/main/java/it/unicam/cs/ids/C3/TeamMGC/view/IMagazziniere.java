package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXComunicareConCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere.JavaFXConsegnareMerceAlCliente;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.PuntoPrelievo;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class IMagazziniere implements JavaFXController {

    private final PuntoPrelievo puntoPrelievo;
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();

    public IMagazziniere(int IDMagazzino) throws SQLException {
        this.puntoPrelievo = gestoreMagazzini.getItem(IDMagazzino);
    }

    /**
     * Apre la finestra per comunicare con il corriere.
     */
    @FXML
    public void avvisaCorriere() {
        try {
            if(!gestoreCorrieri.getCorrieriDisponibili().isEmpty())
            openWindow("/magazziniere/ComunicareConCorriere.fxml", "Comunicare Con il Corriere", new JavaFXComunicareConCorriere(puntoPrelievo.getID()));
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
        openWindow("/magazziniere/ConsegnareMerceCliente.fxml", "ConsegnareMerceCliente", new JavaFXConsegnareMerceAlCliente(puntoPrelievo));
    }


}