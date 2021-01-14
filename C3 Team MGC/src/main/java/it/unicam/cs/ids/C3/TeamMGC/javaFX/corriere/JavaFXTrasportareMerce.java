
package it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.ordine.StatoOrdine;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXTrasportareMerce implements JavaFXController {

    private final GestoreOrdini gestoreOrdini;

    @FXML
    TableView<ArrayList<String>> merceTable;

    @FXML
    TableColumn<ArrayList<String>, String> merceID;

    @FXML
    TableColumn<ArrayList<String>, String> merceIDOrdine;

    @FXML
    TableColumn<ArrayList<String>, String> merceDescrizione;

    @FXML
    TableColumn<ArrayList<String>, String> mercePrezzo;

    @FXML
    TableColumn<ArrayList<String>, String> merceQuantita;


    public JavaFXTrasportareMerce(GestoreOrdini gestoreOrdini) {
        this.gestoreOrdini = gestoreOrdini;
    }

    private void setCorriereCellValueFactory() {
        merceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        merceID.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(0)));
        merceIDOrdine.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(1)));
        merceDescrizione.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(2)));
        mercePrezzo.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(3)));
        merceQuantita.setCellValueFactory(merce -> new SimpleObjectProperty<>(merce.getValue().get(4)));
    }

    public void visualizzaMerce() {
        try {
            setCorriereCellValueFactory();
            merceTable.getItems().clear();
            merceTable.getItems().addAll(gestoreOrdini.getDettagliMerce(StatoOrdine.AFFIDATO_AL_CORRIERE));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (NullPointerException e) {
            alertWindow("Merci non disponibili.", "Nessuna merce affidata.");
        }
    }

    @FXML
    public void setStatoInTransito() {

    }

    private void getDettagliMerce(StatoOrdine statoOrdine) {
        // todo stato affidato al corriere
    }

    public void setStatoMerce(int IDMerce, StatoOrdine statoOrdine) {
        //todo stato in transito
    }

}
