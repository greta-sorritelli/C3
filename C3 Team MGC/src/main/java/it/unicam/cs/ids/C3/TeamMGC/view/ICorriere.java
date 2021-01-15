package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.*;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXConsegnareMerceADestinazione;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXModificareDisponibilita;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere.JavaFXTrasportareMerce;
import javafx.fxml.FXML;

public class ICorriere implements JavaFXController {

    private final int IDCorriere;

    public ICorriere(int ID){
        this.IDCorriere = ID;
    }

    /**
     * Apre la finestra per consegnare la merce al punto di prelievo.
     */
    @FXML
    private void consegnaMerce() {
        openWindow("/ConsegnareMerceADestinazione.fxml", "Consegna Merce", new JavaFXConsegnareMerceADestinazione(IDCorriere));
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
    public void avviaRegistrazione(){
        openWindow("/RegistrazionePiattaforma.fxml", "Registrazione Piattaforma", new JavaFXRegistrazionePiattaforma());
    }

    /**
     * Apre la finestra registrarsi sulla piattaforma.
     */
    @FXML
    //todo creare fxml
    public void trasportoMerce(){
        openWindow("/TrasportareMerce.fxml", "TrasportareMerce", new JavaFXTrasportareMerce(IDCorriere));
    }

}
