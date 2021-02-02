
package it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso;

import it.unicam.cs.ids.C3.TeamMGC.corriere.Corriere;
import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller della pagina per selezionare il Corriere.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXSelezionaCorriere implements JavaFXController {

    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private Corriere selectedSimpleCorriere;
    private final String residenza;
    private final Negozio negozio;

    public JavaFXSelezionaCorriere(String residenza, Negozio negozio) {
        this.negozio = negozio;
        this.selectedSimpleCorriere = null;
        this.residenza = residenza;
    }

    /**
     * Tabella dei corrieri
     */
    @FXML
    TableView<ArrayList<String>> corriereTable;

    @FXML
    TableColumn<ArrayList<String>, String> IDCorriere;

    @FXML
    TableColumn<ArrayList<String>, String> NomeCorriere;

    @FXML
    TableColumn<ArrayList<String>, String> CognomeCorriere;

    /**
     * Collega i campi del Corriere alle colonne della tabella.
     */
    private void setCorriereCellValueFactory() {
        corriereTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        IDCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(0)));
        NomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(1)));
        CognomeCorriere.setCellValueFactory(corriere -> new SimpleObjectProperty<>(corriere.getValue().get(2)));
    }

    /**
     * Inserisce i dati dei corrieri nella tabella
     */
    @FXML
    public void visualizzaCorrieri() {
        try {
            setCorriereCellValueFactory();
            corriereTable.getItems().clear();
            corriereTable.getItems().addAll(gestoreCorrieri.getDettagliCorrieriDisponibili());
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void mandaAlert() {
        try {
            if (!corriereTable.getSelectionModel().isEmpty()) {
                int ID = Integer.parseInt(corriereTable.getSelectionModel().getSelectedItem().get(0));
                if (gestoreCorrieri.getItem(ID) != null) {
                    this.selectedSimpleCorriere = gestoreCorrieri.getItem(ID);
                    gestoreCorrieri.mandaAlert(ID, negozio, residenza);
                    successWindow("Alert mandato con successo!", "L' alert e' stato inviato al corriere.");
                    closeWindow((Stage) corriereTable.getScene().getWindow());
                }
            } else
                throw new IllegalArgumentException("Dati non presenti.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Alert!", "Inserire tutti i dati richiesti.");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

}