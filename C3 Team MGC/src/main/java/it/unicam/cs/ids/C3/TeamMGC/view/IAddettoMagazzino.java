package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.addettoMagazzino.JavaFXAssegnaMerceCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.addettoMagazzino.JavaFXGestioneInventario;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import it.unicam.cs.ids.C3.TeamMGC.personale.AddettoMagazzinoNegozio;
import javafx.fxml.FXML;

import java.sql.SQLException;

/**
 * Controller della Home dell' {@link AddettoMagazzinoNegozio}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class IAddettoMagazzino implements JavaFXController {

    private final GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();
    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private final GestoreOrdini gestoreOrdini = GestoreOrdini.getInstance();
    private final Negozio negozio;
    private final int ID;

    public IAddettoMagazzino(int ID, int IDNegozio) throws SQLException {
        this.ID = ID;
        this.negozio = gestoreNegozi.getItem(IDNegozio);
    }

    /**
     * Apre la finestra per assegnare la merce al corriere.
     */
    @FXML
    private void assegnaMerceCorriere() {
        try {
            gestoreCorrieri.getCorrieriDisponibili();
            if (gestoreOrdini.getDettagliMerce(StatoOrdine.CORRIERE_SCELTO).isEmpty())
                throw new IllegalArgumentException("Merci non disponibili.");
            openWindow("/addettoMagazzino/AssegnaMerceCorriere.fxml", "AssegnaMerce", new JavaFXAssegnaMerceCorriere());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Corrieri disponibili non presenti."))
                alertWindow("Corrieri non disponibili.", "Aggiorna piu' tardi.");
            if (e.getMessage().equals("Merci non disponibili."))
                alertWindow("Non ci sono merci da affidare al corriere.", "Aggiorna piu' tardi.");
        }
    }

    /**
     * Apre la finestra per la gestione dell' inventario.
     */
    @FXML
    private void modificaInventario() {
        openWindow("/addettoMagazzino/GestioneInventario.fxml", "GestioneInventario", new JavaFXGestioneInventario(negozio));
    }

}
